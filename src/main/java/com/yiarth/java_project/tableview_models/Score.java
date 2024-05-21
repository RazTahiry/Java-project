package com.yiarth.java_project.tableview_models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Score {

    private final SimpleStringProperty anneeScolaire;
    private final SimpleStringProperty numMat;
    private final SimpleStringProperty numEleve;
    private final SimpleIntegerProperty note;

    public Score(String anneeScolaire, String numMat, String numEleve, int note) {
        this.anneeScolaire = new SimpleStringProperty(anneeScolaire);
        this.numMat = new SimpleStringProperty(numMat);
        this.numEleve = new SimpleStringProperty(numEleve);
        this.note = new SimpleIntegerProperty(note);
    }

    public String getAnneeScolaire() {
        return anneeScolaire.get();
    }

    public SimpleStringProperty setAnneeScolaire() {
        return anneeScolaire;
    }

    public String getNumMat() {
        return numMat.get();
    }

    public SimpleStringProperty setNumMat() {
        return numMat;
    }

    public String getNumEleve() {
        return numEleve.get();
    }

    public SimpleStringProperty setNumEleve() {
        return numEleve;
    }

    public int getNote() {
        return note.get();
    }

    public SimpleIntegerProperty setNote() {
        return note;
    }
}
