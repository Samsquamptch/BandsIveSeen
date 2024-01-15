package src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WriteDatabase {

    public static void insert(String name, String genre, String country) {
        String sql = "INSERT INTO Band(Name,Genre, Country) VALUES(?,?,?)";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, genre);
            pstmt.setString(3, country);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
