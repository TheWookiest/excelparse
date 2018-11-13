package com.wookie.excelparse.model;

import java.util.Objects;

public class Bloacklauncher {

    String name;
    String type;
    String appleUrl;
    String googleUrl;
    String appleLink;
    String googleLink;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAppleUrl() {
        return appleUrl;
    }

    public void setAppleUrl(String appleUrl) {
        if(this.appleUrl == null) {
            if (appleUrl.contains("itunes.apple.com")) {
                String[] splittedOnce = appleUrl.split("/");
//                System.out.println("APPPP : " + splittedOnce[splittedOnce.length - 1]);
                String appId = splittedOnce[splittedOnce.length - 1].split("\\?")[0].replaceAll("id", "");
//                System.out.println("appID: " + appId);

                this.appleUrl = appId;
            } else {
                this.appleUrl = appleUrl;
            }
        }
    }

    public String getGoogleUrl() {
        return googleUrl;
    }

    public void setGoogleUrl(String googleUrl) {
        if(googleUrl.contains("play.google.com")) {
            String[] splittedOnce = googleUrl.split("/");
            String[] splittedSecond;
//            System.out.println("lengthh: " + splittedOnce.length);
            if(splittedOnce.length == 9) {
                splittedSecond = splittedOnce[8].split("\\?");
            } else {
                splittedSecond = splittedOnce[5].split("\\?");
            }
            String[] splittedThird = splittedSecond[1].split(",");
            String googlePackageName = splittedThird[0]
                    .split("&")[0]
                    .replaceAll("id=", "")
                    .replace(";", "")
                    .replace("\"", "");
//            System.out.println("AAAA: " + googlePackageName);

            this.googleUrl = googlePackageName;
        } else {
            this.googleUrl = googleUrl;
        }
    }

    public String getAppleLink() {
        return appleLink;
    }

    public void setAppleLink(String appleLink) {
        this.appleLink = appleLink;
    }

    public String getGoogleLink() {
        return googleLink;
    }

    public void setGoogleLink(String googleLink) {
        String[] splittedOnce = googleLink.split("/");
        String[] splittedSecond;
//        System.out.println("lengthh: " + splittedOnce.length);
        if(splittedOnce.length == 9) {
            splittedSecond = splittedOnce[8].split("\\?");
        } else {
            splittedSecond = splittedOnce[5].split("\\?");
        }
        String[] splittedThird = splittedSecond[1].split(",");
        String googlePackageName = splittedThird[0]
                .split("&")[0]
                .replaceAll("id=", "")
                .replace(";", "")
                .replace("\"", "");
//        System.out.println("FFFF: " + googlePackageName);

        this.googleLink = googlePackageName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bloacklauncher that = (Bloacklauncher) o;
        return Objects.equals(name.toLowerCase(), that.name.toLowerCase()) &&
                Objects.equals(type, that.type) &&
                Objects.equals(appleUrl, that.appleUrl) &&
                Objects.equals(googleUrl, that.googleUrl) &&
                Objects.equals(appleLink, that.appleLink) &&
                Objects.equals(googleLink, that.googleLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, appleUrl, googleUrl, appleLink, googleLink);
    }

    @Override
    public String toString() {
        return "Bloacklauncher{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", appleUrl='" + appleUrl + '\'' +
                ", googleUrl='" + googleUrl + '\'' +
                ", appleLink='" + appleLink + '\'' +
                ", googleLink='" + googleLink + '\'' +
                '}';
    }
}
