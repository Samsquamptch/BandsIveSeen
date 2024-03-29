package src.database;

import src.eventObjects.Band;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteFromDatabase {
    public static void deleteGig(Connection conn, int gigId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement ("DELETE FROM Gig WHERE Gig.Id = ?");
        ps.setInt (1, gigId);
        ps.executeUpdate();
    }

    public static void deleteBand(Connection conn, int bandId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement ("DELETE FROM Band WHERE Id = ?");
        ps.setInt(1, bandId);
        ps.executeUpdate();
    }

    public static void deletePerformance(Connection conn, Band performance, int gigId) throws SQLException {
        int bandId = ReadFromDatabase.getBandId(conn, performance.getBandName(), performance.getFromCountry());
        PreparedStatement ps = conn.prepareStatement ("DELETE FROM Performance WHERE Band_Id = ? AND Gig_Id = ?");
        ps.setInt(1, bandId);
        ps.setInt(2, gigId);
        ps.executeUpdate();
    }

    public static void deleteAttendedWith(Connection conn, String friendName, int gigID) throws SQLException {
        int friendId = ReadFromDatabase.getFriendId(conn, friendName);
        PreparedStatement ps = conn.prepareStatement ("DELETE FROM Attended_with WHERE Friend_Id = ? AND Gig_Id = ?");
        ps.setInt(1, friendId);
        ps.setInt(2, gigID);
        ps.executeUpdate();
    }

    public static void deleteVenue(Connection conn, int venueId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement ("DELETE FROM Venue WHERE Id = ?");
        ps.setInt(1, venueId);
        ps.executeUpdate();
    }

    public static void deleteFriend(Connection conn, int friendId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement ("DELETE FROM Friend WHERE Id = ?");
        ps.setInt(1, friendId);
        ps.executeUpdate();
    }
}
