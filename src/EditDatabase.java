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
}
