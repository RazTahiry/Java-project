package com.yiarth.java_project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {
    @FXML
    private Label myButton;

    @FXML
    protected void onHelloButtonClick() {
        myButton.setText("Teste");
    }
}