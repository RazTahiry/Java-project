package com.yiarth.java_project.controllers;

import com.yiarth.java_project.models.Matiere;

import java.util.List;

public class SubjectController {

    /**
     * Add a subject
     * @param numMat Subject number
     * @param designMat Subject name
     * @param coef Subject coefficient
     * @return result message
     */
    public String addSubject(String numMat, String designMat, int coef) {
        Matiere subject = new Matiere(numMat, designMat, coef);
        if (!subject.isExists()) {
            if (subject.isValidated()) {
                subject.create();
                if (subject.isCreated()) {
                    return "La matière est ajoutée avec succès.";
                } else {
                    return "On n'a pas pu ajouter la matière, veuillez réessayer.";
                }
            } else {
                return "Veuillez remplir tous les champs requis.";
            }
        } else {
            return "La matière existe déjà dans la base de données.";
        }
    }

    /**
     * Get all subjects
     * @return List of all subjects
     */
    public List<String[]> getAllSubjects() {
        Matiere subject = new Matiere();

        return subject.getAllRecords();
    }

    /**
     * Update a subject
     * @param numMat Subject number
     * @param designMat Subject name
     * @param coef Subject coefficient
     * @return result message
     */
    public String updateSubject(String numMat, String designMat, int coef) {
        Matiere subject = new Matiere(numMat, designMat);
        if (subject.isExists()) {
            subject.set_coef(coef);
            if (subject.isValidated()) {
                subject.update();
                if (subject.isUpdated()) {
                    return "La matière a été mise à jour avec succès.";
                } else {
                    return "On n'a pas pu mettre à jours la matière, veuillez réessayer.";
                }
            } else {
                return "Veuillez remplir tous les champs requis.";
            }
        } else {
            return "Aucune matière correspondant n'a été trouvé.";
        }
    }

    /**
     * Delete a subject
     * @param numMat Subject number
     * @param designMat Subject name
     * @return result message
     */
    public String deleteSubject(String numMat, String designMat) {
        Matiere subject = new Matiere(numMat, designMat);
        if (subject.isExists()) {
            subject.delete();
            if (subject.isDeleted()) {
                return "La matière a été supprimée avec succès.";
            } else {
                return "On n'a pas pu supprimer la matière, veuillez réessayer.";
            }
        } else {
            return "Aucune matière correspondant n'a été trouvé.";
        }
    }
}
