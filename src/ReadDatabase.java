package src;

import java.sql.*;
import java.sql.PreparedStatement;

public class ReadDatabase {

    public static void selectBands() {
        String sql = "SELECT * FROM Band";

        try (Connection conn = DatabaseConnector.connect();
             Statement queryStatement = conn.createStatement();
             ResultSet queryResult = queryStatement.executeQuery(sql)) {

            while (queryResult.next()) {
                System.out.println(queryResult.getInt("id") + "\t" +
                        queryResult.getString("Name") + "\t" +
                        queryResult.getString("Genre") + "\t" +
                        queryResult.getString("Country"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void selectVenues() {
        String sql = "SELECT * FROM Venue";

        try (Connection conn = DatabaseConnector.connect();
             Statement queryStatement = conn.createStatement();
             ResultSet queryResult = queryStatement.executeQuery(sql)) {

            while (queryResult.next()) {
                System.out.println(queryResult.getInt("id") + "\t" +
                        queryResult.getString("Name") + "\t" +
                        queryResult.getString("Location") + "\t" +
                        queryResult.getBoolean("isFestival"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean checkExists(String table, String column1, String column2,
                                          String name, String identifier) throws SQLException {
        Connection con = DatabaseConnector.connect();
        PreparedStatement ps =
                con.prepareStatement
                        ("SELECT " + column1 + " FROM " + table + " WHERE " + column1 + " = ? AND " + column2 + " = ?");
        ps.setString (1, name);
        ps.setString (2, identifier);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            rs.close();
            return true;
        } else {
            rs.close();
            return false;
        }
    }

    public static boolean checkVenueExists(String value) throws SQLException {
        Connection con = DatabaseConnector.connect();
        PreparedStatement ps =
                con.prepareStatement
                        ("SELECT Name FROM Venue WHERE Name = ?");
        ps.setString (1, value);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            rs.close();
            return true;
        } else {
            rs.close();
            return false;
        }
    }

    public static String[] getVenueData(String name) throws SQLException {
        Connection con = DatabaseConnector.connect();

        PreparedStatement ps =
                con.prepareStatement
                        ("SELECT Name, Location FROM Venue WHERE Name = ?");
        ps.setString (1, name);
        ResultSet rs = ps.executeQuery();
        String[] result = new String[2];
        result[0] = rs.getString("Name");
        result[1] = rs.getString("Location");
        return result;
    }
}
