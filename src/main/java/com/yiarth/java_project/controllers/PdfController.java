package com.yiarth.java_project.controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;

public class PdfController {

    private String _annee_scolaire;
    private String _nom;
    private String _prenom;
    private String _date_nais;
    private String _ecole;
    private double _note_total;
    private double _moyenne;

    public PdfController() {}

    public PdfController(String anneeScolaire, String nom, String prenom, String dateNais,
                         String ecole, double noteTotal, double moyenne) {
        _annee_scolaire = anneeScolaire;
        _nom = nom;
        _prenom = prenom;
        _date_nais = dateNais;
        _ecole = ecole;
        _note_total = noteTotal;
        _moyenne = moyenne;
    }

    /**
     * Generate the body of the pdf
     * @param tableData data's array for the table
     */
    public void generatePdf(String[][] tableData) {
        try {
            String outputPath = STR."\{System.getProperty("user.home")}\\releve.pdf";
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(outputPath));
            doc.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

            Paragraph title = new Paragraph("Relevé de note", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            doc.add(title);
            doc.add(new Paragraph(" ", normalFont));
            doc.add(new Paragraph(STR."Année scolaire : \{_annee_scolaire}", normalFont));
            doc.add(new Paragraph(STR."Nom : \{_nom}", normalFont));
            doc.add(new Paragraph(STR."Prénoms : \{_prenom}", normalFont));
            doc.add(new Paragraph(STR."Date de naissance : \{_date_nais}", normalFont));
            doc.add(new Paragraph(STR."Ecole : \{_ecole}", normalFont));
            doc.add(new Paragraph(" ", normalFont));

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);

            addTableHeader(table, "Matière");
            addTableHeader(table, "Coefficient");
            addTableHeader(table, "Note");
            addTableHeader(table, "Note pondéré");

//            String[][] data = {
//                    {"Malagasy", "3", "12", "36"},
//                    {"Calcul", "1", "18", "18"},
//                    {"Problème", "2", "19", "38"},
//                    {"Tantara", "1", "11", "11"},
//                    {"Géographie", "1", "14", "14"},
//                    {"Français", "1", "15", "15"},
//                    {"SVT", "2", "09", "18"}
//            };

            Arrays.stream(tableData).forEach(row -> {
                for (String cell : row) {
                    PdfPCell pdfCell = new PdfPCell(new Phrase(cell, normalFont));
                    pdfCell.setPaddingBottom(3f);
                    pdfCell.setPaddingLeft(5f);
                    table.addCell(pdfCell);
                }
            });

            addTotalRow(table, normalFont, "Total", String.valueOf(_note_total));
            addTotalRow(table, normalFont, "Moyenne", String.valueOf(_moyenne));

            doc.add(table);
            doc.close();
            System.out.println(STR."PDF created successfully at: \{outputPath}");
        } catch (FileNotFoundException e) {
            System.out.println(STR."FileNotFoundException: \{e.getMessage()}");
        } catch (DocumentException e) {
            System.out.println(STR."DocumentException: \{e.getMessage()}");
        }
    }

    /**
     * Add table header
     * @param table the table container
     * @param headerTitle the header titles
     */
    private void addTableHeader(PdfPTable table, String headerTitle) {
        PdfPCell header = new PdfPCell();
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12);
        header.setPhrase(new Phrase(headerTitle, headerFont));
        header.setPaddingBottom(3f);
        header.setPaddingLeft(5f);
        table.addCell(header);
    }

    /**
     * The score total and score average rows on the bottom of the table
     * @param table the table container
     * @param font texts font style
     * @param label Totale | Moyenne
     * @param value total value | Average value
     */
    private void addTotalRow(PdfPTable table, Font font, String label, String value) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, font));
        labelCell.setColspan(3);
        labelCell.setPaddingBottom(3f);
        labelCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(labelCell);

        PdfPCell valueCell = new PdfPCell(new Phrase(value, font));
        valueCell.setPaddingBottom(3f);
        valueCell.setPaddingLeft(5f);
        table.addCell(valueCell);
    }
}
