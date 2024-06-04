package com.yiarth.java_project.models;

import com.yiarth.java_project.config.DbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Matiere {

    private String _num_mat;
    private String _design_mat;
    private int _coef;

    private boolean isCreated;
    private boolean isUpdated;
    private boolean isDeleted;

    /**
     * Constructors
     */

    public Matiere() {}
    public Matiere(String numMat, String designMat, int coef) {
        _num_mat = numMat;
        _design_mat = designMat;
        _coef = coef;
    }
    public Matiere(String numMat, String designMat) {
        _num_mat = numMat;
        _design_mat = designMat;
    }

    /**
     * Getters
     * @return the private attribute value
     */
    public String get_num_mat() {
        return _num_mat;
    }
    public String get_design_mat() {
        return _design_mat;
    }
    public int get_coef() {
        return _coef;
    }

    /**
     * Setters
     */

    public void set_num_mat(String numMat) {
        _num_mat = numMat;
    }
    public void set_design_mat(String designMat) {
        _design_mat = designMat;
    }
    public void set_coef(int coef) {
        _coef = coef;
    }

    /**
     * Input validator
     * @return {@code true} if all attributes have value, otherwise {@code false}
     */
    public boolean isValidated() {
        if (_num_mat == null || _num_mat.isEmpty()) {
            System.out.println("VALIDATOR ERROR: Subject's number should not be empty or null.");
            return false;
        }
        if (_design_mat == null || _design_mat.isEmpty()) {
            System.out.println("VALIDATOR ERROR: Subject's name should not be empty or null.");
            return false;
        }
        if (_coef < 0 || _coef > 3) {
            System.out.println("VALIDATOR ERROR: Subject's coefficient should be between 1 and 3.");
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
                String sql_select = "SELECT COUNT(*) FROM matiere WHERE numMat=? AND designMat=?";
                try (PreparedStatement p_stmt = db_con.prepareStatement(sql_select)) {
                    p_stmt.setString(1, _num_mat);
                    p_stmt.setString(2, _design_mat);

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
                String sql_insert = "INSERT INTO matiere VALUES (?, ?, ?)";
                try (PreparedStatement p_stmt = db_con.prepareStatement(sql_insert)){
                    p_stmt.setString(1, _num_mat);
                    p_stmt.setString(2, _design_mat);
                    p_stmt.setInt(3, _coef);

                    int rowsAffected = p_stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        // System.out.println("Data inserted successfully.");
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
                String sql_select = "SELECT * FROM matiere";
                try (PreparedStatement p_stmt = db_con.prepareStatement(sql_select)) {
                    rs = p_stmt.executeQuery();

                    while (rs.next()) {
                        String numMat = rs.getString("numMat");
                        String designMat = rs.getString("designMat");
                        int coef = rs.getInt("coef");

                        records.add(new String[]{numMat, designMat, String.valueOf(coef)});
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
                String sql_update = "UPDATE matiere SET coef=? WHERE numMat=? AND designMat=?";
                try (PreparedStatement p_stmt = db_con.prepareStatement(sql_update)) {
                    p_stmt.setInt(1, _coef);
                    p_stmt.setString(2, _num_mat);
                    p_stmt.setString(3, _design_mat);

                    int rowsAffected = p_stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        // System.out.println("Data updated successfully.");
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
                String sql_delete = "DELETE FROM matiere WHERE numMat=? AND designMat=?";
                try (PreparedStatement p_stmt = db_con.prepareStatement(sql_delete)) {
                    p_stmt.setString(1, _num_mat);
                    p_stmt.setString(2, _design_mat);

                    int rowsAffected = p_stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        // System.out.println("Data deleted successfully.");
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
