package yiarth.raz.java_project.controllers;

import yiarth.raz.java_project.models.*;

import java.util.ArrayList;
import java.util.List;

public class AverageController {

    private int _coef_total;

    public AverageController() {
        coefTotal();
    }

    /**
     * Total coefficient value
     */
    private void coefTotal() {
        Matiere subject = new Matiere();
        _coef_total = 0;

        List<String[]> subjectsList = subject.getAllRecords();
        for (String[] subjectArray : subjectsList) {
            _coef_total += Integer.parseInt(subjectArray[2]);
        }
    }

    /**
     * get the score average of a specific student
     * @param numEleve Student number
     * @param numEcole School number
     * @return double (Score average value)
     */
    public double getStudentScoreAverage(String numEleve, String numEcole) {
        Eleve student = new Eleve(numEleve, numEcole);
        double scoreAverageValue;

        if (student.isExists()) {
            scoreAverageValue = (double) student.getTotalScore() / _coef_total;

            return scoreAverageValue;
        } else {
            return 0.00;
        }
    }

    /**
     * Get the students who admitted their CEPE
     * @return List of student admitted
     */
    public List<String[]> getStudentsCepeAdmitted() {
        Eleve student = new Eleve();
        List<String[]> studentCepeAdmitted = new ArrayList<>();
        double average;

        List<String[]> studentsList = student.getAllRecords();
        for (String[] studentArray : studentsList) {
            Eleve candidate = new Eleve(studentArray[0], studentArray[1], studentArray[2], studentArray[3]);
            if (candidate.isExists()) {
                if ((average = ((double) candidate.getTotalScore() / _coef_total)) >= 9.50) {
                    String numEleve = candidate.get_num_eleve();
                    String nom = candidate.get_nom();
                    String prenom = candidate.get_prenom();
                    studentCepeAdmitted.add(new String[]{numEleve, nom, prenom, String.valueOf(average)});
                }
            }
        }
        return studentCepeAdmitted;
    }

    /**
     * Get the students who are admitted in Sixth class
     * @return List of students admitted
     */
    public List<String[]> getStudentsSixthAdmitted() {
        Eleve student = new Eleve();
        List<String[]> studentSixthAdmitted = new ArrayList<>();
        double average;

        List<String[]> studentsList = student.getAllRecords();
        for (String[] studentArray : studentsList) {
            Eleve candidate = new Eleve(studentArray[0], studentArray[1], studentArray[2], studentArray[3]);
            if (candidate.isExists()) {
                if ((average = ((double) candidate.getTotalScore() / _coef_total)) >= 12.00) {
                    String numEleve = candidate.get_num_eleve();
                    String nom = candidate.get_nom();
                    String prenom = candidate.get_prenom();
                    studentSixthAdmitted.add(new String[]{numEleve, nom, prenom, String.valueOf(average)});
                }
            }
        }
        return studentSixthAdmitted;
    }

    /**
     * Get the students who failed their CEPE
     * @return List of students who failed
     */
    public List<String[]> getFailedStudents() {
        Eleve student = new Eleve();
        List<String[]> failedStudents = new ArrayList<>();
        double average;

        List<String[]> studentsList = student.getAllRecords();
        for (String[] studentArray : studentsList) {
            Eleve candidate = new Eleve(studentArray[0], studentArray[1], studentArray[2], studentArray[3]);
            if (candidate.isExists()) {
                if ((average = ((double) candidate.getTotalScore() / _coef_total)) < 9.50) {
                    String numEleve = candidate.get_num_eleve();
                    String nom = candidate.get_nom();
                    String prenom = candidate.get_prenom();
                    failedStudents.add(new String[]{numEleve, nom, prenom, String.valueOf(average)});
                }
            }
        }
        return failedStudents;
    }
}