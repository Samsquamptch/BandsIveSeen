package src.database;

import src.eventObjects.Band;
import src.eventObjects.Venue;

import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;

public class ReadFromDatabase {

    public static String[] selectFriends(Connection conn, String[] friendsList, boolean addOrRemove) {
        String sql = "SELECT FriendName FROM Friend";
        ArrayList<String> friendList = new ArrayList<>();
        if (addOrRemove) {
            friendList.add("Add Friend");
        } else {
            friendList.add("Select Friend");
        }

        try (Statement queryStatement = conn.createStatement();
             ResultSet queryResult = queryStatement.executeQuery(sql))
        {
            while (queryResult.next()) {
                String friend = queryResult.getString("FriendName");
                if (friendsList==null || !Arrays.asList(friendsList).contains(friend)) {
                    friendList.add(friend);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (addOrRemove) {
            friendList.add("Add New Friend");
        }
        String[] friendArray = new String[friendList.size()];
        friendArray = friendList.toArray(friendArray);
        return friendArray;
    }

    public static String[] selectFestival(Connection conn) {
        String sql = "SELECT VenueName, Location FROM Venue WHERE isFestival = 1";
        ArrayList<String> festivalList = new ArrayList<>();
        festivalList.add("Select Festival");
        try (Statement queryStatement = conn.createStatement();
             ResultSet queryResult = queryStatement.executeQuery(sql))
        {
            while (queryResult.next()) {
                festivalList.add(queryResult.getString("VenueName") + " - " + queryResult.getString("Location"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        String[] festivalArray = new String[festivalList.size()];
        festivalArray = festivalList.toArray(festivalArray);
        return festivalArray;
    }

    public static String[] selectGigs(Connection conn) {
        String sql = "SELECT Gig.Date AS Date, Band.BandName AS Band, Venue.Id FROM Gig JOIN " +
                "Band on Gig.Headline = Band.Id JOIN Venue on Gig.Venue_Id = Venue.Id " +
                "WHERE Venue.isFestival = 0 ORDER BY Band.BandName, Gig.Date";
        ArrayList<String> gigList = new ArrayList<>();
        gigList.add("Select Gig");
        try (Statement queryStatement = conn.createStatement();
             ResultSet queryResult = queryStatement.executeQuery(sql))
        {
            while (queryResult.next()) {
                gigList.add(queryResult.getString("Band") + " - " + queryResult.getString("Date"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        String[] gigArray = new String[gigList.size()];
        gigArray = gigList.toArray(gigArray);
        return gigArray;
    }

    public static String[][] selectPerformances(Connection conn, String filterBy) throws SQLException {
        int arrayLength = getTableCount(conn, "Performance");

        PreparedStatement ps = conn.prepareStatement("SELECT Gig.Id AS ID, Gig.Date AS Date, Band.BandName AS Artist, " +
                "(Venue.VenueName || ' - ' || Venue.Location) AS Venue, Performance.Rating AS Rating, Gig.Headline Headline, " +
                "Band.Id AS BandId FROM Gig JOIN Performance ON Gig.Id = Performance.Gig_Id JOIN Band " +
                "ON Band.Id = Performance.Band_Id JOIN Venue ON Venue.Id = Gig.Venue_Id " + filterBy + "ORDER BY Date, Gig.Id, Performance.Id");
        ResultSet rs = ps.executeQuery();

        String[][] bandTable = new String[arrayLength][5];
        int i = 0;

        while (rs.next()) {
            if (rs.getInt("Headline") != rs.getInt("BandId")) {
                bandTable[i][0] = "-" + rs.getString("Artist");
            } else {
                bandTable[i][0] = rs.getString("Artist");
            }
            bandTable[i][1] = rs.getString("Date");
            bandTable[i][2] = rs.getString("Venue");
            bandTable[i][3] = rs.getString("Rating");
            bandTable[i][4] = rs.getString("ID");
            i++;
        }
        return bandTable;
    }

    public static String[] selectBands(Connection conn, boolean addOrRemove) {
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
        if (addOrRemove) {
            bandData.add("Add New Band");
            bandData.add("Remove Band");
        }
        String[] bandArray = new String[bandData.size()];
        bandArray = bandData.toArray(bandArray);
        return bandArray;
    }

    public static String[] selectVenues(Connection conn, boolean addOrRemove) {
        String sql = "SELECT VenueName, Location FROM Venue WHERE isFestival = 0 ORDER BY VenueName";
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
        if (addOrRemove) {
            venueData.add("Add New Venue");
        }
        String[] venueArray = new String[venueData.size()];
        venueArray = venueData.toArray(venueArray);
        return venueArray;
    }

    public static String[] selectAllVenues(Connection conn) {
        String sql = "SELECT VenueName, Location FROM Venue ORDER BY VenueName";
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
        String[] venueArray = new String[venueData.size()];
        venueArray = venueData.toArray(venueArray);
        return venueArray;
    }

    public static boolean checkExists(Connection conn, String table, String column1, String column2,
                                          String name, String identifier) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT " + column1 + " FROM " + table + " WHERE "
                + column1 + " = ? AND " + column2 + " = ?");
        ps.setString (1, name);
        ps.setString (2, identifier);
        ResultSet rs = ps.executeQuery();
        return !rs.next();
    }

    public static boolean checkIfUsed(Connection conn, String column, int checkId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT Id FROM Gig WHERE "
                + column + " = ?");
        ps.setInt (1, checkId);
        ResultSet rs = ps.executeQuery();
        return rs.getString("Id") != null;
    }

    public static boolean checkFriend(Connection conn, String friendName) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM Friend WHERE FriendName = ?");
        ps.setString (1, friendName);
        ResultSet rs = ps.executeQuery();

        return rs.next();
    }

    public static ArrayList<Band> getGigPerformances(Connection conn, int gigId) throws SQLException {
        ArrayList<Band> performanceList = new ArrayList<>();
        PreparedStatement ps = conn.prepareStatement("SELECT Band.BandName, Band.Genre, Band.Country, Performance.Rating " +
                "FROM Performance JOIN Band ON Band.Id = Performance.Band_Id WHERE Performance.Gig_Id = ?");
        ps.setInt(1, gigId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Band addBand = new Band(rs.getString("BandName"), rs.getString("Genre"),
                    rs.getString("Country"), rs.getInt("Rating"));
            performanceList.add(addBand);
        }
        return performanceList;
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

    public static String[] getGigHeadlineDetails(Connection conn, int bandId, int gigId) throws SQLException {
        String[] bandDetails = new String[4];
        PreparedStatement ps = conn.prepareStatement("SELECT Band.BandName AS Name, Band.Genre As Genre, Band.Country " +
                "AS Country, Performance.Rating AS Rating FROM Band JOIN Performance ON Band.Id = Performance.Band_Id " +
                "WHERE Band.Id = ? and Performance.Gig_Id = ?");
        ps.setInt(1, bandId);
        ps.setInt(2, gigId);
        ResultSet rs = ps.executeQuery();
        bandDetails[0] = rs.getString("Name");
        bandDetails[1] = rs.getString("Genre");
        bandDetails[2] = rs.getString("Country");
        bandDetails[3] = rs.getString("Rating");
        return bandDetails;
    }

    public static String[] getGigFriends(Connection conn, int gigId) throws SQLException {
        ArrayList<String> friendList = new ArrayList<>();
        PreparedStatement ps = conn.prepareStatement("SELECT FriendName FROM Friend JOIN Attended_With " +
                "ON Friend.Id = Attended_With.Friend_Id WHERE Attended_WIth.Gig_Id = ?");
        ps.setInt(1, gigId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            friendList.add(rs.getString("FriendName"));
        }
        String[] friendArray = new String[friendList.size()];
        friendArray = friendList.toArray(friendArray);
        return friendArray;
    }

    public static String getGigFriendString(Connection conn, int gigId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT GROUP_CONCAT(FriendName) AS Name FROM Friend JOIN Attended_With " +
                "ON Friend.Id = Attended_With.Friend_Id WHERE Attended_WIth.Gig_Id = ?");
        ps.setInt(1, gigId);
        ResultSet rs = ps.executeQuery();
        return rs.getString("Name");
    }

    public static String[] getGigDetails(Connection conn, int gigId) throws SQLException {
        String[] gigDetails = new String[5];
        PreparedStatement ps = conn.prepareStatement("SELECT Venue.Id, Venue.VenueName, Venue.Location, Gig.Date, " +
                "Gig.Headline FROM Venue JOIN Gig ON Venue.Id = Gig.Venue_Id WHERE Gig.Id = ?");
        ps.setInt(1, gigId);
        ResultSet rs = ps.executeQuery();
        gigDetails[0] = rs.getString("Id");
        gigDetails[1] = rs.getString("VenueName");
        gigDetails[2] = rs.getString("Location");
        gigDetails[3] = rs.getString("Headline");
        gigDetails[4] = rs.getString("Date");
        return gigDetails;
    }

    public static String[] getFestivalDayDetails(Connection conn, int gigId) throws SQLException {
        String[] gigDetails = new String[3];
        PreparedStatement ps = conn.prepareStatement("SELECT Gig.Id, Gig.Date, Gig.Headline FROM Venue " +
                "JOIN Gig ON Venue.Id = Gig.Venue_Id WHERE Gig.Id = ?");
        ps.setInt(1, gigId);
        ResultSet rs = ps.executeQuery();
        gigDetails[0] = rs.getString("Id");
        gigDetails[1] = rs.getString("Headline");
        gigDetails[2] = rs.getString("Date");
        return gigDetails;
    }

    public static String[] getFestivalDayIds(Connection conn, int festivalId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT Gig.Id AS Id FROM Venue " +
                "JOIN Gig ON Gig.Venue_Id = Venue.Id WHERE Venue.Id = ? ORDER BY Gig.Date ASC");
        ps.setInt(1, festivalId);
        ResultSet rs = ps.executeQuery();
        ArrayList<String> idList = new ArrayList<>();
        while (rs.next()) {
            idList.add(rs.getString("Id"));
        }
        String[] festivalIds = new String[idList.size()];
        festivalIds = idList.toArray(festivalIds);
        return festivalIds;
    }

    public static String getGigDate(Connection conn, int gigId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT Date FROM Gig WHERE Id = ?");
        ps.setInt(1, gigId);
        ResultSet rs = ps.executeQuery();
        return rs.getString("Date");
    }

    public static int getGigId(Connection conn, String bandName, String gigDate) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT Gig.Id as Id FROM Gig JOIN Band ON Gig.Headline = Band.Id " +
                "WHERE Band.BandName = ? AND Gig.Date = ?");
        ps.setString(1, bandName);
        ps.setString(2, gigDate);
        ResultSet rs = ps.executeQuery();
        return rs.getInt("Id");
    }

    public static int getBandId(Connection conn, String bandName, String bandCountry) throws SQLException {
        PreparedStatement ps = conn.prepareStatement
                        ("SELECT Id FROM Band WHERE BandName = ? AND Country = ?");
        ps.setString (1, bandName);
        ps.setString (2, bandCountry);
        ResultSet rs = ps.executeQuery();
        return rs.getInt("Id");
    }

    public static int getVenueId(Connection conn, Venue selectedVenue) throws SQLException {
        String name = selectedVenue.getVenueName();
        String location = selectedVenue.getVenueLocation();
        PreparedStatement ps = conn.prepareStatement
                        ("SELECT Id FROM Venue WHERE VenueName = ? AND Location = ?");
        ps.setString (1, name);
        ps.setString (2, location);
        ResultSet rs = ps.executeQuery();
        return rs.getInt("Id");
    }
    public static int getTableCount(Connection conn, String table) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT Id FROM " + table);
        ResultSet rs = ps.executeQuery();
        int columnCount = 0;
        while (rs.next()) {
            if (rs.getString("Id") != null){
                columnCount++;
            }
        }
        return columnCount;
    }

    public static int getLastId(Connection conn, String table) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT Max(Id) AS tableId FROM " + table);
        ResultSet rs = ps.executeQuery();
        return rs.getInt("tableId");
    }
}
