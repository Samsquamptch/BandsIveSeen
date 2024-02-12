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
        PreparedStatement ps = conn.prepareStatement ("DELETE Gig, Performance, Attended_with FROM Gig " +
                "JOIN Performance ON (Gig.Id = Performance.Gig_Id) JOIN Attended_with ON (Gig.Id = Attended_with.Gig_Id)" +
                " WHERE Gig.Id = ?");
        ps.setInt (1, gigId);
        ps.executeUpdate();
    }
}
