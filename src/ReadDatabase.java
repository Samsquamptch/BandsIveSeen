package src;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReadDatabase {

    public static void selectAll(){
        String sql = "SELECT id, Name, Genre, Country FROM Band";

        try (Connection conn = DatabaseConnector.connect();
             Statement queryStatement  = conn.createStatement();
             ResultSet queryResult    = queryStatement.executeQuery(sql)){

            while (queryResult.next()) {
                System.out.println(queryResult.getInt("id") +  "\t" +
                        queryResult.getString("Name") + "\t" +
                        queryResult.getString("Genre") + "\t" +
                        queryResult.getString("Country"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
