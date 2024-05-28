package com.yiarth.java_project.tableview_models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Score {

    private final SimpleStringProperty numEleve;
    private final SimpleStringProperty nom;
    private final SimpleStringProperty prenom;
    private final SimpleDoubleProperty noteTotale;

    public Score(String numMat, String nom, String prenom, double noteTotale) {
        this.numEleve = new SimpleStringProperty(numMat);
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.noteTotale = new SimpleDoubleProperty(noteTotale);
    }


    public String getNumEleve() {
        return numEleve.get();
    }

    public SimpleStringProperty setNumEleve() {
        return numEleve;
    }

    public String getNom() {
        return nom.get();
    }

    public SimpleStringProperty setNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom.get();
    }

    public SimpleStringProperty setPrenom() {
        return prenom;
    }

    public double getNoteTotale() {
        return noteTotale.get();
    }

    public SimpleDoubleProperty setNoteTotale() {
        return noteTotale;
    }
}
