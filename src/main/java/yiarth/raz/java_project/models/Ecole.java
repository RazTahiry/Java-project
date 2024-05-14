package yiarth.raz.java_project.models;

import yiarth.raz.java_project.config.DbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Ecole {

    private String _num_ecole;
    private String _design;
    private String _adresse;

    private boolean isCreated;
    private boolean isUpdated;
    private boolean isDeleted;

    /**
     * Constructors
     */
    public Ecole() {}
    public Ecole(String numEcole, String design, String adresse) {
        _num_ecole = numEcole;
        _design = design;
        _adresse = adresse;
    }
    public Ecole(String numEcole) {
        _num_ecole = numEcole;
    }

    /**
     * Getters
     * @return String (private attributes value)
     */
    public String get_num_ecole() {
        return _num_ecole;
    }
    public String get_design() {
        return _design;
    }
    public  String get_adresse() {
        return _adresse;
    }

    /**
     * Setters
     */
    public void set_num_ecole(String numEcole) {
        _num_ecole = numEcole;
    }
    public void set_design(String design) {
        _design = design;
    }
    public void set_adresse(String adresse) {
        _adresse = adresse;
    }

    /**
     * Input validator
     * @return true/false
     */
    public boolean isValidated() {
        if (_num_ecole == null || _num_ecole.isEmpty()) {
            System.out.println("VALIDATOR ERROR: School number should not be empty or null.");
            return false;
        }
        if (_design == null || _design.isEmpty()) {
            System.out.println("VALIDATOR ERROR: School name should not be empty or null.");
            return false;
        }
        if (_adresse == null || _adresse.isEmpty()) {
            System.out.println("VALIDATOR ERROR: School address should not not be empty or null.");
            return false;
        }
        return true;
    }

    /**
     * Verify if a record already exists in database
     * @return true/false
     */
    public boolean isExists() {
        DbManager db = new DbManager();
        Connection db_con = null;
        ResultSet rs = null;
        try {
            if (db.isConnected()) {
                db_con = db.getConnection();
                String sql_select = "SELECT COUNT(*) FROM ecole WHERE numEcole=?";
                try (PreparedStatement p_stmt = db_con.prepareStatement(sql_select)) {
                    p_stmt.setString(1, _num_ecole);

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
                String sql_insert = "INSERT INTO ecole VALUES (?, ?, ?)";
                try (PreparedStatement p_stmt = db_con.prepareStatement(sql_insert)) {
                    p_stmt.setString(1, _num_ecole);
                    p_stmt.setString(2, _design);
                    p_stmt.setString(3, _adresse);

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
     * @return true/false
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
                String sql_select = "SELECT * FROM ecole";
                try (PreparedStatement p_stmt = db_con.prepareStatement(sql_select)) {
                    rs = p_stmt.executeQuery();

                    System.out.println("numEcole\t\tdesign\t\tadresse");

                    while (rs.next()) {
                        String numEcole = rs.getString("numEcole");
                        String design = rs.getString("design");
                        String adresse = rs.getString("adresse");

                        System.out.println(STR."\{numEcole}\t\t\{design}\t\t\{adresse}");

                        records.add(new String[]{numEcole, design, adresse});
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
     * Update a record in the database
     */
    public void update() {
        DbManager db = new DbManager();
        Connection db_con = null;
        try {
            if (db.isConnected()) {
                db_con = db.getConnection();
                String sql_update = "UPDATE ecole SET design=?, adresse=? WHERE numEcole=?";
                try (PreparedStatement p_stmt = db_con.prepareStatement(sql_update)) {
                    p_stmt.setString(1, _design);
                    p_stmt.setString(2, _adresse);
                    p_stmt.setString(3, _num_ecole);

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
     * @return true/false
     */
    public boolean isUpdated() {
        return isUpdated;
    }

    /**
     * Delete a record in the database
     */
    public void delete() {
        DbManager db = new DbManager();
        Connection db_con = null;
        try {
            if (db.isConnected()) {
                db_con = db.getConnection();
                String sql_delete = "DELETE FROM ecole WHERE numEcole=?";
                try (PreparedStatement p_stmt = db_con.prepareStatement(sql_delete)) {
                    p_stmt.setString(1, _num_ecole);

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
     * @return true/false
     */
    public boolean isDeleted() {
        return isDeleted;
    }
}