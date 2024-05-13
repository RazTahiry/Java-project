package yiarth.raz.java_project.controllers;

import yiarth.raz.java_project.models.Eleve;

import java.util.ArrayList;
import java.util.List;

public class StudentController {

    /**
     * Add a student
     * @param numEleve Student number
     * @param numEcole School number
     * @param nom Student firstname
     * @param prenom Student lastname
     * @return String (Result message)
     */
    public String addStudent(String numEleve, String numEcole, String nom, String prenom) {
        Eleve student = new Eleve(numEleve, numEcole, nom, prenom);
        if (!student.isExists()) {
            if (student.isValidated()) {
                student.create();
                if (student.isCreated()) {
                    return "L'éleve est ajouté avec succès.";
                } else {
                    return "On n'a pas pu ajouter l'élève, veuillez réessayer s'il vous plait.";
                }
            } else {
                return "Veuillez remplir tous les champs requis s'il vous plait.";
            }
        } else {
            return "L'élève existe déjà dans la base de données.";
        }
    }

    /**
     * Get all students
     * @return List of all students
     */
    public List<String[]> getAllStudents() {
        Eleve student = new Eleve();

        List<String[]> students_list = new ArrayList<>();
        students_list = student.getAllRecords();

        return students_list;
    }

    /**
     * Update a student
     * @param numEleve Student number
     * @param numEcole School number
     * @param nom Student firstname
     * @param prenom Student lastname
     * @return String (Result message)
     */
    public String updateStudent(String numEleve, String numEcole, String nom, String prenom) {
        Eleve student = new Eleve(numEleve, numEcole);
        if (student.isExists()) {
            student.set_nom(nom);
            student.set_prenom(prenom);
            if (student.isValidated()) {
                student.update();
                if (student.isUpdated()) {
                    return "L'élève a été mis à jour avec succès.";
                } else {
                    return "On n'a pas pu mettre à jours l'élève, veuillez réessayer s'il vous plait.";
                }
            } else {
                return "Veuillez remplir tous les champs requis s'il vous plait.";
            }
        } else {
            return "Aucun élève correspondant n'a été trouvé dans la base de données.";
        }
    }

    /**
     * Delete a student
     * @param numEleve Student number
     * @param numEcole School number
     * @return String (Result message)
     */
    public String deleteStudent(String numEleve, String numEcole) {
        Eleve student = new Eleve(numEleve, numEcole);
        if (student.isExists()) {
            student.delete();
            if (student.isDeleted()) {
                return "L'élève a été supprimé avec succès.";
            } else {
                return "On n'a pas pu supprimer l'élève, veuillez réessayer s'il vous plait.";
            }
        } else {
            return "Aucun élève correspondant n'a été trouvé dans la base de données.";
        }
    }
}
