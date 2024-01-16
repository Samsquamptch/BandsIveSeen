package src;


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnector {

    public static Connection connect() {
        String url = "jdbc:sqlite:src/bisDatabase.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

}