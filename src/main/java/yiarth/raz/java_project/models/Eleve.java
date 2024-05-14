package yiarth.raz.java_project.models;

import yiarth.raz.java_project.config.DbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Eleve {

    private String _num_eleve;
    private String _num_ecole;
    private String _nom;
    private String _prenom;

    private boolean isCreated;
    private boolean isUpdated;
    private boolean isDeleted;

    /**
     * Constructors
     */
    public Eleve() {}
    public Eleve(String numEleve, String numEcole, String nom, String prenom) {
        _num_eleve = numEleve;
        _num_ecole = numEcole;
        _nom = nom;
        _prenom = prenom;
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
     * @return String (private attributes value)
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

    /**
     * Input validator
     * @return true/false
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
                String sql_insert = "INSERT INTO eleve(numEleve, numEcole, nom, prenom) VALUES (?, ?, ?, ?)";
                try (PreparedStatement p_stmt = db_con.prepareStatement(sql_insert)) {
                    p_stmt.setString(1, _num_eleve);
                    p_stmt.setString(2, _num_ecole);
                    p_stmt.setString(3, _nom);
                    p_stmt.setString(4, _prenom);

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
                String sql_select = "SELECT * FROM eleve";
                try (PreparedStatement p_stmt = db_con.prepareStatement(sql_select)) {
                    rs = p_stmt.executeQuery();

                    System.out.println("numEleve\t\tnumEcole\t\tnom\t\tprenom");

                    while (rs.next()) {
                        String numEleve = rs.getString("numEleve");
                        String numEcole = rs.getString("numEcole");
                        String nom = rs.getString("nom");
                        String prenom = rs.getString("prenom");

                        System.out.println(STR."\{numEleve}\t\t\{numEcole}\t\t\{nom}\t\t\{prenom}");

                        records.add(new String[]{numEleve, numEcole, nom, prenom});
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
                String sql_update = "UPDATE eleve SET nom=?, prenom=? WHERE numEleve=? AND numEcole=?";
                try (PreparedStatement p_stmt = db_con.prepareStatement(sql_update)) {
                    p_stmt.setString(1, _nom);
                    p_stmt.setString(2, _prenom);
                    p_stmt.setString(3, _num_eleve);
                    p_stmt.setString(4, _num_ecole);

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
     * @return true/false
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
                String sql_select = "SELECT * FROM eleve WHERE numEcole=?";
                try (PreparedStatement p_stmt = db_con.prepareStatement(sql_select)){
                    p_stmt.setString(1, _num_ecole);
                    rs = p_stmt.executeQuery();

                    while (rs.next()) {
                        String numEleve = rs.getString("numEleve");
                        String numEcole = rs.getString("numEcole");
                        String nom = rs.getString("nom");
                        String prenom = rs.getString("prenom");

                        studentsRecords.add(new String[]{numEleve, numEcole, nom, prenom});
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
     * Get the total score obtained by a student
     * @return Int (student's total score value)
     */
    public int getTotalScore() {
        int TotalScore = 0;

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
}
