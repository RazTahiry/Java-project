package yiarth.raz.java_project.controllers;

import yiarth.raz.java_project.models.Matiere;

import java.util.ArrayList;
import java.util.List;

public class SubjectController {

    /**
     * Add a subject
     * @param numMat Subject number
     * @param designMat Subject name
     * @param coef Subject coefficient
     * @return String (Result message)
     */
    public String addSubject(String numMat, String designMat, int coef) {
        Matiere subject = new Matiere(numMat, designMat, coef);
        if (!subject.isExists()) {
            if (subject.isValidated()) {
                subject.create();
                if (subject.isCreated()) {
                    return "La matière est ajoutée avec succès.";
                } else {
                    return "On n'a pas pu ajouter la matière, veuillez réessayer s'il vous plait.";
                }
            } else {
                return "Veuillez remplir tous les champs requis s'il vous plait.";
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

        List<String[]> subjects_list = new ArrayList<>();
        subjects_list = subject.getAllRecords();

        return subjects_list;
    }

    /**
     * Update a subject
     * @param numMat Subject number
     * @param designMat Subject name
     * @param coef Subject coefficient
     * @return String (Result message)
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
                    return "On n'a pas pu mettre à jours la matière, veuillez réessayer s'il vous plait.";
                }
            } else {
                return "Veuillez remplir tous les champs requis s'il vous plait.";
            }
        } else {
            return "Aucune matière correspondant n'a été trouvé dans la base de données.";
        }
    }

    /**
     * Delete a subject
     * @param numMat Subject number
     * @param designMat Subject name
     * @return String (Result message)
     */
    public String deleteSubject(String numMat, String designMat) {
        Matiere subject = new Matiere(numMat, designMat);
        if (subject.isExists()) {
            subject.delete();
            if (subject.isDeleted()) {
                return "La matière a été supprimée avec succès.";
            } else {
                return "On n'a pas pu supprimer la matière, veuillez réessayer s'il vous plait.";
            }
        } else {
            return "Aucune matière correspondant n'a été trouvé dans la base de données.";
        }
    }
}
