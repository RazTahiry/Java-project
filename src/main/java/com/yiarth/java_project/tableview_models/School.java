package com.yiarth.java_project.tableview_models;

import javafx.beans.property.SimpleStringProperty;

public class School {
    /**
     * This class is a model of the school TableView
     * <p>
     * Each private attribute of this class corresponds to a column in the TableView user interface.
     */

    private final SimpleStringProperty numEcole;
    private final SimpleStringProperty design;
    private final SimpleStringProperty adresse;

    /**
     * Constructor
     */

    public School(String numEcole, String design, String adresse) {
        this.numEcole = new SimpleStringProperty(numEcole);
        this.design = new SimpleStringProperty(design);
        this.adresse = new SimpleStringProperty(adresse);
    }

    /**
     * Getters
     * @return private attributes value
     */

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
