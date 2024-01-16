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

    public static boolean checkFriend(String friendName) throws SQLException {
        Connection con = DatabaseConnector.connect();
        PreparedStatement ps =
                con.prepareStatement
                        ("SELECT * FROM Friend WHERE Name = ?");
        ps.setString (1, friendName);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            rs.close();
            return true;
        } else {
            rs.close();
            return false;
        }
    }

    public static int getFriendId(String friendName) throws SQLException {
        Connection con = DatabaseConnector.connect();

        PreparedStatement ps =
                con.prepareStatement
                        ("SELECT Id FROM Friend WHERE FriendName = ?");
        ps.setString (1, friendName);
        ResultSet rs = ps.executeQuery();
        return rs.getInt("Id");
    }

    public static boolean checkGig(String headline, String gigDate) throws SQLException {
        Connection con = DatabaseConnector.connect();
        PreparedStatement ps = con.prepareStatement("SELECT Gig.Date AS Date, Venue.VenueName AS Venue, " +
                "Band.BandName AS Headline FROM Gig INNER JOIN Venue ON Gig.Venue_Id = Venue.Id " +
                "INNER JOIN Band ON Gig.Headline = Band.Id WHERE Gig.Date = ? AND Band.BandName = ?");
        ps.setString(1, gigDate);
        ps.setString(2, headline);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            rs.close();
            return true;
        } else {
            rs.close();
            return false;
        }
    }

    public static void seclectGigs() {
        String sql = "SELECT * FROM Gig";

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

    public static int getBandId(String bandName, String bandCountry) throws SQLException {
        Connection con = DatabaseConnector.connect();

        PreparedStatement ps =
                con.prepareStatement
                        ("SELECT Id FROM Band WHERE BandName = ? AND Country = ?");
        ps.setString (1, bandName);
        ps.setString (2, bandCountry);
        ResultSet rs = ps.executeQuery();
        int returnValue = rs.getInt("Id");
        rs.close();
        return returnValue;
    }

    public static int getVenueId(String venueName, String Location) throws SQLException {
        Connection con = DatabaseConnector.connect();

        PreparedStatement ps =
                con.prepareStatement
                        ("SELECT Id FROM Venue WHERE VenueName = ? AND Location = ?");
        ps.setString (1, venueName);
        ps.setString (2, Location);
        ResultSet rs = ps.executeQuery();
        return rs.getInt("Id");
    }

    public static int getLastId(String table) throws SQLException {
        Connection con = DatabaseConnector.connect();

        PreparedStatement ps = con.prepareStatement("SELECT Max(Id) AS tableId FROM " + table);
        ResultSet rs = ps.executeQuery();
        int returnValue = rs.getInt("tableId");
        rs.close();
        return returnValue;
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
