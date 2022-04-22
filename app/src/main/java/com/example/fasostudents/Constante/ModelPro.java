package com.example.fasostudents.Constante;

public class ModelPro {
    String NomPro, Membres, CompetencesRequises, Descriptions;

    public ModelPro() {}

    public ModelPro(String nomPro, String membres, String competencesRequises, String descriptions) {
        NomPro = nomPro;
        Membres = membres;
        CompetencesRequises = competencesRequises;
        Descriptions = descriptions;
    }

    public String getNomPro() {
        return NomPro;
    }

    public void setNomPro(String nomPro) {
        NomPro = nomPro;
    }

    public String getMembres() {
        return Membres;
    }

    public void setMembres(String membres) {
        Membres = membres;
    }

    public String getCompetencesRequises() {
        return CompetencesRequises;
    }

    public void setCompetencesRequises(String competencesRequises) {
        CompetencesRequises = competencesRequises;
    }

    public String getDescriptions() {
        return Descriptions;
    }

    public void setDescriptions(String descriptions) {
        Descriptions = descriptions;
    }
}
