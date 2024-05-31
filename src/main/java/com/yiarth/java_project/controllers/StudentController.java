package com.yiarth.java_project.controllers;

import com.yiarth.java_project.models.Eleve;

import java.sql.Date;
import java.util.List;

public class StudentController {

    /**
     * Add a student
     * @param numEleve Student number
     * @param numEcole School number
     * @param nom Student firstname
     * @param prenom Student lastname
     * @return result message
     */
    public String addStudent(String numEleve, String numEcole, String nom, String prenom, Date dateNais) {
        Eleve student = new Eleve(numEleve, numEcole, nom, prenom, dateNais);
        if (!student.isExists()) {
            if (student.isValidated()) {
                student.create();
                if (student.isCreated()) {
                    return "L'éleve est ajouté avec succès.";
                } else {
                    return "On n'a pas pu ajouter l'élève, veuillez réessayer.";
                }
            } else {
                return "Veuillez remplir tous les champs requis.";
            }
        } else {
            return "Le numéro matricule existe déjà dans la base de données.";
        }
    }

    /**
     * Get all students
     * @return List of all students
     */
    public List<String[]> getAllStudents() {
        Eleve student = new Eleve();

        return student.getAllRecords();
    }

    /**
     * Get all students in a specific school
     * @return List of all students
     */
    public List<String[]> getAllStudentsInSchool(String numEcole) {
        Eleve student = new Eleve(numEcole);

        return student.getStudentsInSchool();
    }

    /**
     * Update a student
     * @param numEleve Student number
     * @param numEcole School number
     * @param nom Student firstname
     * @param prenom Student lastname
     * @return result message
     */
    public String updateStudent(String numEleve, String numEcole, String nom, String prenom, Date dateNais) {
        Eleve student = new Eleve(numEleve, numEcole);
        if (student.isExists()) {
            student.set_nom(nom);
            student.set_prenom(prenom);
            student.set_date_nais(dateNais);
            if (student.isValidated()) {
                student.update();
                if (student.isUpdated()) {
                    return "L'élève a été mis à jour avec succès.";
                } else {
                    return "On n'a pas pu mettre à jours l'élève, veuillez réessayer.";
                }
            } else {
                return "Veuillez remplir tous les champs requis.";
            }
        } else {
            return "Aucun élève correspondant n'a été trouvé.";
        }
    }

    /**
     * Delete a student
     * @param numEleve Student number
     * @param numEcole School number
     * @return result message
     */
    public String deleteStudent(String numEleve, String numEcole) {
        Eleve student = new Eleve(numEleve, numEcole);
        if (student.isExists()) {
            student.delete();
            if (student.isDeleted()) {
                return "L'élève a été supprimé avec succès.";
            } else {
                return "On n'a pas pu supprimer l'élève, veuillez réessayer.";
            }
        } else {
            return "Aucun élève correspondant n'a été trouvé.";
        }
    }

    /**
     * Search a students in a specific school
     * @return List of filtered students
     */
    public List<String[]> searchSudentInSchool(String filterValue, String numEcole) {
        Eleve student = new Eleve(numEcole);

        return student.getFilteredStudentInSchool(filterValue);
    }

    /**
     * Search a students
     * @return List of filtered students
     */
    public List<String[]> searchSudent(String filterValue) {
        Eleve student = new Eleve();

        return student.getFilteredStudent(filterValue);
    }
}
