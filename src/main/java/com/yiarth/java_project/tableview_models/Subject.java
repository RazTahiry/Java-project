package com.yiarth.java_project.tableview_models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Subject {

    private final SimpleStringProperty numMat;
    private final SimpleStringProperty design;
    private final SimpleIntegerProperty coef;

    public Subject(String numMat, String design, int coef) {
        this.numMat = new SimpleStringProperty(numMat);
        this.design = new SimpleStringProperty(design);
        this.coef = new SimpleIntegerProperty(coef);
    }

    public String getNumMat() {
        return numMat.get();
    }

    public SimpleStringProperty numMatProperty() {
        return numMat;
    }

    public String getDesign() {
        return design.get();
    }

    public SimpleStringProperty designProperty() {
        return design;
    }

    public int getCoef() {
        return coef.get();
    }

    public SimpleIntegerProperty coefProperty() {
        return coef;
    }
}
