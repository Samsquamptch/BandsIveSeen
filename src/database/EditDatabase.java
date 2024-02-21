package src.database;

import src.Band;
import src.Venue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditDatabase {

    public static void setPragma(Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("PRAGMA foreign_keys = ON");
        ps.executeUpdate();
    }

    public static void changeGigVenue(Connection conn, Venue newVenue, int gigId) throws SQLException {
        int newVenueId = ReadFromDatabase.getVenueId(conn, newVenue);
        PreparedStatement ps = conn.prepareStatement ("UPDATE Gig SET Venue_Id = ? WHERE Id = ?");
        ps.setInt(1, newVenueId);
        ps.setInt(2, gigId);
        ps.executeUpdate();
    }

    public static void editBand(Connection conn, String name, String genre, String country, int bandId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement ("UPDATE Band SET BandName = ?, Genre = ?, Country = ? WHERE Id = ?");
        ps.setString(1, name);
        ps.setString(2, genre);
        ps.setString(3, country);
        ps.setInt(4, bandId);
        ps.executeUpdate();
    }

    public static void editFriend(Connection conn, String newName, int friendId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement ("UPDATE Friend SET FriendName = ? WHERE Id = ?");
        ps.setString(1, newName);
        ps.setInt(2, friendId);
        ps.executeUpdate();
    }

    public static void changeGigDate(Connection conn, String newDate, int gigId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement ("UPDATE Gig SET Date = ? WHERE Id = ?");
        ps.setString(1, newDate);
        ps.setInt(2, gigId);
        ps.executeUpdate();
    }

    public static void changeGigHeadline(Connection conn, Band newHeadline, int gigId) throws SQLException {
        int newHeadlineId = ReadFromDatabase.getBandId(conn, newHeadline.getBandName(), newHeadline.getFromCountry());
        PreparedStatement ps = conn.prepareStatement ("UPDATE Gig SET Headline = ? WHERE Id = ?");
        ps.setInt(1, newHeadlineId);
        ps.setInt(2, gigId);
        ps.executeUpdate();
    }

    public static void changeWentWith(Connection conn, String newFriend, String oldFriend, int gigId) throws SQLException {
        int newFriendId = ReadFromDatabase.getFriendId(conn, newFriend);
        int oldFriendId = ReadFromDatabase.getFriendId(conn, oldFriend);
        PreparedStatement ps = conn.prepareStatement ("UPDATE Attended_with SET Friend_Id = ? " +
                 "WHERE Friend_Id = ? AND Gig_Id = ?");
        ps.setInt(1, newFriendId);
        ps.setInt(2, oldFriendId);
        ps.setInt(3, gigId);
        ps.executeUpdate();
    }

    public static void changePerformanceBand(Connection conn, Band newBand, Band oldBand, int gigId) throws SQLException {
        int newBandId = ReadFromDatabase.getBandId(conn, newBand.getBandName(), newBand.getFromCountry());
        int oldBandId = ReadFromDatabase.getBandId(conn, oldBand.getBandName(), oldBand.getFromCountry());
        PreparedStatement ps = conn.prepareStatement ("UPDATE Performance SET Band_Id = ?, Rating = ? " +
                "WHERE Band_Id = ? AND Gig_Id = ?");
        ps.setInt(1, newBandId);
        ps.setInt(2, newBand.getRating());
        ps.setInt(3, oldBandId);
        ps.setInt(4, gigId);
        ps.executeUpdate();
    }
}
