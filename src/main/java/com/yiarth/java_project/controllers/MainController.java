package com.yiarth.java_project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class MainController {

    @FXML
    private AnchorPane home_anchor_pane;
    @FXML
    private AnchorPane result_anchor_pane;
    @FXML
    private AnchorPane student_anchor_pane;
    @FXML
    private AnchorPane subject_anchor_pane;
    @FXML
    private AnchorPane school_anchor_pane;

    @FXML
    private Label bar_title;

    /**
     *
     * @param anchorPaneNumber
     */
    private void handleSideBarButtonClick(int anchorPaneNumber) {
        home_anchor_pane.setVisible(false);
        result_anchor_pane.setVisible(false);
        student_anchor_pane.setVisible(false);
        subject_anchor_pane.setVisible(false);
        school_anchor_pane.setVisible(false);

        switch (anchorPaneNumber) {
            case 1:
                home_anchor_pane.setVisible(true);
                bar_title.setText("Accueil");
                break;
            case 2:
                result_anchor_pane.setVisible(true);
                bar_title.setText("Résultat");
                break;
            case 3:
                student_anchor_pane.setVisible(true);
                bar_title.setText("Elève");
                break;
            case 4:
                subject_anchor_pane.setVisible(true);
                bar_title.setText("Matière");
                break;
            case 5:
                school_anchor_pane.setVisible(true);
                bar_title.setText("Ecole");
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
    public void student_button_sidebar() {
        handleSideBarButtonClick(3);
    }
    @FXML
    public void subject_button_sidebar() {
        handleSideBarButtonClick(4);
    }
    @FXML
    public void school_button_sidebar() {
        handleSideBarButtonClick(5);
    }
}