package com.yiarth.java_project.controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.util.stream.Stream;

public class PdfController {

    private String _annee_scolaire;
    private String _nom;
    private String _prenom;
    private String _date_nais;
    private String _ecole;

    public PdfController(String anneeScolaire, String nom, String prenom, String dateNais, String ecole) {
        _annee_scolaire = anneeScolaire;
        _nom = nom;
        _prenom = prenom;
        _date_nais = dateNais;
        _ecole = ecole;
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Matière", "Coefficient", "Note", "Note pondérée").forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(columnTitle, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            table.addCell(header);
        });
    }
}
