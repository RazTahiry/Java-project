package com.yiarth.java_project.controllers;

import com.yiarth.java_project.models.Ecole;
import com.yiarth.java_project.tableview_models.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private Timeline timing;

    @FXML private AnchorPane home_anchor_pane;
    @FXML private AnchorPane result_anchor_pane;
    @FXML private AnchorPane score_anchor_pane;
    @FXML private AnchorPane student_anchor_pane;
    @FXML private AnchorPane subject_anchor_pane;
    @FXML private AnchorPane school_anchor_pane;

    @FXML private Label bar_title;

    @FXML private ComboBox<String> school_cbox_student;
    @FXML private ComboBox<String> school_score_cbx;

    /**
     * School TextFields
     */

    @FXML private TextField num_ecole_input;
    @FXML private TextField design_ecole_input;
    @FXML private TextField adresse_ecole_input;
    @FXML private Label school_result_message;

    /**
     * Student TextFields
     */

    @FXML private ComboBox<String> student_school_combobox;
    @FXML private TextField student_num_input;
    @FXML private TextField student_firstname_input;
    @FXML private TextField student_lastname_input;
    @FXML private Label student_result_message;
    @FXML private DatePicker student_datenais_input;

    /**
     * Subject TextFields
     */

    @FXML private TextField c_matiere_input;
    @FXML private TextField design_matiere_input;
    @FXML private TextField coef_input;
    @FXML private Label subject_result_message;

    /**
     * Score TextFields
     */

    @FXML private TextField school_year_input;
    @FXML private ComboBox<String> score_student_input;
    @FXML private ComboBox<String> score_subject_input;
    @FXML private TextField score_input;
    @FXML private Label score_result_message;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        handleSideBarButtonClick(1);

        // Show data in a TableView (school and subject TableView)
        dataToSchoolTb();
        dataToSubjectTb();

        // Add data to a combobox
        studentFormCombobox(); // School combobox in student form
        scoreSubjectCombobox(); // Subject combobox in score form

        // Show data on result TableView
        showCepeAdmittedStudents();
        showSixthAdmittedStudents();
        showFailedStudents();

        // The selection method on a different TableView
        selectTableStudent();
        selectTableSubject();
        selectTableSchool();
        selectTableScore();

        // Button and Input disabled by default
        disabledButton();
        disabledInput();

        // The score input value depends on subject combobox value (In score form)
        subjectToScore();

        // Add data to school combobox filter
        dataToSchoolCbx(); // School combobox filter in student section
        dataToSchoolScoreCbx(); // School combobox filter in score section

        // Action handling on combobox change
        schoolCombobox(); // School combobox filter in student section
        schoolComboboxScore(); // School combobox filter in score section

        // Search methods
        searchStudent();
    }

    /**
     * Handle the action on sidebar button click
     */

    @FXML private Button home_button_sidebar;
    @FXML private Button result_button_sidebar;
    @FXML private Button score_button_sidebar;
    @FXML private Button student_button_sidebar;
    @FXML private Button subject_button_sidebar;
    @FXML private Button school_button_sidebar;

    private void handleSideBarButtonClick(int anchorPaneNumber) {
        home_anchor_pane.setVisible(false);
        result_anchor_pane.setVisible(false);
        score_anchor_pane.setVisible(false);
        student_anchor_pane.setVisible(false);
        subject_anchor_pane.setVisible(false);
        school_anchor_pane.setVisible(false);

        home_button_sidebar.getStyleClass().remove("sidebar_button_active");
        result_button_sidebar.getStyleClass().remove("sidebar_button_active");
        score_button_sidebar.getStyleClass().remove("sidebar_button_active");
        student_button_sidebar.getStyleClass().remove("sidebar_button_active");
        subject_button_sidebar.getStyleClass().remove("sidebar_button_active");
        school_button_sidebar.getStyleClass().remove("sidebar_button_active");

        switch (anchorPaneNumber) {
            case 1:
                home_anchor_pane.setVisible(true);
                bar_title.setText("Accueil");
                home_button_sidebar.getStyleClass().add("sidebar_button_active");
                break;
            case 2:
                result_anchor_pane.setVisible(true);
                bar_title.setText("Résultats");
                result_button_sidebar.getStyleClass().add("sidebar_button_active");
                break;
            case 3:
                score_anchor_pane.setVisible(true);
                bar_title.setText("Notes");
                score_button_sidebar.getStyleClass().add("sidebar_button_active");
                break;
            case 4:
                student_anchor_pane.setVisible(true);
                bar_title.setText("Elèves");
                student_button_sidebar.getStyleClass().add("sidebar_button_active");
                break;
            case 5:
                subject_anchor_pane.setVisible(true);
                bar_title.setText("Matières");
                subject_button_sidebar.getStyleClass().add("sidebar_button_active");
                break;
            case 6:
                school_anchor_pane.setVisible(true);
                bar_title.setText("Ecoles");
                school_button_sidebar.getStyleClass().add("sidebar_button_active");
                break;
            default:
                break;
        }
    }
    @FXML
    public void home_button_sidebar() {
        handleSideBarButtonClick(1);
    }
    @FXML
    public void result_button_sidebar() {
        handleSideBarButtonClick(2);
    }
    @FXML
    public void score_button_sidebar() {
        handleSideBarButtonClick(3);
    }
    @FXML
    public void student_button_sidebar() {
        handleSideBarButtonClick(4);
    }
    @FXML
    public void subject_button_sidebar() {
        handleSideBarButtonClick(5);
    }
    @FXML
    public void school_button_sidebar() {
        handleSideBarButtonClick(6);
    }

    /**
     * Disabled button by default
     */
    private void disabledButton() {
        update_school_btn.setDisable(true);
        update_student_btn.setDisable(true);
        update_subject_btn.setDisable(true);
        update_score_btn.setDisable(true);

        delete_school_btn.setDisable(true);
        delete_student_btn.setDisable(true);
        delete_subject_btn.setDisable(true);
        delete_score_btn.setDisable(true);
    }

    /**
     * Disabled input by default
     */
    private void disabledInput() {
        school_year_input.setText("2022-2023");
        school_year_input.setEditable(false);
    }

    /**
     * School button methods
     */

    @FXML private Button add_school_btn;
    @FXML private Button update_school_btn;
    @FXML private Button delete_school_btn;
    @FXML
    public void add_school() {
        SchoolController school = new SchoolController();
        String numEcole = num_ecole_input.getText();
        String designEcole = design_ecole_input.getText();
        String adresseEcole = adresse_ecole_input.getText();

        String resultMessage = school.addSchool(numEcole,designEcole,adresseEcole);
        if(resultMessage.contains("ajoutée")) {
            cancel_school();
            studentFormCombobox();
            dataToSchoolCbx();
            dataToSchoolCbx();
            dataToSchoolScoreCbx();
        }
        school_result_message.setText(resultMessage);
        timing = new Timeline(new KeyFrame(Duration.seconds(3), _ -> school_result_message.setText("")));
        timing.play();
    }
    @FXML
    public void update_school() {
        SchoolController school = new SchoolController();
        String numEcole = num_ecole_input.getText();
        String designEcole = design_ecole_input.getText();
        String adresseEcole = adresse_ecole_input.getText();

        String resultMessage = school.updateSchool(numEcole,designEcole,adresseEcole);
        if (resultMessage.contains("mise à jour")) {
            cancel_school();
            studentFormCombobox();
            dataToSchoolCbx();
            dataToSchoolCbx();
            dataToSchoolScoreCbx();
        }
        school_result_message.setText(resultMessage);
        timing = new Timeline(new KeyFrame(Duration.seconds(3), _ -> school_result_message.setText("")));
        timing.play();
    }
    @FXML
    private void delete_school(String numEcole) {
        SchoolController school = new SchoolController();

        String resultMessage = school.deleteSchool(numEcole);
        if (resultMessage.contains("supprimée")) {
            cancel_school();
            studentFormCombobox();
            dataToSchoolCbx();
            dataToSchoolCbx();
            dataToSchoolScoreCbx();
        }
        school_result_message.setText(resultMessage);
        timing = new Timeline(new KeyFrame(Duration.seconds(3), _ -> school_result_message.setText("")));
        timing.play();
    }
    // Clear all inputs value in school form
    @FXML
    public void cancel_school() {
        dataToSchoolTb();
        num_ecole_input.clear();
        design_ecole_input.clear();
        adresse_ecole_input.clear();

        num_ecole_input.setEditable(true);

        add_school_btn.setDisable(false);
        update_school_btn.setDisable(true);
        delete_school_btn.setDisable(true);
    }
    // Show confirmation message on school delete button click
    @FXML
    public void handleSchoolDelete() {
        String numEcole = num_ecole_input.getText();

        if (!numEcole.isEmpty()) {
            // Show a confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Etes-vous sûr de vouloir supprimer cette école?");

            // Capture the user's response
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                delete_school(numEcole);
            }
        } else {
            school_result_message.setText("Aucune école sélectionnée.");
            timing = new Timeline(new KeyFrame(Duration.seconds(3), _ -> school_result_message.setText("")));
            timing.play();
        }
    }

    /**
     * Student button methods
     */

    @FXML private Button add_student_btn;
    @FXML private Button update_student_btn;
    @FXML private Button delete_student_btn;
    @FXML
    public void add_student() {
        StudentController student = new StudentController();
        String numEcole = student_school_combobox.getValue();
        String numEleve = student_num_input.getText();
        String nom = student_firstname_input.getText();
        String prenom = student_lastname_input.getText();

        if (student_datenais_input.getValue() != null) {
            LocalDate dateNais = student_datenais_input.getValue();
            LocalDate currentDate = LocalDate.now();

            if (!dateNais.isAfter(currentDate)) {
                Date sqlDate = Date.valueOf(dateNais);
                String resultMessage = student.addStudent(numEleve,numEcole,nom,prenom,sqlDate);
                if (resultMessage.contains("ajouté")) {
                    cancel_student();
                    scoreStudentCombobox(school_score_cbx.getValue());
                }
                student_result_message.setText(resultMessage);
            } else {
                student_result_message.setText("La date de naissance ne peut pas être dans le futur.");
            }
        } else {
            student_result_message.setText("Veuillez remplir tous les champs récquis.");
        }
        timing = new Timeline(new KeyFrame(Duration.seconds(3), _ -> student_result_message.setText("")));
        timing.play();
    }
    @FXML
    public void update_student() {
        StudentController student = new StudentController();
        String numEcole = student_school_combobox.getValue();
        String numEleve = student_num_input.getText();
        String nom = student_firstname_input.getText();
        String prenom = student_lastname_input.getText();

        if (student_datenais_input.getValue() != null) {
            LocalDate dateNais = student_datenais_input.getValue();
            LocalDate currentDate = LocalDate.now();

            if (!dateNais.isAfter(currentDate)) {
                Date sqlDate = Date.valueOf(dateNais);
                String resultMessage = student.updateStudent(numEleve,numEcole,nom,prenom,sqlDate);
                if (resultMessage.contains("mis à jour")) {
                    cancel_student();
                    scoreStudentCombobox(school_score_cbx.getValue());
                }
                student_result_message.setText(resultMessage);
            } else {
                student_result_message.setText("La date de naissance ne peut pas être dans le futur.");
            }

        } else {
            student_result_message.setText("Veuillez remplir tous les champs récquis.");
        }
        timing = new Timeline(new KeyFrame(Duration.seconds(3), _ -> student_result_message.setText("")));
        timing.play();
    }
    @FXML
    private void delete_student(String numEcole, String numEleve) {
        StudentController student = new StudentController();

        String resultMessage = student.deleteStudent(numEleve,numEcole);
        if (resultMessage.contains("supprimé")) {
            cancel_student();
            scoreStudentCombobox(school_score_cbx.getValue());
        }
        student_result_message.setText(resultMessage);
        timing = new Timeline(new KeyFrame(Duration.seconds(3), _ -> student_result_message.setText("")));
        timing.play();
    }
    // Clear all inputs value in student form
    @FXML
    public void cancel_student() {
        if (Objects.equals(school_cbox_student.getValue(), "Toutes les écoles")) {
            dataToStudentTbAll();
        } else {
            dataToStudentTb(school_cbox_student.getValue());
        }
        student_search.clear();
        student_num_input.clear();
        student_firstname_input.clear();
        student_lastname_input.clear();
        student_datenais_input.setValue(null);

        student_school_combobox.setDisable(false);
        student_num_input.setEditable(true);

        add_student_btn.setDisable(false);
        update_student_btn.setDisable(true);
        delete_student_btn.setDisable(true);
    }
    // Show confirmation message on student delete button click
    @FXML
    public void handleStudentDelete() {
        String numEcole = student_school_combobox.getValue();
        String numEleve = student_num_input.getText();

        if (numEcole != null && !numEleve.isEmpty()) {
            // Show a confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Etes-vous sûr de vouloir supprimer cet élève?");

            // Capture the user's response
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                delete_student(numEcole, numEleve);
            }
        } else {
            System.out.println(numEcole);
            System.out.println(numEleve);
            student_result_message.setText("Aucun élève sélectionné.");
            timing = new Timeline(new KeyFrame(Duration.seconds(3), _ -> student_result_message.setText("")));
            timing.play();
        }
    }

    /**
     * Subject button methods
     */

    @FXML private Button add_subject_btn;
    @FXML private Button update_subject_btn;
    @FXML private Button delete_subject_btn;
    @FXML
    public void add_subject() {
        SubjectController subject = new SubjectController();
        String numMat = c_matiere_input.getText();
        String designMat = design_matiere_input.getText();
        String coef = coef_input.getText();

        if (!numMat.isEmpty() && !designMat.isEmpty() && !coef.isEmpty()) {
            try {
                String resultMessage = subject.addSubject(numMat, designMat, Integer.parseInt(coef));
                if (resultMessage.contains("ajoutée")) {
                    cancel_subject();
                    scoreSubjectCombobox();
                }
                subject_result_message.setText(resultMessage);
                timing = new Timeline(new KeyFrame(Duration.seconds(3), _ -> subject_result_message.setText("")));
                timing.play();
            } catch (NumberFormatException e) {
                subject_result_message.setText("Coefficient Invalide.");
                timing = new Timeline(new KeyFrame(Duration.seconds(3), _ -> subject_result_message.setText("")));
                timing.play();
            }
        } else {
            subject_result_message.setText("Veuillez remplir tous les champs requis.");
            timing = new Timeline(new KeyFrame(Duration.seconds(3), _ -> subject_result_message.setText("")));
            timing.play();
        }
    }
    @FXML
    public void update_subject() {
        SubjectController subject = new SubjectController();
        String numMat = c_matiere_input.getText();
        String designMat = design_matiere_input.getText();
        String coef = coef_input.getText();

        try {
            String resultMessage = subject.updateSubject(numMat, designMat, Integer.parseInt(coef));
            if (resultMessage.contains("mise à jour")) {
                cancel_subject();
                scoreSubjectCombobox();
            }
            subject_result_message.setText(resultMessage);
            timing = new Timeline(new KeyFrame(Duration.seconds(3), _ -> subject_result_message.setText("")));
            timing.play();
        } catch (NumberFormatException e) {
            subject_result_message.setText("Coefficient Invalide.");
            timing = new Timeline(new KeyFrame(Duration.seconds(3), _ -> subject_result_message.setText("")));
            timing.play();
        }
    }
    @FXML
    private void delete_subject(String numMat, String designMat) {
        SubjectController subject = new SubjectController();

        String resultMessage = subject.deleteSubject(numMat, designMat);
        if (resultMessage.contains("supprimée")) {
            cancel_subject();
            scoreSubjectCombobox();
        }
        subject_result_message.setText(resultMessage);
        timing = new Timeline(new KeyFrame(Duration.seconds(3), _ -> subject_result_message.setText("")));
        timing.play();
    }
    // Clear all inputs value in subject form
    @FXML
    public void cancel_subject() {
        dataToSubjectTb();
        c_matiere_input.clear();
        design_matiere_input.clear();
        coef_input.clear();

        c_matiere_input.setEditable(true);
        design_matiere_input.setEditable(true);

        add_subject_btn.setDisable(false);
        update_subject_btn.setDisable(true);
        delete_subject_btn.setDisable(true);
    }
    // Show confirmation message on subject delete button click
    @FXML
    public void handleSubjectDelete() {
        String numMat = c_matiere_input.getText();
        String designMat = design_matiere_input.getText();

        if (!numMat.isEmpty() && !designMat.isEmpty()) {
            // Show a confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Etes-vous sûr de vouloir supprimer cette matière?");

            // Capture the user's response
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                delete_subject(numMat, designMat);
            }
        } else {
            subject_result_message.setText("Aucune matière sélectionnée.");
            timing = new Timeline(new KeyFrame(Duration.seconds(3), _ -> subject_result_message.setText("")));
            timing.play();
        }
    }

    /**
     * Score button methods
     */

    @FXML private Button add_score_btn;
    @FXML private Button update_score_btn;
    @FXML private Button delete_score_btn;
    @FXML
    public void add_score() {
        NoteController score = new NoteController();
        String schoolYear = school_year_input.getText();
        String student = score_student_input.getValue();
        String subject = score_subject_input.getValue();
        String scoreValue = score_input.getText();

        if (!schoolYear.isEmpty() && student != null && subject != null && !scoreValue.isEmpty()) {
            if (Double.parseDouble(scoreValue) >= 0 && Double.parseDouble(scoreValue) <= 20) {
                try {
                    String resultMessage = score.addNote(schoolYear, student, subject, Double.parseDouble(scoreValue));
                    if (resultMessage.contains("ajoutée")) {
                        AverageController a = new AverageController();
                        a.updateTotalScore(student, school_score_cbx.getValue());
                        a.updateAverage(student, school_score_cbx.getValue());
                        cancel_score();
                    }
                    score_result_message.setText(resultMessage);
                    timing = new Timeline(new KeyFrame(Duration.seconds(3), _ -> score_result_message.setText("")));
                    timing.play();
                } catch (NumberFormatException e) {
                    score_result_message.setText("Note Invalide.");
                    timing = new Timeline(new KeyFrame(Duration.seconds(3), _ -> score_result_message.setText("")));
                    timing.play();
                }
            } else {
                score_result_message.setText("Note d'une élève doit être compris entre 0 et 20.");
                timing = new Timeline(new KeyFrame(Duration.seconds(3), _ -> score_result_message.setText("")));
                timing.play();
            }
        } else {
            score_result_message.setText("Veuillez remplir tous les champs requis.");
            timing = new Timeline(new KeyFrame(Duration.seconds(3), _ -> score_result_message.setText("")));
            timing.play();
        }
    }
    @FXML
    public void update_score() {
        NoteController score = new NoteController();
        String schoolYear = school_year_input.getText();
        String student = score_student_input.getValue();
        String subject = score_subject_input.getValue();
        String scoreValue = score_input.getText();

        if (!schoolYear.isEmpty() && student != null && subject != null && !scoreValue.isEmpty()) {
            if (Double.parseDouble(scoreValue) >= 0 && Double.parseDouble(scoreValue) <= 20) {
                try {
                    String resultMessage = score.updateNote(schoolYear, student, subject, Double.parseDouble(scoreValue));
                    if (resultMessage.contains("mise à jour")) {
                        AverageController a = new AverageController();
                        a.updateTotalScore(student, school_score_cbx.getValue());
                        a.updateAverage(student, school_score_cbx.getValue());
                        cancel_score();
                    }
                    score_result_message.setText(resultMessage);
                    timing = new Timeline(new KeyFrame(Duration.seconds(3), _ -> score_result_message.setText("")));
                    timing.play();
                } catch (NumberFormatException e) {
                    score_result_message.setText("Note Invalide.");
                    timing = new Timeline(new KeyFrame(Duration.seconds(3), _ -> score_result_message.setText("")));
                    timing.play();
                }
            } else {
                score_result_message.setText("Note d'une élève doit être compris entre 0 et 20.");
                timing = new Timeline(new KeyFrame(Duration.seconds(3), _ -> score_result_message.setText("")));
                timing.play();
            }
        } else {
            score_result_message.setText("Veuillez remplir tous les champs requis.");
            timing = new Timeline(new KeyFrame(Duration.seconds(3), _ -> score_result_message.setText("")));
            timing.play();
        }
    }
    @FXML
    private void delete_score(String numEleve, String numMat) {
        NoteController score = new NoteController();

        String resultMessage = score.deleteNote(numEleve, numMat);
        if (resultMessage.contains("supprimée")) {
            AverageController a = new AverageController();
            a.updateTotalScore(numEleve, school_score_cbx.getValue());
            a.updateAverage(numEleve, school_score_cbx.getValue());
            cancel_score();
        }
        score_result_message.setText(resultMessage);
        timing = new Timeline(new KeyFrame(Duration.seconds(3), _ -> score_result_message.setText("")));
        timing.play();
    }
    // Clear all inputs value in score form
    @FXML
    public void cancel_score() {
        dataToScoreTb(school_score_cbx.getValue());
        score_student_input.getSelectionModel().clearSelection();
        score_student_input.setValue(null);
        score_subject_input.getSelectionModel().clearSelection();
        score_subject_input.setValue(null);
        score_input.clear();

        score_student_input.setDisable(false);

        add_score_btn.setDisable(false);
        update_score_btn.setDisable(true);
        delete_score_btn.setDisable(true);
    }
    // Show confirmation message on score delete button click
    @FXML
    public void handleScoreDelete() {
        String numEleve = score_student_input.getValue();
        String numMat = score_subject_input.getValue();

        if (numEleve != null && numMat != null) {
            // Show a confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Etes-vous sûr de vouloir supprimer la note?");

            // Capture the user's response
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                delete_score(numEleve, numMat);
            }
        } else {
            score_result_message.setText("Aucune note sélectionnée.");
            timing = new Timeline(new KeyFrame(Duration.seconds(3), _ -> score_result_message.setText("")));
            timing.play();
        }
    }

    /**
     * Add all schools to the school combobox (In student form at student section)
     */
    private void studentFormCombobox() {
        SchoolController school = new SchoolController();
        List<String[]> schoolList = school.getAllSchools();
        ObservableList<String> options = FXCollections.observableArrayList();

        for (String[] schoolArray : schoolList) {
            options.add(schoolArray[0]);
        }
        student_school_combobox.setPromptText("Selectionner une école");
        student_school_combobox.setItems(options);
    }

    /**
     * Add all students in a specific school to the student combobox (In score form at score section)
     */
    private void scoreStudentCombobox(String numEcole) {
        StudentController student = new StudentController();
        List<String[]> studentList = student.getAllStudentsInSchool(numEcole);
        ObservableList<String> options = FXCollections.observableArrayList();

        for (String[] studentArray : studentList) {
            options.add(studentArray[0]);
        }
        score_student_input.setItems(options);
    }

    /**
     * Add all subjects to the subject combobox (In score form at score section)
     */
    private void scoreSubjectCombobox() {
        SubjectController subject = new SubjectController();
        List<String[]> subjectList = subject.getAllSubjects();
        ObservableList<String> options = FXCollections.observableArrayList();

        for (String[] subjectArray : subjectList) {
            options.add(subjectArray[0]);
        }
        score_subject_input.setItems(options);
    }

    /**
     * School TableView manipulation
     */

    @FXML private TableView<School> school_tableview;
    @FXML private TableColumn<School, String> numEcoleCol;
    @FXML private TableColumn<School, String> designEcoleCol;
    @FXML private TableColumn<School, String> adresseEcoleCol;

    ObservableList<School> schoolsList = FXCollections.observableArrayList();
    // Show all schools in school TableView
    private void dataToSchoolTb() {
        SchoolController school = new SchoolController();
        List<String[]> listSchools = school.getAllSchools();

        numEcoleCol.setCellValueFactory(new PropertyValueFactory<>("numEcole"));
        designEcoleCol.setCellValueFactory(new PropertyValueFactory<>("design"));
        adresseEcoleCol.setCellValueFactory(new PropertyValueFactory<>("adresse"));

        school_tableview.getItems().clear();

        for (String[] schoolArray : listSchools) {
            schoolsList.add(new School(schoolArray[0], schoolArray[1], schoolArray[2]));
            school_tableview.setItems(schoolsList);
        }
    }
    // Handle selection on school TableView
    private void selectTableSchool() {
        school_tableview.getSelectionModel().selectedItemProperty().addListener((_, _, newSelection) -> {
            if (newSelection != null) {
                num_ecole_input.setText(newSelection.getNumEcole());
                design_ecole_input.setText(newSelection.getDesign());
                adresse_ecole_input.setText(newSelection.getAdresse());

                num_ecole_input.setEditable(false);

                add_school_btn.setDisable(true);
                update_school_btn.setDisable(false);
                delete_school_btn.setDisable(false);
            }
        });
    }

    /**
     * Subject TableView manipulation
     */

    @FXML private TableView<Subject> subject_tableview;
    @FXML private TableColumn<Subject, String> numMatCol;
    @FXML private TableColumn<Subject, String> designMatCol;
    @FXML private TableColumn<Subject, Integer> coefCol;

    ObservableList<Subject> subjectsList = FXCollections.observableArrayList();
    // Show all subjects in subject TableView
    private void dataToSubjectTb() {
        SubjectController subject = new SubjectController();
        List<String[]> listSubjects = subject.getAllSubjects();

        numMatCol.setCellValueFactory(new PropertyValueFactory<>("numMat"));
        designMatCol.setCellValueFactory(new PropertyValueFactory<>("design"));
        coefCol.setCellValueFactory(new PropertyValueFactory<>("coef"));

        subject_tableview.getItems().clear();

        for (String[] subjectArray : listSubjects) {
            subjectsList.add(new Subject(subjectArray[0], subjectArray[1], Integer.parseInt(subjectArray[2])));
            subject_tableview.setItems(subjectsList);
        }
    }
    // Handle selection on subject TableView
    private void selectTableSubject() {
        subject_tableview.getSelectionModel().selectedItemProperty().addListener((_, _, newSelection) -> {
            if (newSelection != null) {
                c_matiere_input.setText(newSelection.getNumMat());
                design_matiere_input.setText(newSelection.getDesign());
                coef_input.setText(String.valueOf(newSelection.getCoef()));

                c_matiere_input.setEditable(false);
                design_matiere_input.setEditable(false);

                add_subject_btn.setDisable(true);
                update_subject_btn.setDisable(false);
                delete_subject_btn.setDisable(false);
            }
        });
    }

    /**
     * Student TableView manipulation
     */

    @FXML
    private TableView<Student> student_tableview;
    @FXML
    private TableColumn<Student, String> numEleveCol;
    @FXML
    private TableColumn<Student, String> nomCol;
    @FXML
    private TableColumn<Student, String> prenomCol;
    @FXML
    private TableColumn<Student, String> dateNaisCol;

    ObservableList<Student> studentsList = FXCollections.observableArrayList();
    // Show all students at a specific school in student TableView
    private void dataToStudentTb(String numEcole) {
        StudentController student = new StudentController();
        List<String[]> listStudents = student.getAllStudentsInSchool(numEcole);

        numEleveCol.setCellValueFactory(new PropertyValueFactory<>("numEleve"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        dateNaisCol.setCellValueFactory(new PropertyValueFactory<>("dateNais"));

        student_tableview.getItems().clear();

        for (String[] studentArray : listStudents) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate date = LocalDate.parse(studentArray[4]);

            studentsList.add(new Student(studentArray[0], studentArray[1], studentArray[2],
                    studentArray[3], date.format(formatter)));
            student_tableview.setItems(studentsList);
        }
    }
    // Show all students in student TableView
    private void dataToStudentTbAll() {
        StudentController student = new StudentController();
        List<String[]> listStudents = student.getAllStudents();

        numEleveCol.setCellValueFactory(new PropertyValueFactory<>("numEleve"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        dateNaisCol.setCellValueFactory(new PropertyValueFactory<>("dateNais"));

        student_tableview.getItems().clear();

        for (String[] studentArray : listStudents) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate date = LocalDate.parse(studentArray[4]);

            studentsList.add(new Student(studentArray[0], studentArray[1], studentArray[2],
                    studentArray[3], date.format(formatter)));
            student_tableview.setItems(studentsList);
        }
    }
    // Handle the selection on student TableView
    private void selectTableStudent() {
        student_tableview.getSelectionModel().selectedItemProperty().addListener((_, _, newSelection) -> {
            if (newSelection != null) {
                student_school_combobox.setValue(newSelection.getNumEcole());
                student_num_input.setText(newSelection.getNumEleve());
                student_firstname_input.setText(newSelection.getNom());
                student_lastname_input.setText(newSelection.getPrenom());

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                student_datenais_input.setValue(LocalDate.parse(newSelection.getDateNais(), formatter));

                student_school_combobox.setDisable(true);
                student_num_input.setEditable(false);

                add_student_btn.setDisable(true);
                update_student_btn.setDisable(false);
                delete_student_btn.setDisable(false);
            }
        });
    }

    /**
     * Score TableView manipulation
     */

    @FXML private TableView<Score> score_tableview;
    @FXML private TableColumn<Score, String> scoreNumEleveCol;
    @FXML private TableColumn<Score, String> scoreNomCol;
    @FXML private TableColumn<Score, String> scorePrenomCol;
    @FXML private TableColumn<Score, Double> scoreTotalCol;

    ObservableList<Score> studentsScoreList = FXCollections.observableArrayList();
    // Show all students at a specific school in score TableView
    private void dataToScoreTb(String numEcole) {
        StudentController student = new StudentController();
        List<String[]> listStudents = student.getAllStudentsInSchool(numEcole);
        scoreNumEleveCol.setCellValueFactory(new PropertyValueFactory<>("numEleve"));
        scoreNomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        scorePrenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        scoreTotalCol.setCellValueFactory(new PropertyValueFactory<>("noteTotale"));

        score_tableview.getItems().clear();

        for (String[] studenArray : listStudents) {
            studentsScoreList.add(new Score(studenArray[0], studenArray[2], studenArray[3],
                    Double.parseDouble(studenArray[5])));
            score_tableview.setItems(studentsScoreList);
        }
    }
    // Refresh table score to get the latest update of all data in it
    @FXML
    public void refreshTableScore() {
        StudentController student = new StudentController();
        List<String[]> listStudents = student.getAllStudentsInSchool(school_score_cbx.getValue());
        scoreNumEleveCol.setCellValueFactory(new PropertyValueFactory<>("numEleve"));
        scoreNomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        scorePrenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        scoreTotalCol.setCellValueFactory(new PropertyValueFactory<>("noteTotale"));

        score_tableview.getItems().clear();

        for (String[] studenArray : listStudents) {
            AverageController a = new AverageController();
            a.updateTotalScore(studenArray[0], school_score_cbx.getValue());
            a.updateAverage(studenArray[0], school_score_cbx.getValue());

            studentsScoreList.add(new Score(studenArray[0], studenArray[2], studenArray[3],
                    Double.parseDouble(studenArray[5])));
            score_tableview.setItems(studentsScoreList);
        }
    }
    // Handle the selection on score TableView
    private void selectTableScore() {
        score_tableview.getSelectionModel().selectedItemProperty().addListener((_, _, newSelection) -> {
            if (newSelection != null) {
                score_student_input.setValue(newSelection.getNumEleve());

                score_student_input.setDisable(true);

                add_score_btn.setDisable(true);
                update_score_btn.setDisable(false);
                delete_score_btn.setDisable(false);
            }
        });
    }

    /**
     * Result TableView (students who were admitted to their CEPE)
     */

    @FXML private TableView<StudentCepeAdmitted> result_admitted_cepe;
    @FXML private TableColumn<StudentCepeAdmitted, String> resNumEleveCol1;
    @FXML private TableColumn<StudentCepeAdmitted, String> resNomCol1;
    @FXML private TableColumn<StudentCepeAdmitted, String> resPrenomCol1;
    @FXML private TableColumn<StudentCepeAdmitted, String> resEcoleCol1;
    @FXML private TableColumn<StudentCepeAdmitted, Double> resMoyenneCol1;
    @FXML private TableColumn<StudentCepeAdmitted, Void> resReleveCol1;

    ObservableList<StudentCepeAdmitted> studentsCepeAdmittedList = FXCollections.observableArrayList();
    // Show all students who were admitted to their CEPE
    private void showCepeAdmittedStudents() {
        AverageController a = new AverageController();
        List<String[]> listStudents = a.getStudentsCepeAdmitted();

        resNumEleveCol1.setCellValueFactory(new PropertyValueFactory<>("numEleve"));
        resNomCol1.setCellValueFactory(new PropertyValueFactory<>("nom"));
        resPrenomCol1.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        resEcoleCol1.setCellValueFactory(new PropertyValueFactory<>("ecole"));
        resMoyenneCol1.setCellValueFactory(new PropertyValueFactory<>("moyenne"));

        Callback<TableColumn<StudentCepeAdmitted, Void>, TableCell<StudentCepeAdmitted, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<StudentCepeAdmitted, Void> call(final TableColumn<StudentCepeAdmitted, Void> param) {
                return new TableCell<>() {

                    private final Button btn = new Button("Générer son relevé");

                    {
                        btn.getStyleClass().add("tableview_button");
                        btn.setOnAction((ActionEvent _) -> {
                            StudentCepeAdmitted student = getTableView().getItems().get(getIndex());
                            releveNotesCepe(student);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        };

        resReleveCol1.setCellFactory(cellFactory);

        result_admitted_cepe.getItems().clear();

        for (String[] studenArray : listStudents) {
            studentsCepeAdmittedList.add(new StudentCepeAdmitted(studenArray[0], studenArray[1], studenArray[2],
                    studenArray[3], Double.parseDouble(studenArray[4])));
            result_admitted_cepe.setItems(studentsCepeAdmittedList);
        }
    }

    /**
     * Generate pdf for student who were admitted to their cepe
     * @param student property of the student
     */
    private void releveNotesCepe(StudentCepeAdmitted student) {
        String numEleve = student.getNumEleve();
        String nom = student.getNom();
        String prenom = student.getPrenom();
        String ecole = student.getEcole();
        double moyenne = student.getMoyenne();

        Ecole school = new Ecole();
        AverageController a = new AverageController();
        StudentController stud = new StudentController();
        SubjectController subj = new SubjectController();
        NoteController score = new NoteController();

        String numEcole = school.getNumEcole(ecole);
        double noteTotale = a.getTotalScore(numEleve, numEcole);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateNais = stud.getStudent(numEcole, numEleve)[4];
        LocalDate date = LocalDate.parse(dateNais);

        List<String[]> subjects = subj.getAllSubjects();
        String[][] tableData = new String[subjects.size()][4];
        // String anneeSco = null;
        int i = 0;
        for (String[] subject : subjects) {
            String matiere = subject[1];
            int coef = Integer.parseInt(subject[2]);
            String numMat = subject[0];

            try {
                String[] scoreData = score.getScore(numEleve, numMat);
                if (scoreData != null && scoreData.length > 0) {
                    double note = Double.parseDouble(scoreData[3]);
                    double notePondere = coef * note;

                    tableData[i][0] = matiere;
                    tableData[i][1] = String.valueOf(coef);
                    tableData[i][2] = String.valueOf(note);
                    tableData[i][3] = String.valueOf(notePondere);
                } else {
                    tableData[i][0] = matiere;
                    tableData[i][1] = String.valueOf(coef);
                    tableData[i][2] = "0.0";
                    tableData[i][3] = "0.0";
                }
            } catch (Exception e) {
                tableData[i][0] = matiere;
                tableData[i][1] = String.valueOf(coef);
                tableData[i][2] = "0.0";
                tableData[i][3] = "0.0";
            }

            i++;
        }

        PdfController pdf = new PdfController("2022-2023", nom, prenom, date.format(formatter), ecole, noteTotale, moyenne);
        pdf.generatePdf(tableData);
    }

    /**
     * Result TableView (students who were admitted to sixth class)
     */

    @FXML private TableView<StudentSixthAdmitted> result_admitted_sixth;
    @FXML private TableColumn<StudentSixthAdmitted, String> resNumEleveCol2;
    @FXML private TableColumn<StudentSixthAdmitted, String> resNomCol2;
    @FXML private TableColumn<StudentSixthAdmitted, String> resPrenomCol2;
    @FXML private TableColumn<StudentSixthAdmitted, String> resEcoleCol2;
    @FXML private TableColumn<StudentSixthAdmitted, Double> resMoyenneCol2;
    @FXML private TableColumn<StudentSixthAdmitted, Void> resReleveCol2;

    ObservableList<StudentSixthAdmitted> studentsSixthAdmittedList = FXCollections.observableArrayList();
    // Show all students who were admitted to their CEPE
    private void showSixthAdmittedStudents() {
        AverageController a = new AverageController();
        List<String[]> listStudents = a.getStudentsSixthAdmitted();

        resNumEleveCol2.setCellValueFactory(new PropertyValueFactory<>("numEleve"));
        resNomCol2.setCellValueFactory(new PropertyValueFactory<>("nom"));
        resPrenomCol2.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        resEcoleCol2.setCellValueFactory(new PropertyValueFactory<>("ecole"));
        resMoyenneCol2.setCellValueFactory(new PropertyValueFactory<>("moyenne"));

        Callback<TableColumn<StudentSixthAdmitted, Void>, TableCell<StudentSixthAdmitted, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<StudentSixthAdmitted, Void> call(final TableColumn<StudentSixthAdmitted, Void> param) {
                return new TableCell<>() {

                    private final Button btn = new Button("Générer son relevé");

                    {
                        btn.getStyleClass().add("tableview_button");
                        btn.setOnAction((ActionEvent _) -> {
                            StudentSixthAdmitted student = getTableView().getItems().get(getIndex());
                            releveNotesSixieme(student);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        };

        resReleveCol2.setCellFactory(cellFactory);

        result_admitted_sixth.getItems().clear();

        for (String[] studenArray : listStudents) {
            studentsSixthAdmittedList.add(new StudentSixthAdmitted(studenArray[0], studenArray[1], studenArray[2],
                    studenArray[3], Double.parseDouble(studenArray[4])));
            result_admitted_sixth.setItems(studentsSixthAdmittedList);
        }
    }

    /**
     * Generate pdf for student who were admitted to sixth class
     * @param student property of the student
     */
    private void releveNotesSixieme(StudentSixthAdmitted student) {
        String numEleve = student.getNumEleve();
        String nom = student.getNom();
        String prenom = student.getPrenom();
        String ecole = student.getEcole();
        double moyenne = student.getMoyenne();

        Ecole school = new Ecole();
        AverageController a = new AverageController();
        StudentController stud = new StudentController();
        SubjectController subj = new SubjectController();
        NoteController score = new NoteController();

        String numEcole = school.getNumEcole(ecole);
        double noteTotale = a.getTotalScore(numEleve, numEcole);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateNais = stud.getStudent(numEcole, numEleve)[4];
        LocalDate date = LocalDate.parse(dateNais);

        List<String[]> subjects = subj.getAllSubjects();
        String[][] tableData = new String[subjects.size()][4];
        // String anneeSco = null;
        int i = 0;
        for (String[] subject : subjects) {
            String matiere = subject[1];
            int coef = Integer.parseInt(subject[2]);
            String numMat = subject[0];

            try {
                String[] scoreData = score.getScore(numEleve, numMat);
                if (scoreData != null && scoreData.length > 0) {
                    double note = Double.parseDouble(scoreData[3]);
                    double notePondere = coef * note;

                    tableData[i][0] = matiere;
                    tableData[i][1] = String.valueOf(coef);
                    tableData[i][2] = String.valueOf(note);
                    tableData[i][3] = String.valueOf(notePondere);
                } else {
                    tableData[i][0] = matiere;
                    tableData[i][1] = String.valueOf(coef);
                    tableData[i][2] = "0.0";
                    tableData[i][3] = "0.0";
                }
            } catch (Exception e) {
                tableData[i][0] = matiere;
                tableData[i][1] = String.valueOf(coef);
                tableData[i][2] = "0.0";
                tableData[i][3] = "0.0";
            }

            i++;
        }

        PdfController pdf = new PdfController("2022-2023", nom, prenom, date.format(formatter), ecole, noteTotale, moyenne);
        pdf.generatePdf(tableData);
    }

    /**
     * Result TableView (students who failed to their CEPE)
     */

    @FXML private TableView<StudentFailed> result_failed;
    @FXML private TableColumn<StudentFailed, String> resNumEleveCol3;
    @FXML private TableColumn<StudentFailed, String> resNomCol3;
    @FXML private TableColumn<StudentFailed, String> resPrenomCol3;
    @FXML private TableColumn<StudentFailed, String> resEcoleCol3;
    @FXML private TableColumn<StudentFailed, Double> resMoyenneCol3;
    @FXML private TableColumn<StudentFailed, Void> resReleveCol3;

    ObservableList<StudentFailed> studentsFailedList = FXCollections.observableArrayList();
    // Show all students who were admitted to their CEPE
    private void showFailedStudents() {
        AverageController a = new AverageController();
        List<String[]> listStudents = a.getFailedStudents();

        resNumEleveCol3.setCellValueFactory(new PropertyValueFactory<>("numEleve"));
        resNomCol3.setCellValueFactory(new PropertyValueFactory<>("nom"));
        resPrenomCol3.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        resEcoleCol3.setCellValueFactory(new PropertyValueFactory<>("ecole"));
        resMoyenneCol3.setCellValueFactory(new PropertyValueFactory<>("moyenne"));

        Callback<TableColumn<StudentFailed, Void>, TableCell<StudentFailed, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<StudentFailed, Void> call(final TableColumn<StudentFailed, Void> param) {
                return new TableCell<>() {

                    private final Button btn = new Button("Générer son relevé");

                    {
                        btn.getStyleClass().add("tableview_button");
                        btn.setOnAction((ActionEvent _) -> {
                            StudentFailed student = getTableView().getItems().get(getIndex());
                            releveNotesRedouble(student);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        };

        resReleveCol3.setCellFactory(cellFactory);

        result_failed.getItems().clear();

        for (String[] studenArray : listStudents) {
            studentsFailedList.add(new StudentFailed(studenArray[0], studenArray[1], studenArray[2],
                    studenArray[3], Double.parseDouble(studenArray[4])));
            result_failed.setItems(studentsFailedList);
        }
    }

    /**
     * Generate pdf for student who failed to their exam
     * @param student property of the student
     */
    private void releveNotesRedouble(StudentFailed student) {
        String numEleve = student.getNumEleve();
        String nom = student.getNom();
        String prenom = student.getPrenom();
        String ecole = student.getEcole();
        double moyenne = student.getMoyenne();

        Ecole school = new Ecole();
        AverageController a = new AverageController();
        StudentController stud = new StudentController();
        SubjectController subj = new SubjectController();
        NoteController score = new NoteController();

        String numEcole = school.getNumEcole(ecole);
        double noteTotale = a.getTotalScore(numEleve, numEcole);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateNais = stud.getStudent(numEcole, numEleve)[4];
        LocalDate date = LocalDate.parse(dateNais);

        List<String[]> subjects = subj.getAllSubjects();
        String[][] tableData = new String[subjects.size()][4];
        // String anneeSco = null;
        int i = 0;
        for (String[] subject : subjects) {
            String matiere = subject[1];
            int coef = Integer.parseInt(subject[2]);
            String numMat = subject[0];

            try {
                String[] scoreData = score.getScore(numEleve, numMat);
                if (scoreData != null && scoreData.length > 0) {
                    double note = Double.parseDouble(scoreData[3]);
                    double notePondere = coef * note;

                    tableData[i][0] = matiere;
                    tableData[i][1] = String.valueOf(coef);
                    tableData[i][2] = String.valueOf(note);
                    tableData[i][3] = String.valueOf(notePondere);
                } else {
                    tableData[i][0] = matiere;
                    tableData[i][1] = String.valueOf(coef);
                    tableData[i][2] = "0.0";
                    tableData[i][3] = "0.0";
                }
            } catch (Exception e) {
                tableData[i][0] = matiere;
                tableData[i][1] = String.valueOf(coef);
                tableData[i][2] = "0.0";
                tableData[i][3] = "0.0";
            }

            i++;
        }

        PdfController pdf = new PdfController("2022-2023", nom, prenom, date.format(formatter), ecole, noteTotale, moyenne);
        pdf.generatePdf(tableData);
    }

    /**
     * Handle change on school combobox to score input in score section
     */
    private void subjectToScore() {
        score_subject_input.valueProperty().addListener((_, _, newValue) -> {
            String numEleve = score_student_input.getValue();
            NoteController score = new NoteController();
            String[] scoreInfo = score.getScore(numEleve, newValue);
            score_input.clear();
            if (scoreInfo[3] != null) {
                score_input.setText(scoreInfo[3]);
            }
        });
    }

    /**
     * Handle change on school combobox in student section
     */
    private void schoolCombobox() {
        school_cbox_student.valueProperty().addListener((_, _, newValue) -> {
            if (Objects.equals(newValue, "Toutes les écoles")) {
                dataToStudentTbAll();
            } else {
                dataToStudentTb(newValue);
            }
        });
    }

    /**
     * Handle change on school combobox in score section
     */
    private void schoolComboboxScore() {
        school_score_cbx.valueProperty().addListener((_, _, newValue) -> {
            scoreStudentCombobox(newValue);
            dataToScoreTb(newValue);
        });
    }

    /**
     * School combobox in student section
     */
    private void dataToSchoolCbx() {
        SchoolController school = new SchoolController();
        List<String[]> schoolList = school.getAllSchools();
        ObservableList<String> options = FXCollections.observableArrayList();

        options.add("Toutes les écoles");
        for (String[] schoolArray : schoolList) {
            options.add(schoolArray[0]);
        }
        school_cbox_student.setItems(options);
        school_cbox_student.setValue(options.getFirst());
        dataToStudentTbAll();
    }

    /**
     * School combobox in score section
     */
    private void dataToSchoolScoreCbx() {
        SchoolController school = new SchoolController();
        List<String[]> schoolList = school.getAllSchools();
        ObservableList<String> options = FXCollections.observableArrayList();

        for (String[] schoolArray : schoolList) {
            options.add(schoolArray[0]);
        }
        school_score_cbx.setItems(options);
        school_score_cbx.setValue(options.getFirst());
        scoreStudentCombobox(school_score_cbx.getValue());
        dataToScoreTb(school_score_cbx.getValue());
    }

    @FXML private TextField student_search;
    private void searchStudent() {
        student_search.textProperty().addListener(new ChangeListener<>() {
            final StudentController student = new StudentController();
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                numEleveCol.setCellValueFactory(new PropertyValueFactory<>("numEleve"));
                nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
                prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
                dateNaisCol.setCellValueFactory(new PropertyValueFactory<>("dateNais"));

                student_tableview.getItems().clear();

                if (Objects.equals(school_cbox_student.getValue(), "Toutes les écoles")) {

                    List<String[]> listStudents = student.searchSudent(newValue);

                    for (String[] studentArray : listStudents) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        LocalDate date = LocalDate.parse(studentArray[4]);

                        studentsList.add(new Student(studentArray[0], studentArray[1], studentArray[2],
                                studentArray[3], date.format(formatter)));
                        student_tableview.setItems(studentsList);
                    }
                } else {
                    List<String[]> listStudents = student.searchSudentInSchool(newValue, school_cbox_student.getValue());

                    for (String[] studentArray : listStudents) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        LocalDate date = LocalDate.parse(studentArray[4]);

                        studentsList.add(new Student(studentArray[0], studentArray[1], studentArray[2],
                                studentArray[3], date.format(formatter)));
                        student_tableview.setItems(studentsList);
                    }
                }
            }
        });
    }

    /**
     * Refresh the results TableView
     */
    @FXML
    public void refreshResultsTable() {
        showCepeAdmittedStudents();
        showSixthAdmittedStudents();
        showFailedStudents();
    }
}