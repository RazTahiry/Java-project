package com.yiarth.java_project.controllers;

import com.yiarth.java_project.models.Eleve;
import com.yiarth.java_project.models.Matiere;
import com.yiarth.java_project.models.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class AverageController {

    private int _coef_total;
    private final double _average_deliberation;

    /**
     * Constructor
     */
    public AverageController() {
        coefTotal();
        _average_deliberation = 9.75;
    }

    /**
     * Get the average deliberation value
     * @return double (average deliberation)
     */
    public double get_average_deliberation() {
        return _average_deliberation;
    }

    /**
     * Get the total coefficient value
     * @return int (coefficient total)
     */
    public int get_coef_total() {
        return _coef_total;
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
            return 0;
        }
    }

    /**
     * get the total score of a specific student
     * @param numEleve Student number
     * @param numEcole School number
     * @return double (Score average value)
     */
    public double getTotalScore(String numEleve, String numEcole) {
        Eleve student = new Eleve(numEleve, numEcole);

        return  student.getTotalScore();
    }

    /**
     * Get all students with their average note value
     * @return List of students
     */
    public List<String[]> getAllStudents(String numEcole) {
        Eleve student = new Eleve(numEcole);
        List<String[]> studentList = new ArrayList<>();
        double average;
        List<String[]> studentsList = student.getStudentsInSchool();
        for (String[] studentArray : studentsList) {
            Eleve candidate = new Eleve(studentArray[0], studentArray[1], studentArray[2], studentArray[3], Date.valueOf(studentArray[4]));
            if (candidate.isExists()) {
                average = ((double) candidate.getTotalScore() / _coef_total);
                String numEleve = candidate.get_num_eleve();
                String nom = candidate.get_nom();
                String prenom = candidate.get_prenom();
                studentList.add(new String[]{numEleve, nom, prenom, String.valueOf(average)});
            }
        }
        return studentList;
    }

    /**
     * Get the students who admitted their CEPE
     * @return List of student admitted
     */
    public List<String[]> getStudentsCepeAdmitted(String numEcole) {
        Eleve student = new Eleve(numEcole);
        List<String[]> studentCepeAdmitted = new ArrayList<>();
        double average;

        List<String[]> studentsList = student.getStudentsInSchool();
        for (String[] studentArray : studentsList) {
            Eleve candidate = new Eleve(studentArray[0], studentArray[1], studentArray[2], studentArray[3], Date.valueOf(studentArray[4]));
            if (candidate.isExists()) {
                if ((average = ((double) candidate.getTotalScore() / _coef_total)) >= _average_deliberation) {
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
    public List<String[]> getStudentsSixthAdmitted(String numEcole) {
        Eleve student = new Eleve(numEcole);
        List<String[]> studentSixthAdmitted = new ArrayList<>();
        double average;

        List<String[]> studentsList = student.getStudentsInSchool();
        for (String[] studentArray : studentsList) {
            Eleve candidate = new Eleve(studentArray[0], studentArray[1], studentArray[2], studentArray[3], Date.valueOf(studentArray[4]));
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
    public List<String[]> getFailedStudents(String numEcole) {
        Eleve student = new Eleve(numEcole);
        List<String[]> failedStudents = new ArrayList<>();
        double average;

        List<String[]> studentsList = student.getStudentsInSchool();
        for (String[] studentArray : studentsList) {
            Eleve candidate = new Eleve(studentArray[0], studentArray[1], studentArray[2], studentArray[3], Date.valueOf(studentArray[4]));
            if (candidate.isExists()) {
                if ((average = ((double) candidate.getTotalScore() / _coef_total)) < _average_deliberation) {
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
