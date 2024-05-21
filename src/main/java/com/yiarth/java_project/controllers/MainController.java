package com.yiarth.java_project.controllers;

import com.yiarth.java_project.tableview_models.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private Timeline timing;

    @FXML
    private AnchorPane home_anchor_pane;
    @FXML
    private AnchorPane result_anchor_pane;
    @FXML
    private AnchorPane score_anchor_pane;
    @FXML
    private AnchorPane student_anchor_pane;
    @FXML
    private AnchorPane subject_anchor_pane;
    @FXML
    private AnchorPane school_anchor_pane;

    @FXML
    private AnchorPane bar_school_container;
    @FXML
    private Label bar_title;

    /**
     * School TextFields
     */
    @FXML
    private TextField num_ecole_input;
    @FXML
    private TextField design_ecole_input;
    @FXML
    private TextField adresse_ecole_input;
    @FXML
    private Label school_result_message;

    /**
     * Student TextFields
     */
    @FXML
    private ComboBox<String> student_school_combobox;
    @FXML
    private TextField student_num_input;
    @FXML
    private TextField student_firstname_input;
    @FXML
    private TextField student_lastname_input;
    @FXML
    private Label student_result_message;
    @FXML
    private DatePicker student_datenais_input;

    /**
     * Subject TextFields
     */
    @FXML
    private TextField c_matiere_input;
    @FXML
    private TextField design_matiere_input;
    @FXML
    private TextField coef_input;
    @FXML
    private Label subject_result_message;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        handleSideBarButtonClick(1);

        dataToSchoolTb();
        dataToSubjectTb();
        dataToStudentTb();

        studentFormCombobox();

        selectTableStudent();
        selectTableSubject();
        selectTableSchool();

        disabledButton();
    }

    /**
     * Handle the action on sidebar button click
     */
    @FXML
    private Button home_button_sidebar;
    @FXML
    private Button result_button_sidebar;
    @FXML
    private Button score_button_sidebar;
    @FXML
    private Button student_button_sidebar;
    @FXML
    private Button subject_button_sidebar;
    @FXML
    private Button school_button_sidebar;
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
                bar_school_container.setVisible(true);
                home_button_sidebar.getStyleClass().add("sidebar_button_active");
                break;
            case 2:
                result_anchor_pane.setVisible(true);
                bar_title.setText("Résultat");
                bar_school_container.setVisible(true);
                result_button_sidebar.getStyleClass().add("sidebar_button_active");
                break;
            case 3:
                score_anchor_pane.setVisible(true);
                bar_title.setText("Note");
                bar_school_container.setVisible(true);
                score_button_sidebar.getStyleClass().add("sidebar_button_active");
                break;
            case 4:
                student_anchor_pane.setVisible(true);
                bar_title.setText("Elève");
                bar_school_container.setVisible(true);
                student_button_sidebar.getStyleClass().add("sidebar_button_active");
                break;
            case 5:
                subject_anchor_pane.setVisible(true);
                bar_title.setText("Matière");
                bar_school_container.setVisible(false);
                subject_button_sidebar.getStyleClass().add("sidebar_button_active");
                break;
            case 6:
                school_anchor_pane.setVisible(true);
                bar_title.setText("Ecole");
                bar_school_container.setVisible(false);
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

    private void disabledButton() {
        update_school_btn.setDisable(true);
        update_student_btn.setDisable(true);
        update_subject_btn.setDisable(true);

        delete_school_btn.setDisable(true);
        delete_student_btn.setDisable(true);
        delete_subject_btn.setDisable(true);
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
        }
        school_result_message.setText(resultMessage);
        timing = new Timeline(new KeyFrame(Duration.seconds(3), events -> school_result_message.setText("")));
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
        }
        school_result_message.setText(resultMessage);
        timing = new Timeline(new KeyFrame(Duration.seconds(3), events -> school_result_message.setText("")));
        timing.play();
    }
    @FXML
    private void delete_school(String numEcole) {
        SchoolController school = new SchoolController();

        String resultMessage = school.deleteSchool(numEcole);
        if (resultMessage.contains("supprimée")) {
            cancel_school();
        }
        school_result_message.setText(resultMessage);
        timing = new Timeline(new KeyFrame(Duration.seconds(3), events -> school_result_message.setText("")));
        timing.play();
    }
    @FXML
    public void cancel_school() {
        dataToSchoolTb();
        num_ecole_input.clear();
        design_ecole_input.clear();
        adresse_ecole_input.clear();

        add_school_btn.setDisable(false);
        update_school_btn.setDisable(true);
        delete_school_btn.setDisable(true);
    }
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
            timing = new Timeline(new KeyFrame(Duration.seconds(3), events -> school_result_message.setText("")));
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
            Date dateNais = Date.valueOf(student_datenais_input.getValue());

            String resultMessage = student.addStudent(numEleve,numEcole,nom,prenom,dateNais);
            if (resultMessage.contains("ajouté")) {
                cancel_student();
            }
            student_result_message.setText(resultMessage);
            timing = new Timeline(new KeyFrame(Duration.seconds(3), events -> student_result_message.setText("")));
            timing.play();
        } else {
            student_result_message.setText("Veuillez remplir tous les champs récquis.");
            timing = new Timeline(new KeyFrame(Duration.seconds(3), events -> student_result_message.setText("")));
            timing.play();
        }
    }
    @FXML
    public void update_student() {
        StudentController student = new StudentController();
        String numEcole = student_school_combobox.getValue();
        String numEleve = student_num_input.getText();
        String nom = student_firstname_input.getText();
        String prenom = student_lastname_input.getText();
        Date dateNais = Date.valueOf(student_datenais_input.getValue());

        String resultMessage = student.updateStudent(numEleve,numEcole,nom,prenom,dateNais);
        if (resultMessage.contains("mis à jour")) {
            cancel_student();
        }
        student_result_message.setText(resultMessage);
        timing = new Timeline(new KeyFrame(Duration.seconds(3), events -> student_result_message.setText("")));
        timing.play();
    }
    @FXML
    private void delete_student(String numEcole, String numEleve) {
        StudentController student = new StudentController();

        String resultMessage = student.deleteStudent(numEleve,numEcole);
        if (resultMessage.contains("supprimé")) {
            cancel_student();
        }
        student_result_message.setText(resultMessage);
        timing = new Timeline(new KeyFrame(Duration.seconds(3), events -> student_result_message.setText("")));
        timing.play();
    }
    @FXML
    public void cancel_student() {
        dataToStudentTb();
        student_school_combobox.getSelectionModel().clearSelection();
        student_school_combobox.setValue(null);
        student_num_input.clear();
        student_firstname_input.clear();
        student_lastname_input.clear();
        student_datenais_input.setValue(null);

        add_student_btn.setDisable(false);
        update_student_btn.setDisable(true);
        delete_student_btn.setDisable(true);
    }
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
            timing = new Timeline(new KeyFrame(Duration.seconds(3), events -> student_result_message.setText("")));
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
                }
                subject_result_message.setText(resultMessage);
                timing = new Timeline(new KeyFrame(Duration.seconds(3), events -> subject_result_message.setText("")));
                timing.play();
            } catch (NumberFormatException e) {
                subject_result_message.setText("Coefficient Invalide.");
                timing = new Timeline(new KeyFrame(Duration.seconds(3), events -> subject_result_message.setText("")));
                timing.play();
            }
        } else {
            subject_result_message.setText("Veuillez remplir tous les champs requis.");
            timing = new Timeline(new KeyFrame(Duration.seconds(3), events -> subject_result_message.setText("")));
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
            }
            subject_result_message.setText(resultMessage);
            timing = new Timeline(new KeyFrame(Duration.seconds(3), events -> subject_result_message.setText("")));
            timing.play();
        } catch (NumberFormatException e) {
            subject_result_message.setText("Coefficient Invalide.");
            timing = new Timeline(new KeyFrame(Duration.seconds(3), events -> subject_result_message.setText("")));
            timing.play();
        }
    }
    @FXML
    private void delete_subject(String numMat, String designMat) {
        SubjectController subject = new SubjectController();

        String resultMessage = subject.deleteSubject(numMat, designMat);
        if (resultMessage.contains("supprimée")) {
            cancel_subject();
        }
        subject_result_message.setText(resultMessage);
        timing = new Timeline(new KeyFrame(Duration.seconds(3), events -> subject_result_message.setText("")));
        timing.play();
    }
    @FXML
    public void cancel_subject() {
        dataToSubjectTb();
        c_matiere_input.clear();
        design_matiere_input.clear();
        coef_input.clear();

        add_subject_btn.setDisable(false);
        update_subject_btn.setDisable(true);
        delete_subject_btn.setDisable(true);
    }
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
            timing = new Timeline(new KeyFrame(Duration.seconds(3), events -> subject_result_message.setText("")));
            timing.play();
        }
    }

    /**
     * School combobox in student form
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
     * School TableView manipulation
     */
    @FXML
    private TableView<School> school_tableview;
    @FXML
    private TableColumn<School, String> numEcoleCol;
    @FXML
    private TableColumn<School, String> designEcoleCol;
    @FXML
    private TableColumn<School, String> adresseEcoleCol;

    ObservableList<School> schoolsList = FXCollections.observableArrayList();

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
    private void selectTableSchool() {
        school_tableview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                num_ecole_input.setText(newSelection.getNumEcole());
                design_ecole_input.setText(newSelection.getDesign());
                adresse_ecole_input.setText(newSelection.getAdresse());

                add_school_btn.setDisable(true);
                update_school_btn.setDisable(false);
                delete_school_btn.setDisable(false);
            }
        });
    }

    /**
     * Subject TableView manipulation
     */
    @FXML
    private TableView<Subject> subject_tableview;
    @FXML
    private TableColumn<Subject, String> numMatCol;
    @FXML
    private TableColumn<Subject, String> designMatCol;
    @FXML
    private TableColumn<Subject, Integer> coefCol;

    ObservableList<Subject> subjectsList = FXCollections.observableArrayList();

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
    private void selectTableSubject() {
        subject_tableview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                c_matiere_input.setText(newSelection.getNumMat());
                design_matiere_input.setText(newSelection.getDesign());
                coef_input.setText(String.valueOf(newSelection.getCoef()));

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

    private void dataToStudentTb() {
        StudentController student = new StudentController();
        List<String[]> listStudents = student.getAllStudents();

        numEleveCol.setCellValueFactory(new PropertyValueFactory<>("numEleve"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        dateNaisCol.setCellValueFactory(new PropertyValueFactory<>("dateNais"));

        student_tableview.getItems().clear();

        for (String[] studentArray : listStudents) {
            LocalDate date = LocalDate.parse(studentArray[4]);
            studentsList.add(new Student(studentArray[0], studentArray[1], studentArray[2], studentArray[3], String.valueOf(date)));
            student_tableview.setItems(studentsList);
        }
    }
    private void selectTableStudent() {
        student_tableview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                student_school_combobox.setValue(newSelection.getNumEcole());
                student_num_input.setText(newSelection.getNumEleve());
                student_firstname_input.setText(newSelection.getNom());
                student_lastname_input.setText(newSelection.getPrenom());
                student_datenais_input.setValue(Date.valueOf(newSelection.getDateNais()).toLocalDate());

                add_student_btn.setDisable(true);
                update_student_btn.setDisable(false);
                delete_student_btn.setDisable(false);
            }
        });
    }
}