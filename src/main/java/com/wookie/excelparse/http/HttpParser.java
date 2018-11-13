package com.wookie.excelparse.http;

import com.wookie.excelparse.model.Bloacklauncher;
import com.wookie.excelparse.model.GooglePlayRes;
import com.wookie.excelparse.parser.ReadWriteExcelFile;
import jdk.internal.org.xml.sax.InputSource;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpParser {

    public static List<GooglePlayRes> res = new ArrayList<>(500);
    private static ReadWriteExcelFile readWriteExcelFile = new ReadWriteExcelFile();

    private static String getRequest(String toSearch) throws IOException {
        String toSearchUrl = toSearch.replaceAll(" ", "%20");
        System.out.println("url: " + toSearchUrl);

        String urlString = "https://play.google.com/store/search?q=" + toSearchUrl.toLowerCase();
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        InputStream is = conn.getInputStream();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

//        builder = factory.newDocumentBuilder();
//        return builder.parse(is);

        return IOUtils.toString(is, "UTF-8");
    }

    private static Document getGoogleDocument(String toSearch) throws IOException, ParserConfigurationException, SAXException {
        String toSearchUrl = toSearch.replaceAll(" ", "%20");
        System.out.println("url: " + toSearchUrl);

        String urlString = "https://play.google.com/store/search?q=" + toSearchUrl.toLowerCase();
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        InputStream is = conn.getInputStream();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        builder = factory.newDocumentBuilder();
        return builder.parse(is);

//        return IOUtils.toString(is, "UTF-8");
    }

    private static String getITunesRequest(String toSearch) throws IOException {
        String urlString = "https://www.apple.com/search/" + toSearch.toLowerCase().replaceAll(" ", "-") + "?src=serp";
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        InputStream is = conn.getInputStream();

//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder;

//        builder = factory.newDocumentBuilder();
//        return builder.parse(is);

        return IOUtils.toString(is, "UTF-8");
    }

    private static String getITunesRequestByAppId(String appId) throws IOException {
        String urlString = "https://itunes.apple.com/app/skype-for-ipad/id" + appId;
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        InputStream is = conn.getInputStream();

//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder;

//        builder = factory.newDocumentBuilder();
//        return builder.parse(is);

        return IOUtils.toString(is, "UTF-8");
    }

//    public static void parse(String toSearch) {
//        try {
//            System.out.println(getGoogleDocument(toSearch));
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }

    public static void parse(String toSearch) throws IOException {

        GooglePlayRes googlePlayRes = new GooglePlayRes();
        String parsed1 = "";
        String parsed2 = "";
        try {
            System.out.println("================================");
            System.out.print("TO SEARCH: " + toSearch + " - ");
            googlePlayRes.setName(toSearch);

            String page = getRequest(toSearch);
            toSearch = toSearch.replaceAll(" ", "").toLowerCase();
//            System.out.println(page);

            int index = page.indexOf("." + toSearch);
            parsed1 = parseGooglePlay(page.substring(index - 30, index + 40), toSearch);
            System.out.println(parsed1);
            index = page.indexOf(toSearch + ".");
            parsed2 = parseGooglePlay(page.substring(index - 30, index + 40), toSearch);
            System.out.println(parsed2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String ressult = parsed1;
        if (!parsed1.equalsIgnoreCase(parsed2))
            ressult += "  -----------  " + parsed2;
        googlePlayRes.setResult(ressult);

        System.out.println("================================");
        res.add(googlePlayRes);
    }

    private static String parseGooglePlay(String toParse, String toSearch) {
        try {
//            System.out.println("TO PARSE: " + toParse);
            String[] splitted = toParse.split("\"");
            for (String s : splitted) {
                if (s.contains(toSearch))
                    return s;
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public static void parseAppStoreByAllIds(Map<String, Bloacklauncher> apps) throws IOException {

        for (long appId = 2760; appId < Long.MAX_VALUE; appId++) {
            try {
                System.out.println("================================");

                String page = getITunesRequestByAppId(Long.toString(appId)).toLowerCase();
//                System.out.println(page);

                for(Map.Entry<String, Bloacklauncher> entry : apps.entrySet()) {
                    if(page.contains(entry.getKey().toLowerCase())) {
                        res.add(new GooglePlayRes(entry.getKey(), Long.toString(appId)));
                        System.out.println("Founded: " + appId);
                    }
                }

                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("err: " + e.getMessage());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e1) {
                }
            }

            System.out.println("================================");

            if(appId % 1000 == 0) {
                System.out.println("TRY to SAVE");
                readWriteExcelFile.writeXLSXFile(res, "/home/olexii/IdeaProjects/excelparse/src/main/resources/testappi" +
                        + appId + ".xlsx");
                res = new ArrayList<>(500);
                System.out.println("SAVED");
            }
        }
    }



    public static void parseAppStore(String toSearch) throws IOException {

        GooglePlayRes googlePlayRes = new GooglePlayRes();
        try {
            System.out.println("================================");
            System.out.print("TO SEARCH: " + toSearch + " - ");
            toSearch = toSearch.replaceAll(" ", "-");
            googlePlayRes.setName(toSearch);

            String page = getITunesRequest(toSearch);
            toSearch = toSearch.replaceAll(" ", "").toLowerCase();
//            System.out.println(page);

            int index = page.indexOf("/app/" + toSearch);
            System.out.println("aaaAa:" + page.substring(index - 30, index + 50));

            String[] splitted = page.substring(index - 30, index + 50).split("/");

            for(String s : splitted) {
//                System.out.println("SS: " + s);
                if (s.contains("id")) {
                    String temp = s.split("\\?")[0].replaceAll("id", "");
                    System.out.println("FOUNDDD: " + temp);
                    googlePlayRes.setResult(temp);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("================================");
        res.add(googlePlayRes);
    }
}
