package src.database;


import java.sql.*;
import java.util.Arrays;

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