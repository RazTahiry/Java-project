package com.yiarth.java_project.controllers;

import com.yiarth.java_project.models.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteController {

    /**
     * Add a score
     * @param anneeScolaire School year
     * @param numEleve Student number
     * @param numMat Subject number
     * @param score Weighted rating
     * @return result message
     */
    public String addNote(String anneeScolaire, String numEleve, String numMat, double score) {
        Note note = new Note(anneeScolaire, numEleve, numMat, score);
        if (!note.isExists()) {
            if (note.isValidated()) {
                note.create();
                if (note.isCreated()) {
                    return "La note est ajoutée avec succès.";
                } else {
                    return "On n'a pas pu ajouter la note, veuillez réessayer.";
                }
            } else {
                return "Veuillez remplir tous les champs requis.";
            }
        } else {
            return "La note existe déjà dans la base de données.";
        }
    }

    /**
     * Get all scores
     * @return List of all scores
     */
    public List<String[]> getAllScores() {
        Note note = new Note();

        List<String[]> scores_list = new ArrayList<>();
        scores_list = note.getAllRecords();

        return scores_list;
    }

    /**
     * Update a score
     * @param anneeScolaire School year
     * @param numEleve Student number
     * @param numMat Subject number
     * @param score Weighted rating
     * @return result message
     */
    public String updateNote(String anneeScolaire, String numEleve, String numMat, double score) {
        Note note = new Note(numEleve, numMat);
        if (note.isExists()) {
            note.set_annee_scolaire(anneeScolaire);
            note.set_note(score);
            if (note.isValidated()) {
                note.update();
                if (note.isUpdated()) {
                    return "La note a été mise à jour avec succès.";
                } else {
                    return "On n'a pas pu mettre à jours la note, veuillez réessayer.";
                }
            } else {
                return "Veuillez remplir tous les champs requis.";
            }
        } else {
            return "Aucune note correspondant n'a été trouvé.";
        }
    }

    /**
     * Delete a score
     * @param numEleve Student number
     * @param numMat Subject number
     * @return result message
     */
    public String deleteNote(String numEleve, String numMat) {
        Note note = new Note(numEleve, numMat);
        if (note.isExists()) {
            note.delete();
            if (note.isDeleted()) {
                return "La note a été supprimée avec succès.";
            } else {
                return "On n'a pas pu supprimer la note, veuillez réessayer.";
            }
        } else {
            return "Aucune note correspondant n'a été trouvé.";
        }
    }
}
