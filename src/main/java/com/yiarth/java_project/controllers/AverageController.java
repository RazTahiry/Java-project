package com.yiarth.java_project.controllers;

import com.yiarth.java_project.models.Ecole;
import com.yiarth.java_project.models.Eleve;
import com.yiarth.java_project.models.Matiere;

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
        // Function call for every instantiation of the class AverageController to get the coefficient total
        coefTotal();

        _average_deliberation = 9.75; // Set the value of average deliberation manually
    }

    /**
     * Get the average deliberation value
     * @return value of average deliberation
     */
    public double get_average_deliberation() {
        return _average_deliberation;
    }

    /**
     * Get the total value of coefficient of all subjects
     * @return value of coefficient total
     */
    public int get_coef_total() {
        return _coef_total;
    }

    /**
     * Calculate then set the total value of coefficient of all subjects
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
     * Set the score average obtained by a student
     * @param numEleve Student number
     * @param numEcole School number
     */
    public void updateAverage(String numEleve, String numEcole) {
        Eleve student = new Eleve(numEleve, numEcole);

        // Divide the total score obtained by the student
        // by the total value of coefficient of all subjects,
        // to get the score average value
        double totalScore = student.getTotalScore();
        double average = totalScore / _coef_total;

        student.updateAverageScore(average);
    }

    /**
     * Set the total score obtained by a student
     * @param numEleve Student number
     * @param numEcole School number
     */
    public void updateTotalScore(String numEleve, String numEcole) {
        Eleve student = new Eleve(numEleve, numEcole);
        double totalScore = student.getTotalScore();
        student.updateTotalScore(totalScore);
    }

    /**
     * Get the total score of a specific student
     * @param numEleve Student number
     * @param numEcole School number
     * @return value of total score
     */
    public double getTotalScore(String numEleve, String numEcole) {
        Eleve student = new Eleve(numEleve, numEcole);

        return Double.parseDouble(student.getStudent()[5]);
    }

    /**
     * Get the information of a specific student
     * @param numEleve Student number
     * @param numEcole School number
     * @return array of student information
     */
    public String[] getStudent(String numEleve, String numEcole) {
        Eleve student = new Eleve(numEleve, numEcole);
        return student.getStudent();
    }

    /**
     * Get the students who were admitted to their CEPE
     * @return List of students who were admitted
     */
    public List<String[]> getStudentsCepeAdmitted() {
        Eleve student = new Eleve();
        List<String[]> studentCepeAdmitted = new ArrayList<>();

        List<String[]> studentsList = student.getAllRecordsOrderByMerit();
        for (String[] studentArray : studentsList) {
            Eleve candidate = new Eleve(studentArray[0], studentArray[1], studentArray[2], studentArray[3],
                    Date.valueOf(studentArray[4]), Double.parseDouble(studentArray[5]), Double.parseDouble(studentArray[6]));
            if (candidate.isExists()) {
                double moyenne = candidate.get_moyenne();
                if (moyenne >= _average_deliberation) {
                    Ecole school = new Ecole(candidate.get_num_ecole());
                    String ecole = school.getDesignation();

                    String numEleve = candidate.get_num_eleve();
                    String nom = candidate.get_nom();
                    String prenom = candidate.get_prenom();

                    studentCepeAdmitted.add(new String[]{numEleve, nom, prenom, ecole, String.valueOf(moyenne)});
                }
            }
        }
        return studentCepeAdmitted;
    }

    /**
     * Get the students who were admitted to Sixth class
     * @return List of students who were admitted
     */
    public List<String[]> getStudentsSixthAdmitted() {
        Eleve student = new Eleve();
        List<String[]> studentSixthAdmitted = new ArrayList<>();

        List<String[]> studentsList = student.getAllRecordsOrderByMerit();
        for (String[] studentArray : studentsList) {
            Eleve candidate = new Eleve(studentArray[0], studentArray[1], studentArray[2], studentArray[3],
                    Date.valueOf(studentArray[4]), Double.parseDouble(studentArray[5]), Double.parseDouble(studentArray[6]));
            if (candidate.isExists()) {
                double moyenne = candidate.get_moyenne();
                if (moyenne >= 12.00) {
                    Ecole school = new Ecole(candidate.get_num_ecole());
                    String ecole = school.getDesignation();

                    String numEleve = candidate.get_num_eleve();
                    String nom = candidate.get_nom();
                    String prenom = candidate.get_prenom();

                    studentSixthAdmitted.add(new String[]{numEleve, nom, prenom, ecole, String.valueOf(moyenne)});
                }
            }
        }
        return studentSixthAdmitted;
    }

    /**
     * Get the students who failed to their CEPE
     * @return List of students who failed
     */
    public List<String[]> getFailedStudents() {
        Eleve student = new Eleve();
        List<String[]> failedStudents = new ArrayList<>();

        List<String[]> studentsList = student.getAllRecordsOrderByMerit();
        for (String[] studentArray : studentsList) {
            Eleve candidate = new Eleve(studentArray[0], studentArray[1], studentArray[2], studentArray[3],
                    Date.valueOf(studentArray[4]), Double.parseDouble(studentArray[5]), Double.parseDouble(studentArray[6]));
            if (candidate.isExists()) {
                double moyenne = candidate.get_moyenne();
                if (moyenne < _average_deliberation) {
                    Ecole school = new Ecole(candidate.get_num_ecole());
                    String ecole = school.getDesignation();

                    String numEleve = candidate.get_num_eleve();
                    String nom = candidate.get_nom();
                    String prenom = candidate.get_prenom();

                    failedStudents.add(new String[]{numEleve, nom, prenom, ecole, String.valueOf(moyenne)});
                }
            }
        }
        return failedStudents;
    }
}
