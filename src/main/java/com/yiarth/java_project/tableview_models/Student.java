package com.yiarth.java_project.tableview_models;

import javafx.beans.property.SimpleStringProperty;

public class Student {
    /**
     * This class is a model of the student TableView
     * <p>
     * Each private attribute of this class corresponds to a column in the TableView user interface.
     */

    private final SimpleStringProperty numEleve;
    private final SimpleStringProperty numEcole;
    private final SimpleStringProperty nom;
    private final SimpleStringProperty prenom;
    private final SimpleStringProperty dateNais;

    /**
     * Constructor
     */

    public Student(String numEleve, String numEcole, String nom, String prenom, String dateNais) {
        this.numEleve = new SimpleStringProperty(numEleve);
        this.numEcole = new SimpleStringProperty(numEcole);
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.dateNais = new SimpleStringProperty(dateNais);
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

    public String getNumEcole() {
        return numEcole.get();
    }
    public SimpleStringProperty setNumEcole() {
        return numEcole;
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

    public String getDateNais() {
        return dateNais.get();
    }
    public SimpleStringProperty setDateNais() {
        return dateNais;
    }
}
