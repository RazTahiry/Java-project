package com.yiarth.java_project.models;

import com.yiarth.java_project.config.DbManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Eleve {

    private String _num_eleve;
    private String _num_ecole;
    private String _nom;
    private String _prenom;
    private Date _date_nais;
    private double _note_total;
    private double _moyenne;

    private boolean isCreated;
    private boolean isUpdated;
    private boolean isDeleted;

    /**
     * Constructors
     */

    public Eleve() {}
    public Eleve(String numEleve, String numEcole, String nom, String prenom, Date dateNais, double noteTotal, double moyenne) {
        _num_eleve = numEleve;
        _num_ecole = numEcole;
        _nom = nom;
        _prenom = prenom;
        _date_nais = dateNais;
        _note_total = noteTotal;
        _moyenne = moyenne;
    }
    public Eleve(String numEleve, String numEcole, String nom, String prenom, Date dateNais) {
        _num_eleve = numEleve;
        _num_ecole = numEcole;
        _nom = nom;
        _prenom = prenom;
        _date_nais = dateNais;
    }
    public Eleve(String numEleve, String numEcole) {
        _num_eleve = numEleve;
        _num_ecole = numEcole;
    }
    public Eleve(String numEcole) {
        _num_ecole = numEcole;
    }

    /**
     * Getters
     * @return the private attribute value
     */

    public String get_num_eleve() {
        return _num_eleve;
    }
    public String get_num_ecole() {
        return _num_ecole;
    }
    public String get_nom() {
        return _nom;
    }
    public String get_prenom() {
        return _prenom;
    }
    public Date get_date_nais() {
        return _date_nais;
    }
    public double get_note_total() {
        return _note_total;
    }
    public double get_moyenne() {
        return _moyenne;
    }

    /**
     * Setters
     */

    public void set_num_eleve(String numEleve) {
        _num_eleve = numEleve;
    }
    public void set_num_ecole(String numEcole) {
        _num_ecole = numEcole;
    }
    public void set_nom(String nom) {
        _nom = nom;
    }
    public void set_prenom(String prenom) {
        _prenom = prenom;
    }
    public void set_date_nais(Date dateNais) {
        _date_nais = dateNais;
    }
    public void set_note_total(double noteTotal) {
        _note_total = noteTotal;
    }
    public void set_moyenne(double moyenne) {
        _note_total = moyenne;
    }

    /**
     * Input validator
     * @return {@code true} if all attributes have value, otherwise {@code false}
     */
    public boolean isValidated() {
        if (_num_eleve == null || _num_eleve.isEmpty()) {
            System.out.println("VALIDATOR ERROR: Student's number should not be empty or null.");
            return false;
        }
        if (_num_ecole == null || _num_ecole.isEmpty()) {
            System.out.println("VALIDATOR ERROR: School's number should not be empty or null.");
            return false;
        }
        if (_nom == null || _nom.isEmpty()) {
            System.out.println("VALIDATOR ERROR: Student's firstname should not be empty or null.");
            return false;
        }
        if (_prenom == null || _prenom.isEmpty()) {
            System.out.println("VALIDATOR ERROR: Student's lastname should not be empty or null.");
            return false;
        }
        if (_date_nais == null) {
            System.out.println("VALIDATOR ERROR: Student's date of birth should not be null.");
            return false;
        }
        return true;
    }

    /**
     * Verify if a record already exists in database
     * @return {@code true} if the primary key value exists in database,
     * otherwise {@code false}
     */
    public boolean isExists() {
        DbManager db = new DbManager();
        Connection db_con = null;
        ResultSet rs = null;
        try {
            if (db.isConnected()) {
                db_con = db.getConnection();
                String sql_select = "SELECT COUNT(*) FROM eleve WHERE numEleve=? AND numEcole=?";
                try (PreparedStatement p_stmt = db_con.prepareStatement(sql_select)) {
                    p_stmt.setString(1, _num_eleve);
                    p_stmt.setString(2, _num_ecole);

                    try {
                        rs = p_stmt.executeQuery();
                        if (rs.next()) {
                            int count = rs.getInt(1);
                            return count > 0;
                        }
                    } catch (SQLException e) {
                        System.out.println(STR."Error querying records: \{e.getMessage()}");
                    }
                }
            } else {
                throw new SQLException("Database connection error.");
            }
        } catch (SQLException e) {
            System.out.println(STR."Error fetching records: \{e.getMessage()}");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println(STR."Error closing result set: \{e.getMessage()}");
                }
            }
            if (db_con != null) {
                try {
                    db_con.close();
                } catch (SQLException e) {
                    System.out.println(STR."Error closing connection: \{e.getMessage()}");
                }
            }
        }
        return false;
    }

    /**
     * Insert a record to database
     */
    public void create() {
        DbManager db = new DbManager();
        Connection db_con = null;
        try {
            if (db.isConnected()) {
                db_con = db.getConnection();
                String sql_insert = "INSERT INTO eleve(numEleve, numEcole, nom, prenom, dateNais, note_total, moyenne) " +
                                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement p_stmt = db_con.prepareStatement(sql_insert)) {
                    p_stmt.setString(1, _num_eleve);
                    p_stmt.setString(2, _num_ecole);
                    p_stmt.setString(3, _nom.toUpperCase());
                    p_stmt.setString(4, _prenom);
                    p_stmt.setDate(5, _date_nais);
                    p_stmt.setDouble(6, _note_total);
                    p_stmt.setDouble(7, _moyenne);

                    int rowsAffected = p_stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Data inserted successfully.");
                        isCreated = true;
                    } else {
                        System.out.println("No data inserted.");
                        isCreated = false;
                    }
                }
            } else {
                isCreated = false;
                throw new SQLException("Database connection error.");
            }
        } catch (SQLException e) {
            System.out.println(STR."Error inserting data: \{e.getMessage()}");
            isCreated = false;
        } finally {
            if (db_con != null) {
                try {
                    db_con.close();
                } catch (SQLException e) {
                    System.out.println(STR."Error closing connection: \{e.getMessage()}");
                }
            }
        }
    }

    /**
     * Verify the result of create operation
     * @return {@code true} if the record has been added to database,
     * otherwise {@code false}
     */
    public boolean isCreated() {
        return isCreated;
    }

    /**
     * Fetch all records from database order by name
     * @return List of records
     */
    public List<String[]> getAllRecords() {
        List<String[]> records = new ArrayList<>();

        DbManager db = new DbManager();
        Connection db_con = null;
        ResultSet rs = null;
        try {
            if (db.isConnected()) {
                db_con = db.getConnection();
                String sql_select = "SELECT * FROM eleve ORDER BY nom ASC";
                try (PreparedStatement p_stmt = db_con.prepareStatement(sql_select)) {
                    rs = p_stmt.executeQuery();

                    while (rs.next()) {
                        String numEleve = rs.getString("numEleve");
                        String numEcole = rs.getString("numEcole");
                        String nom = rs.getString("nom");
                        String prenom = rs.getString("prenom");
                        Date dateNais = rs.getDate("dateNais");
                        double noteTotal = rs.getDouble("note_total");
                        double moyenne = rs.getDouble("moyenne");

                        records.add(new String[]{numEleve, numEcole, nom, prenom, String.valueOf(dateNais),
                                String.valueOf(noteTotal), String.valueOf(moyenne)});
                    }
                }
            } else {
                throw new SQLException("Database connection error.");
            }
        } catch (SQLException e) {
                System.out.println(STR."Error fetching records: \{e.getMessage()}");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println(STR."Error closing result set: \{e.getMessage()}");
                }
            }
            if (db_con != null) {
                try {
                    db_con.close();
                } catch (SQLException e) {
                    System.out.println(STR."Error closing connection: \{e.getMessage()}");
                }
            }
        }
        return records;
    }

    /**
     * Fetch all records from database order by name
     * @return List of records
     */
    public List<String[]> getAllRecordsOrderByMerit() {
        List<String[]> records = new ArrayList<>();

        DbManager db = new DbManager();
        Connection db_con = null;
        ResultSet rs = null;
        try {
            if (db.isConnected()) {
                db_con = db.getConnection();
                String sql_select = "SELECT eleve.*, SUM(matiere.coef * note.note) AS totalScore FROM eleve " +
                                    "JOIN note ON eleve.numEleve = note.numEleve " +
                                    "JOIN matiere ON note.numMat = matiere.numMat " +
                                    "GROUP BY eleve.numEleve, eleve.nom, eleve.prenom" +
                                    "ORDER BY totalScore DESC";

                try (PreparedStatement p_stmt = db_con.prepareStatement(sql_select)) {
                    rs = p_stmt.executeQuery();

                    System.out.println("numEleve\t\tnumEcole\t\tnom\t\tprenom");

                    while (rs.next()) {
                        String numEleve = rs.getString("numEleve");
                        String numEcole = rs.getString("numEcole");
                        String nom = rs.getString("nom");
                        String prenom = rs.getString("prenom");
                        Date dateNais = rs.getDate("dateNais");
                        double noteTotal = rs.getDouble("note_total");
                        double moyenne = rs.getDouble("moyenne");

                        records.add(new String[]{numEleve, numEcole, nom, prenom, String.valueOf(dateNais),
                                String.valueOf(noteTotal), String.valueOf(moyenne)});
                    }
                }
            } else {
                throw new SQLException("Database connection error.");
            }
        } catch (SQLException e) {
            System.out.println(STR."Error fetching records: \{e.getMessage()}");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println(STR."Error closing result set: \{e.getMessage()}");
                }
            }
            if (db_con != null) {
                try {
                    db_con.close();
                } catch (SQLException e) {
                    System.out.println(STR."Error closing connection: \{e.getMessage()}");
                }
            }
        }
        return records;
    }

    /**
     * Update a record in database
     */
    public void update() {
        DbManager db = new DbManager();
        Connection db_con = null;
        try {
            if (db.isConnected()) {
                db_con = db.getConnection();
                String sql_update = "UPDATE eleve SET nom=?, prenom=?, dateNais=?, note_total=?, moyenne=? " +
                                    "WHERE numEleve=? AND numEcole=?";
                try (PreparedStatement p_stmt = db_con.prepareStatement(sql_update)) {
                    p_stmt.setString(1, _nom.toUpperCase());
                    p_stmt.setString(2, _prenom);
                    p_stmt.setDate(3, _date_nais);
                    p_stmt.setDouble(4, _note_total);
                    p_stmt.setDouble(5, _moyenne);
                    p_stmt.setString(6, _num_eleve);
                    p_stmt.setString(7, _num_ecole);

                    int rowsAffected = p_stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Data updated successfully.");
                        isUpdated = true;
                    } else {
                        System.out.println("No data updated.");
                        isUpdated = false;
                    }
                }
            } else {
                isUpdated = false;
                throw new SQLException("Database connection error.");
            }
        } catch (SQLException e) {
            System.out.println(STR."Error updating record: \{e.getMessage()}");
            isUpdated = false;
        } finally {
            if (db_con != null) {
                try {
                    db_con.close();
                } catch (SQLException e) {
                    System.out.println(STR."Error closing connection: \{e.getMessage()}");
                }
            }
        }
    }

    /**
     * Verify the result of update operation
     * @return {@code true} if the record has been updated,
     * otherwise {@code false}
     */
    public boolean isUpdated() {
        return isUpdated;
    }

    /**
     * Delete a record in database
     */
    public void delete() {
        DbManager db = new DbManager();
        Connection db_con = null;
        try {
            if (db.isConnected()) {
                db_con = db.getConnection();
                String sql_delete = "DELETE FROM eleve WHERE numEleve=? AND numEcole=?";
                try (PreparedStatement p_stmt = db_con.prepareStatement(sql_delete)) {
                    p_stmt.setString(1, _num_eleve);
                    p_stmt.setString(2, _num_ecole);

                    int rowsAffected = p_stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Data deleted successfully.");
                        isDeleted = true;
                    } else {
                        System.out.println("No data deleted.");
                        isDeleted = false;
                    }
                }
            } else {
                isDeleted = false;
                throw new SQLException("Database connection error.");
            }
        } catch (SQLException e) {
            System.out.println(STR."Error deleting record: \{e.getMessage()}");
            isDeleted = false;
        } finally {
            if (db_con != null) {
                try {
                    db_con.close();
                } catch (SQLException e) {
                    System.out.println(STR."Error closing connection: \{e.getMessage()}");
                }
            }
        }
    }

    /**
     * Verify the result of delete operation
     * @return {@code true} if the record has been deleted from database,
     * otherwise {@code false}
     */
    public boolean isDeleted() {
        return isDeleted;
    }

    /**
     * Get all students in a specific school
     * @return List of all students
     */
    public List<String[]> getStudentsInSchool() {
        List<String[]> studentsRecords = new ArrayList<>();

        DbManager db = new DbManager();
        Connection db_con = null;
        ResultSet rs = null;
        try {
            if (db.isConnected()) {
                db_con = db.getConnection();
                String sql_select = "SELECT * FROM eleve WHERE numEcole=? ORDER BY nom";
                try (PreparedStatement p_stmt = db_con.prepareStatement(sql_select)){
                    p_stmt.setString(1, _num_ecole);
                    rs = p_stmt.executeQuery();

                    while (rs.next()) {
                        String numEleve = rs.getString("numEleve");
                        String numEcole = rs.getString("numEcole");
                        String nom = rs.getString("nom");
                        String prenom = rs.getString("prenom");
                        Date dateNais = rs.getDate("dateNais");
                        double noteTotal = rs.getDouble("note_total");
                        double moyenne = rs.getDouble("moyenne");

                        studentsRecords.add(new String[]{numEleve, numEcole, nom, prenom, String.valueOf(dateNais),
                                String.valueOf(noteTotal), String.valueOf(moyenne)});
                    }
                }
            } else {
                throw new SQLException("Database connection error.");
            }
        } catch (SQLException e) {
            System.out.println(STR."Error fetching records: \{e.getMessage()}");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println(STR."Error closing result set: \{e.getMessage()}");
                }
            }
            if (db_con != null) {
                try {
                    db_con.close();
                } catch (SQLException e) {
                    System.out.println(STR."Error closing connection: \{e.getMessage()}");
                }
            }
        }
        return studentsRecords;
    }

    /**
     * Get a specific student
     * @return array of student information
     */
    public String[] getStudent() {
        String[] studentArray = new String[7];

        DbManager db = new DbManager();
        Connection db_con = null;
        ResultSet rs = null;
        try {
            if (db.isConnected()) {
                db_con = db.getConnection();
                String sql_select = "SELECT * FROM eleve WHERE numEleve=? AND numEcole=?";
                try (PreparedStatement p_stmt = db_con.prepareStatement(sql_select)){
                    p_stmt.setString(1, _num_eleve);
                    p_stmt.setString(2, _num_ecole);
                    rs = p_stmt.executeQuery();

                    while (rs.next()) {
                        studentArray[0] = rs.getString("numEleve");
                        studentArray[1] = rs.getString("numEcole");
                        studentArray[2] = rs.getString("nom");
                        studentArray[3] = rs.getString("prenom");
                        studentArray[4] = String.valueOf(rs.getDate("dateNais"));
                        studentArray[5] = String.valueOf(rs.getDouble("note_total"));
                        studentArray[6] = String.valueOf(rs.getDouble("moyenne"));
                    }
                }
            } else {
                throw new SQLException("Database connection error.");
            }
        } catch (SQLException e) {
            System.out.println(STR."Error fetching records: \{e.getMessage()}");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println(STR."Error closing result set: \{e.getMessage()}");
                }
            }
            if (db_con != null) {
                try {
                    db_con.close();
                } catch (SQLException e) {
                    System.out.println(STR."Error closing connection: \{e.getMessage()}");
                }
            }
        }
        return studentArray;
    }

    /**
     * Get the total score obtained by a student
     * @return student's total score value
     */
    public double getTotalScore() {
        double TotalScore = 0;

        DbManager db = new DbManager();
        Connection db_con = null;
        ResultSet rs = null;
        try {
            if (db.isConnected()) {
                db_con = db.getConnection();
                String sql_select = "SELECT SUM(matiere.coef * note.note) AS totalScore FROM eleve " +
                                    "JOIN note ON eleve.numEleve = note.numEleve " +
                                    "JOIN matiere ON note.numMat = matiere.numMat " +
                                    "WHERE eleve.numEleve=?";
                try (PreparedStatement p_stmt = db_con.prepareStatement(sql_select)){
                    p_stmt.setString(1, _num_eleve);
                    rs = p_stmt.executeQuery();

                    while (rs.next()) {
                        TotalScore = rs.getInt("totalScore");
                    }
                }
            } else {
                throw new SQLException("Database connection error.");
            }
        } catch (SQLException e) {
            System.out.println(STR."Error fetching records: \{e.getMessage()}");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println(STR."Error closing result set: \{e.getMessage()}");
                }
            }
            if (db_con != null) {
                try {
                    db_con.close();
                } catch (SQLException e) {
                    System.out.println(STR."Error closing connection: \{e.getMessage()}");
                }
            }
        }
        return TotalScore;
    }

    /**
     * Get all students concerned by the {@code filterValue} parameter in a specific school
     * @param filterValue value to compare to the students name
     * @return List of students
     */
    public List<String[]> getFilteredStudentInSchool(String filterValue) {
        List<String[]> records = new ArrayList<>();

        DbManager db = new DbManager();
        Connection db_con = null;
        ResultSet rs = null;
        try {
            if (db.isConnected()) {
                db_con = db.getConnection();
                String sql_select = "SELECT * FROM eleve WHERE nom like ? OR prenom like ? AND numEcole=? ORDER BY nom";
                try (PreparedStatement p_stmt = db_con.prepareStatement(sql_select)) {
                    p_stmt.setString(1, '%' + filterValue + '%');
                    p_stmt.setString(2, '%' + filterValue + '%');
                    p_stmt.setString(3, _num_ecole);
                    rs = p_stmt.executeQuery();

                    while (rs.next()) {
                        String numEleve = rs.getString("numEleve");
                        String numEcole = rs.getString("numEcole");
                        String nom = rs.getString("nom");
                        String prenom = rs.getString("prenom");
                        Date dateNais = rs.getDate("dateNais");
                        double noteTotal = rs.getDouble("note_total");
                        double moyenne = rs.getDouble("moyenne");

                        records.add(new String[]{numEleve, numEcole, nom, prenom, String.valueOf(dateNais),
                                String.valueOf(noteTotal), String.valueOf(moyenne)});
                    }
                }
            } else {
                throw new SQLException("Database connection error.");
            }
        } catch (SQLException e) {
            System.out.println(STR."Error fetching records: \{e.getMessage()}");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println(STR."Error closing result set: \{e.getMessage()}");
                }
            }
            if (db_con != null) {
                try {
                    db_con.close();
                } catch (SQLException e) {
                    System.out.println(STR."Error closing connection: \{e.getMessage()}");
                }
            }
        }
        return records;
    }
    /**
     * Get all students concerned by the {@code filterValue} parameter in a specific school
     * @param filterValue value to compare to the students name
     * @return List of students
     */
    public List<String[]> getFilteredStudent(String filterValue) {
        List<String[]> records = new ArrayList<>();

        DbManager db = new DbManager();
        Connection db_con = null;
        ResultSet rs = null;
        try {
            if (db.isConnected()) {
                db_con = db.getConnection();
                String sql_select = "SELECT * FROM eleve WHERE nom like ? OR prenom like ? ORDER BY nom";
                try (PreparedStatement p_stmt = db_con.prepareStatement(sql_select)) {
                    p_stmt.setString(1, '%' + filterValue + '%');
                    p_stmt.setString(2, '%' + filterValue + '%');
                    rs = p_stmt.executeQuery();

                    while (rs.next()) {
                        String numEleve = rs.getString("numEleve");
                        String numEcole = rs.getString("numEcole");
                        String nom = rs.getString("nom");
                        String prenom = rs.getString("prenom");
                        Date dateNais = rs.getDate("dateNais");
                        double noteTotal = rs.getDouble("note_total");
                        double moyenne = rs.getDouble("moyenne");

                        records.add(new String[]{numEleve, numEcole, nom, prenom, String.valueOf(dateNais),
                                String.valueOf(noteTotal), String.valueOf(moyenne)});
                    }
                }
            } else {
                throw new SQLException("Database connection error.");
            }
        } catch (SQLException e) {
            System.out.println(STR."Error fetching records: \{e.getMessage()}");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println(STR."Error closing result set: \{e.getMessage()}");
                }
            }
            if (db_con != null) {
                try {
                    db_con.close();
                } catch (SQLException e) {
                    System.out.println(STR."Error closing connection: \{e.getMessage()}");
                }
            }
        }
        return records;
    }

    /**
     * Update the score total obtained by a student
     */
    public void updateTotalScore(double noteTotale) {
        DbManager db = new DbManager();
        Connection db_con = null;
        try {
            if (db.isConnected()) {
                db_con = db.getConnection();
                String sql_update = "UPDATE eleve SET note_total=? " +
                        "WHERE numEleve=? AND numEcole=?";
                try (PreparedStatement p_stmt = db_con.prepareStatement(sql_update)) {
                    p_stmt.setDouble(1, noteTotale);
                    p_stmt.setString(2, _num_eleve);
                    p_stmt.setString(3, _num_ecole);

                    int rowsAffected = p_stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Score updated successfully.");
                        isUpdated = true;
                    } else {
                        System.out.println("No data updated.");
                        isUpdated = false;
                    }
                }
            } else {
                isUpdated = false;
                throw new SQLException("Database connection error.");
            }
        } catch (SQLException e) {
            System.out.println(STR."Error updating record: \{e.getMessage()}");
            isUpdated = false;
        } finally {
            if (db_con != null) {
                try {
                    db_con.close();
                } catch (SQLException e) {
                    System.out.println(STR."Error closing connection: \{e.getMessage()}");
                }
            }
        }
    }

    /**
     * Update the average score obtained by a student
     */
    public void updateAverageScore(double moyenne) {
        DbManager db = new DbManager();
        Connection db_con = null;
        try {
            if (db.isConnected()) {
                db_con = db.getConnection();
                String sql_update = "UPDATE eleve SET moyenne=? " +
                        "WHERE numEleve=? AND numEcole=?";
                try (PreparedStatement p_stmt = db_con.prepareStatement(sql_update)) {
                    p_stmt.setDouble(1, moyenne);
                    p_stmt.setString(2, _num_eleve);
                    p_stmt.setString(3, _num_ecole);

                    int rowsAffected = p_stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Score updated successfully.");
                        isUpdated = true;
                    } else {
                        System.out.println("No data updated.");
                        isUpdated = false;
                    }
                }
            } else {
                isUpdated = false;
                throw new SQLException("Database connection error.");
            }
        } catch (SQLException e) {
            System.out.println(STR."Error updating record: \{e.getMessage()}");
            isUpdated = false;
        } finally {
            if (db_con != null) {
                try {
                    db_con.close();
                } catch (SQLException e) {
                    System.out.println(STR."Error closing connection: \{e.getMessage()}");
                }
            }
        }
    }
}
