package src;

import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class ReadDatabase {

    public static String[] selectFriends(Connection conn) {
        String sql = "SELECT FriendName FROM Friend";
        ArrayList<String> friendList = new ArrayList<>();
        friendList.add("Add Friend");

        try (Statement queryStatement = conn.createStatement();
             ResultSet queryResult = queryStatement.executeQuery(sql))
        {
            while (queryResult.next()) {
                friendList.add(queryResult.getString("FriendName"));
            }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
        friendList.add("Add New Friend");
        String[] friendArray = new String[friendList.size()];
        friendArray = friendList.toArray(friendArray);
        return friendArray;
    }

    public static String[] selectBands(Connection conn) {
        String sql = "SELECT BandName, Genre, Country FROM Band";
        ArrayList<String> bandData = new ArrayList<>();
        bandData.add("Select a Band");

        try (Statement queryStatement = conn.createStatement();
             ResultSet queryResult = queryStatement.executeQuery(sql)) {

            while (queryResult.next()) {
                bandData.add(queryResult.getString("BandName") + " - " +
                        queryResult.getString("Country") + " - " +
                        queryResult.getString("Genre"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        bandData.add("Add New Band");
        String[] bandArray = new String[bandData.size()];
        bandArray = bandData.toArray(bandArray);
        return bandArray;
    }

    public static String[] selectVenues(Connection conn) {
        String sql = "SELECT VenueName, Location FROM Venue";
        ArrayList<String> venueData = new ArrayList<>();
        venueData.add("Select a Venue");

        try (Statement queryStatement = conn.createStatement();
             ResultSet queryResult = queryStatement.executeQuery(sql)) {

            while (queryResult.next()) {
                venueData.add(queryResult.getString("VenueName") + " - " +
                        queryResult.getString("Location"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        venueData.add("Add New Venue");
        String[] venueArray = new String[venueData.size()];
        venueArray = venueData.toArray(venueArray);
        return venueArray;
    }

    public static boolean checkExists(Connection conn, String table, String column1, String column2,
                                          String name, String identifier) throws SQLException {
        PreparedStatement ps =
                conn.prepareStatement
                        ("SELECT " + column1 + " FROM " + table + " WHERE " + column1 + " = ? AND " + column2 + " = ?");
        ps.setString (1, name);
        ps.setString (2, identifier);
        ResultSet rs = ps.executeQuery();
        return !rs.next();
    }

    public static boolean checkFriend(Connection conn, String friendName) throws SQLException {
        PreparedStatement ps =
                conn.prepareStatement
                        ("SELECT * FROM Friend WHERE FriendName = ?");
        ps.setString (1, friendName);
        ResultSet rs = ps.executeQuery();

        return rs.next();
    }

    public static int getFriendId(Connection conn, String friendName) throws SQLException {

        PreparedStatement ps =
                conn.prepareStatement
                        ("SELECT Id FROM Friend WHERE FriendName = ?");
        ps.setString (1, friendName);
        ResultSet rs = ps.executeQuery();
        return rs.getInt("Id");
    }

    public static boolean checkGig(Connection conn, String headline, String gigDate) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT Gig.Date AS Date, Venue.VenueName AS Venue, " +
                "Band.BandName AS Headline FROM Gig INNER JOIN Venue ON Gig.Venue_Id = Venue.Id " +
                "INNER JOIN Band ON Gig.Headline = Band.Id WHERE Gig.Date = ? AND Band.BandName = ?");
        ps.setString(1, gigDate);
        ps.setString(2, headline);
        ResultSet rs = ps.executeQuery();

        return rs.next();
    }

    public static Object[][] selectGigs(Connection conn) throws SQLException {
        int arrayLength = getLastId(conn, "Performance");

        PreparedStatement ps = conn.prepareStatement("SELECT Gig.Id AS ID, Gig.Date AS Date, Band.BandName AS Artist, Venue.VenueName " +
                "AS Venue, Performance.Rating AS Rating FROM Gig JOIN Performance ON Gig.Id = Performance.Gig_Id JOIN Band ON " +
                "Band.Id = Performance.Band_Id JOIN Venue ON Venue.Id = Gig.Venue_Id ORDER BY Gig.Date, Performance.Id");
        ResultSet rs = ps.executeQuery();

        Object[][] bandTable = new String[arrayLength][4];
        int i = 0;

        while (rs.next()) {
            bandTable[i][0] = rs.getString("Artist");
            bandTable[i][1] = rs.getString("Date");
            bandTable[i][2] = rs.getString("Venue");
            bandTable[i][3] = rs.getString("Rating");
            i++;
        }
    return bandTable;
    }

    public static int getBandId(Connection conn, String bandName, String bandCountry) throws SQLException {
        PreparedStatement ps = conn.prepareStatement
                        ("SELECT Id FROM Band WHERE BandName = ? AND Country = ?");
        ps.setString (1, bandName);
        ps.setString (2, bandCountry);
        ResultSet rs = ps.executeQuery();
        return rs.getInt("Id");
    }

    public static int getVenueId(Connection conn, String venueName, String Location) throws SQLException {
        PreparedStatement ps = conn.prepareStatement
                        ("SELECT Id FROM Venue WHERE VenueName = ? AND Location = ?");
        ps.setString (1, venueName);
        ps.setString (2, Location);
        ResultSet rs = ps.executeQuery();
        return rs.getInt("Id");
    }

    public static int getLastId(Connection conn, String table) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT Max(Id) AS tableId FROM " + table);
        ResultSet rs = ps.executeQuery();
        return rs.getInt("tableId");
    }

/*    public static String[] getVenueData(Connection conn, String name) throws SQLException {
        PreparedStatement ps =
                conn.prepareStatement
                        ("SELECT Name, Location FROM Venue WHERE Name = ?");
        ps.setString (1, name);
        ResultSet rs = ps.executeQuery();
        String[] result = new String[2];
        result[0] = rs.getString("Name");
        result[1] = rs.getString("Location");
        return result;
    }*/
}
