package com.example.fasostudents.Constante;

public class ModelUser {
    String  UserName, Ville, Universite, Domaine, Email, ProfilImg, Id;

    public ModelUser() {
    }

    public ModelUser(String userName, String ville, String universite,
                     String domaine, String email, String profilImg, String id) {
        UserName = userName;
        Ville = ville;
        Universite = universite;
        Domaine = domaine;
        Email = email;
        ProfilImg = profilImg;
        Id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getVille() {
        return Ville;
    }

    public void setVille(String ville) {
        Ville = ville;
    }

    public String getUniversite() {
        return Universite;
    }

    public void setUniversite(String universite) {
        Universite = universite;
    }

    public String getDomaine() {
        return Domaine;
    }

    public void setDomaine(String domaine) {
        Domaine = domaine;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getProfilImg() {
        return ProfilImg;
    }

    public void setProfilImg(String profilImg) {
        ProfilImg = profilImg;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
