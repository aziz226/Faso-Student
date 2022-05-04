package com.example.fasostudents.Constante;

public class ModelPost {

    String  UserName, ProfilImg, Date, Heure, Pub_description, PostUrl, Id;

    public ModelPost() { }

    public ModelPost(String userName, String profilImg, String date, String heure,
                     String pub_description, String postUrl, String id) {
        UserName = userName;
        ProfilImg = profilImg;
        Date = date;
        Heure = heure;
        Pub_description = pub_description;
        PostUrl = postUrl;
        Id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getProfilImg() {
        return ProfilImg;
    }

    public void setProfilImg(String profilImg) {
        ProfilImg = profilImg;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getHeure() {
        return Heure;
    }

    public void setHeure(String heure) {
        Heure = heure;
    }

    public String getPub_description() {
        return Pub_description;
    }

    public void setPub_description(String pub_description) {
        Pub_description = pub_description;
    }

    public String getPostUrl() {
        return PostUrl;
    }

    public void setPostUrl(String postUrl) {
        PostUrl = postUrl;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
