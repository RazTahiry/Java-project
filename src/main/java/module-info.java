module yiarth.raz.java_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires itextpdf;

    opens com.yiarth.java_project to javafx.fxml;
    exports com.yiarth.java_project;
    exports com.yiarth.java_project.controllers;
    opens com.yiarth.java_project.controllers to javafx.fxml;
    opens com.yiarth.java_project.tableview_models to javafx.base;
}