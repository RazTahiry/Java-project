package com.yiarth.java_project.controllers;

import com.yiarth.java_project.models.Ecole;

import java.util.ArrayList;
import java.util.List;

public class SchoolController {

    /**
     * Add a school
     * @param numEcole School number
     * @param design School name
     * @param adresse School address
     * @return String (Result message)
     */
    public String addSchool(String numEcole, String design, String adresse) {
        Ecole school = new Ecole(numEcole, design, adresse);
        if (!school.isExists()) {
            if (school.isValidated()) {
                school.create();
                if (school.isCreated()) {
                    return "L'école est ajoutée avec succès.";
                } else {
                    return "On n'a pas pu ajouter l'école, veuillez réessayer.";
                }
            } else {
                return "Veuillez remplir tous les champs requis.";
            }
        } else {
            return "Le numéro école existe déjà dans la base de données.";
        }
    }

    /**
     * Get all schools
     * @return List of all schools
     */
    public List<String[]> getAllSchools() {
        Ecole school = new Ecole();

        List<String[]> schools_list = new ArrayList<>();
        schools_list = school.getAllRecords();

        return schools_list;
    }

    /**
     * Update a school
     * @param numEcole School numbe
     * @param design School name
     * @param adresse School address
     * @return String (Result message)
     */
    public String updateSchool(String numEcole, String design, String adresse) {
        Ecole school = new Ecole(numEcole);
        if (school.isExists()) {
            school.set_design(design);
            school.set_adresse(adresse);
            if (school.isValidated()) {
                school.update();
                if (school.isUpdated()) {
                    return "L'école a été mise à jour avec succès.";
                } else {
                    return "On n'a pas pu mettre à jours l'école, veuillez réessayer.";
                }
            } else {
                return "Veuillez remplir tous les champs requis.";
            }
        } else {
            return "Aucune école correspondant n'a été trouvé.";
        }
    }

    /**
     * Delete a school
     * @param numEcole School number
     * @return String (Result message)
     */
    public String deleteSchool(String numEcole) {
        Ecole school = new Ecole(numEcole);
        if (school.isExists()) {
            school.delete();
            if (school.isDeleted()) {
                return "L'école a été supprimée avec succès.";
            } else {
                return "On n'a pas pu supprimer l'école, veuillez réessayer.";
            }
        } else {
            return "Aucune école correspondant n'a été trouvé.";
        }
    }
}
