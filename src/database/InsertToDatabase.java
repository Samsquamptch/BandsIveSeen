package src.database;

import src.eventObjects.Band;
import src.eventObjects.Gig;
import src.eventObjects.Venue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertToDatabase {

    public static void insertGig(Connection conn, Gig addGig) throws SQLException {
        String headline = addGig.getHeadlineAct().getBandName();
        String headlineCountry = addGig.getHeadlineAct().getFromCountry();
        String gigDate = addGig.getEventDay();
        if (ReadFromDatabase.checkGig(conn, headline, gigDate)) {
            return;
        }
        int venueId = ReadFromDatabase.getVenueId(conn, addGig.getLocation());

        int headlineId = ReadFromDatabase.getBandId(conn, headline, headlineCountry);

        PreparedStatement ps = conn.prepareStatement("INSERT INTO Gig(Date, Venue_Id, Headline) VALUES(?,?,?)");
        ps.setString(1, gigDate);
        ps.setInt(2, venueId);
        ps.setInt(3, headlineId);
        ps.executeUpdate();

        int gigID = ReadFromDatabase.getLastId(conn, "Gig");
        for (Band performance : addGig.getPerformances()) {
            insertPerformance(conn, performance, gigID);
        }
        if (!addGig.getWentWith().isEmpty()) {
            for (String friend : addGig.getWentWith()) {
                InsertToDatabase.insertAttendedWith(conn, friend, gigID);
            }
        }
    }

    public static void insertFriend(Connection conn, String friendName) throws SQLException {
        if (!ReadFromDatabase.checkFriend(conn, friendName)) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Friend(FriendName) VALUES(?)");
            ps.setString(1, friendName);
            ps.executeUpdate();
        }
    }

    public static void addBand(Connection conn, String name, String genre, String country) throws SQLException {
        if (ReadFromDatabase.checkExists(conn, "Band", "BandName", "Country", name, country)) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Band(BandName,Genre, Country) VALUES(?,?,?)");
            ps.setString(1, name);
            ps.setString(2, genre);
            ps.setString(3, country);
            ps.executeUpdate();
        }
    }

    public static void insertPerformance(Connection conn, Band addBand, int gigId) throws SQLException {
        String name = addBand.getBandName();
        String country = addBand.getFromCountry();
        int rating = addBand.getRating();
        int bandId = ReadFromDatabase.getBandId(conn, name, country);

        PreparedStatement ps = conn.prepareStatement("INSERT INTO Performance(Gig_Id,Band_Id,Rating) VALUES(?,?,?)");
        ps.setInt(1, gigId);
        ps.setInt(2, bandId);
        ps.setInt(3, rating);
        ps.executeUpdate();

    }

    public static void insertAttendedWith(Connection conn, String friendName, int gigID) throws SQLException {
        int friendId = ReadFromDatabase.getFriendId(conn, friendName);
        PreparedStatement ps = conn.prepareStatement ("INSERT INTO Attended_with(Gig_Id, Friend_Id) VALUES(?,?)");
                ps.setInt(1, gigID);
                ps.setInt(2, friendId);
                ps.executeUpdate();
        }

    public static void insertVenue(Connection conn, Venue addVenue) throws SQLException {
        String name = addVenue.getVenueName();
        String location = addVenue.getVenueLocation();
        boolean isFestival = addVenue.getIsFestival();
        if (ReadFromDatabase.checkExists(conn, "Venue", "VenueName", "Location", name, location)) {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Venue(VenueName, Location, isFestival) VALUES(?,?,?)");
            pstmt.setString(1, name);
            pstmt.setString(2, location);
            pstmt.setBoolean(3, isFestival);
            pstmt.executeUpdate();
        }
    }
}
