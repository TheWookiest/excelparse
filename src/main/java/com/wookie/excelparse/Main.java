package com.wookie.excelparse;

import com.wookie.excelparse.files.FileParser;
import com.wookie.excelparse.http.HttpParser;
import com.wookie.excelparse.model.Bloacklauncher;
import com.wookie.excelparse.parser.ReadWriteExcelFile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;

public class Main {

    private static ReadWriteExcelFile readWriteExcelFile = new ReadWriteExcelFile();
    private static FileParser fileParser = new FileParser();

    public static void main(String[] args) throws IOException, InterruptedException {
//        parser.parse("/home/olexii/IdeaProjects/excelparse/src/main/resources/Blocklauncher.xlsx");

//        fileParser.iterateAllFilesInFolder("/home/olexii/Pictures");

        try {
            readWriteExcelFile.readXLSXFile("/home/olexii/IdeaProjects/excelparse/src/main/resources/Blocklauncher.xlsx");
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }

        HttpParser.parseAppStoreByAllIds(readWriteExcelFile.getParsed());
//
//        System.out.println(readWriteExcelFile.getParsed().size());

//        for(Map.Entry<String, Bloacklauncher> entry : readWriteExcelFile.getParsed().entrySet()) {
////            System.out.println("key: " + entry.getKey());
////            System.out.println("value: " + entry.getValue());
////            System.out.println("=======================================");
//            Thread.sleep(2000);
//            try {
//                HttpParser.parse(entry.getKey().toLowerCase());
//            } catch (Exception e) {
//            }
//        }

//        HttpParser.parseAppStore("clash of clan");
        readWriteExcelFile.writeXLSXFile(HttpParser.res, "/home/olexii/IdeaProjects/excelparse/src/main/resources/iapp222.xlsx");
//        HttpParser.parse("clash of clan");
    }
}
