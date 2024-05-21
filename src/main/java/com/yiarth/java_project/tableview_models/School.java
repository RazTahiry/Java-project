package com.yiarth.java_project.tableview_models;

import javafx.beans.property.SimpleStringProperty;

public class School {

    private final SimpleStringProperty numEcole;
    private final SimpleStringProperty design;
    private final SimpleStringProperty adresse;

    public School(String numEcole, String design, String adresse) {
        this.numEcole = new SimpleStringProperty(numEcole);
        this.design = new SimpleStringProperty(design);
        this.adresse = new SimpleStringProperty(adresse);
    }

    public String getNumEcole() {
        return numEcole.get();
    }

    public SimpleStringProperty numEcoleProperty() {
        return numEcole;
    }

    public String getDesign() {
        return design.get();
    }

    public SimpleStringProperty designProperty() {
        return design;
    }

    public String getAdresse() {
        return adresse.get();
    }

    public SimpleStringProperty adresseProperty() {
        return adresse;
    }
}
