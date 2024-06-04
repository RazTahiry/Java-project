package com.yiarth.java_project.tableview_models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class StudentCepeAdmitted {
    /**
     * This class is a model of the TableView for students who were admitted to their CEPE.
     * <p>
     * Each private attribute of this class corresponds to a column in the TableView user interface.
     */

    private final SimpleStringProperty numEleve;
    private final SimpleStringProperty nom;
    private final SimpleStringProperty prenom;
    private final SimpleStringProperty ecole;
    private final SimpleDoubleProperty moyenne;

    /**
     * Constructor
     */

    public StudentCepeAdmitted(String numEleve, String nom, String prenom, String ecole, double moyenne) {
        this.numEleve = new SimpleStringProperty(numEleve);
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.ecole = new SimpleStringProperty(ecole);
        this.moyenne = new SimpleDoubleProperty(moyenne);
    }

    /**
     * Getters
     * @return private attributes value
     */

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

    public String getEcole() {
        return ecole.get();
    }
    public SimpleStringProperty setEcole() {
        return ecole;
    }

    public double getMoyenne() {
        return moyenne.get();
    }
    public SimpleDoubleProperty setMoyenne() {
        return moyenne;
    }
}
