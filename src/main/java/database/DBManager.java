package database;

import data.CheckListItem;
import data.DataManagement;

import java.sql.*;

public class DBManager {

    private static final DBManager instance = new DBManager();
    private static final String DB_URL = "jdbc:sqlite:mydb.db";

    private DBManager() {
        // private constructor
    }

    public static DBManager getInstance() {
        return instance;
    }

    @Deprecated
    public void createDB() {
        String createTable = """
            CREATE TABLE IF NOT EXISTS test (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                dueDate TEXT NOT NULL,
                dueTime TEXT NOT NULL,
                isCompleted BOOLEAN NOT NULL,
                createdDate TEXT NOT NULL,
                createdTime TEXT NOT NULL,
                priority INTEGER NOT NULL,
                category TEXT,
                notes TEXT,
                reminder BOOLEAN NOT NULL
            );
        """;


        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            stmt.execute(createTable);
            System.out.println("Database and table created.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    public void insertDB(String name) {
        String url = "jdbc:sqlite:mydb.db";
        String sql = "INSERT INTO test(name) VALUES (?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showDataBase() {
        String url = "jdbc:sqlite:mydb.db";
        String sql = "SELECT * FROM test";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(rs.getInt(1) + " | " + rs.getString(2) + " due: " + rs.getString(3) + rs.getString(4) + " Completed: " + rs.getBoolean(5));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized void clearOnlyDatabase() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:mydb.db");
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("DELETE FROM test");

            try {
                stmt.executeUpdate("DELETE FROM sqlite_sequence WHERE name = 'test'");
            } catch (SQLException ignore) {
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized void updateDataBase() {
//        System.err.println("start updating DataBase");
        String deleteSql = "DELETE FROM test";
        String resetSeq  = "DELETE FROM sqlite_sequence WHERE name = 'test'";
        String insertSql = "INSERT INTO test(" +
                "name, dueDate, dueTime, isCompleted, " +
                "createdDate, createdTime, priority, category, notes, reminder) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement  stmt  = conn.createStatement();
             PreparedStatement ps = conn.prepareStatement(insertSql)) {

            stmt.executeUpdate(deleteSql);
            try { stmt.executeUpdate(resetSeq); } catch (SQLException ignore) {}

            for (CheckListItem item : DataManagement.getInstance().getChecklist()) {
                ps.setString (1,  item.getCheckListName());
                ps.setString (2,  item.getDm().getDueDate());
                ps.setString (3,  item.getCm().getDueTime());
                ps.setBoolean(4,  item.getCompleted());
                ps.setString (5,  item.getDm().getCreatedDate());
                ps.setString (6,  item.getCm().getCreatedTime());
                ps.setInt    (7,  item.getPriority().getInt());
                ps.setString (8,  item.getCategory());
                ps.setString (9,  item.getNotes());
                ps.setBoolean(10, item.isReminder());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        System.err.println("finished updating DataBase");
    }


    public synchronized void updateMemory() {
        String selectSql = "SELECT * FROM test";
        DataManagement.getInstance().getChecklist().clear();

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement  stmt = conn.createStatement();
             ResultSet  rs   = stmt.executeQuery(selectSql)) {

            while (rs.next()) {
                CheckListItem item = new CheckListItem(
                        rs.getString("name"),
                        rs.getString("dueDate"),
                        rs.getString("dueTime"),
                        rs.getBoolean("isCompleted"),
                        rs.getString("createdDate"),
                        rs.getString("createdTime"),
                        rs.getInt("priority"),
                        rs.getString("category"),
                        rs.getString("notes"),
                        rs.getBoolean("reminder"));
                DataManagement.getInstance().updateList(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
