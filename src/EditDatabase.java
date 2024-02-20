package src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditDatabase {

    public static void setPragma(Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("PRAGMA foreign_keys = ON");
        ps.executeUpdate();
    }
    public static void deleteGig(Connection conn, int gigId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement ("DELETE FROM Gig WHERE Gig.Id = ?");
        ps.setInt (1, gigId);
        ps.executeUpdate();
    }

    public static void changeGigHeadline(Connection conn, int gigId, Band newHeadline) throws SQLException {
        int newHeadlineId = ReadFromDatabase.getBandId(conn, newHeadline.getBandName(), newHeadline.getFromCountry());
        PreparedStatement ps = conn.prepareStatement ("UPDATE Gig SET Headline = ? WHERE Id = ?");
        ps.setInt(1, newHeadlineId);
        ps.setInt(2, gigId);
        ps.executeUpdate();
    }

    public static void changePerformanceBand(Connection conn, int gigId, Band newBand, Band oldBand) throws SQLException {
        int newBandId = ReadFromDatabase.getBandId(conn, newBand.getBandName(), newBand.getFromCountry());
        int oldBandId = ReadFromDatabase.getBandId(conn, oldBand.getBandName(), oldBand.getFromCountry());
        PreparedStatement ps = conn.prepareStatement ("UPDATE Performance SET Band_Id = ?" +
                "WHERE Gig_Id = ? AND Band_Id = ?");
        ps.setInt(1, newBandId);
        ps.setInt(2, gigId);
        ps.setInt(3, oldBandId);
        ps.executeUpdate();
    }
}
