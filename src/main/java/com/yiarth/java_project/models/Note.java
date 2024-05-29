package com.yiarth.java_project.models;

import com.yiarth.java_project.config.DbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Note {

    private String _annee_scolaire;
    private String _num_eleve;
    private String _num_mat;
    private double _note;

    private boolean isCreated;
    private boolean isUpdated;
    private boolean isDeleted;

    /**
     * Constructors
     */

    public Note() {}
    public Note(String anneeScolaire, String numEleve, String numMat, double note) {
        _annee_scolaire = anneeScolaire;
        _num_eleve = numEleve;
        _num_mat = numMat;
        _note = note;
    }
    public Note(String numEleve, String numMat) {
        _num_eleve = numEleve;
        _num_mat = numMat;
    }

    /**
     * Getters
     * @return the private attribute value
     */
    public String get_annee_scolaire() {
        return _annee_scolaire;
    }
    public String get_num_eleve() {
        return _num_eleve;
    }
    public String get_num_mat() {
        return _num_mat;
    }
    public double get_note() {
        return _note;
    }

    /**
     * Setters
     */

    public void set_annee_scolaire(String anneeScolaire) {
        _annee_scolaire = anneeScolaire;
    }
    public void set_num_eleve(String numEleve) {
        _num_eleve = numEleve;
    }
    public void set_num_mat(String numMat) {
        _num_mat = numMat;
    }
    public void set_note(double note) {
        _note = note;
    }

    /**
     * Input validator
     * @return {@code true} if all attributes have value, otherwise {@code false}
     */
    public boolean isValidated() {
        if (_annee_scolaire == null || _annee_scolaire.isEmpty()) {
            System.out.println("VALIDATOR ERROR: School year should not be empty or null.");
            return false;
        }
        if (_num_eleve == null || _num_eleve.isEmpty()) {
            System.out.println("VALIDATOR ERROR: Student's number should not be empty or null.");
            return false;
        }
        if (_num_mat == null || _num_mat.isEmpty()) {
            System.out.println("VALIDATOR ERROR: Subject's number should not be empty or null.");
            return false;
        }
        if (_note < 0 || _note > 20) {
            System.out.println("VALIDATOR ERROR: Score should be between 0 and 20.");
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
                String sql_select = "SELECT COUNT(*) FROM note WHERE numEleve=? AND numMat=?";
                try (PreparedStatement p_stmt = db_con.prepareStatement(sql_select)) {
                    p_stmt.setString(1, _num_eleve);
                    p_stmt.setString(2, _num_mat);

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
                String sql_insert = "INSERT INTO note VALUES (?, ?, ?, ?)";
                try (PreparedStatement p_stmt = db_con.prepareStatement(sql_insert)) {
                    p_stmt.setString(1, _annee_scolaire);
                    p_stmt.setString(2, _num_eleve);
                    p_stmt.setString(3, _num_mat);
                    p_stmt.setDouble(4, _note);

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
     * Fetch all records from database
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
                String sql_select = "SELECT * FROM note";
                try (PreparedStatement p_stmt = db_con.prepareStatement(sql_select)) {
                    rs = p_stmt.executeQuery();

                    while (rs.next()) {
                        String anneScolaire = rs.getString("anneeScolaire");
                        String numEleve = rs.getString("numEleve");
                        String numMat = rs.getString("numMat");
                        int note = rs.getInt("note");

                        records.add(new String[]{anneScolaire, numEleve, numMat, String.valueOf(note)});
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
                String sql_update = "UPDATE note SET anneeScolaire=?, note=? WHERE numEleve=? AND numMat=?";
                try (PreparedStatement p_stmt = db_con.prepareStatement(sql_update)) {
                    p_stmt.setString(1, _annee_scolaire);
                    p_stmt.setDouble(2, _note);
                    p_stmt.setString(3, _num_eleve);
                    p_stmt.setString(4, _num_mat);

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
                String sql_delete = "DELETE FROM note WHERE numEleve=? AND numMat=?";
                try (PreparedStatement p_stmt = db_con.prepareStatement(sql_delete)) {
                    p_stmt.setString(1, _num_eleve);
                    p_stmt.setString(2, _num_mat);

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
}
