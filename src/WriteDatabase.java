package src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WriteDatabase {

    public static void insertBand(Band addBand) throws SQLException {
        String name = addBand.getBandName();
        String genre = addBand.getBandGenre();
        String country = addBand.getFromCountry();
        if (ReadDatabase.checkExists("Band", "Name", "Country", name, country))
            return;
        String sql = "INSERT INTO Band(Name,Genre, Country) VALUES(?,?,?)";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, genre);
            pstmt.setString(3, country);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertVenue(Venue addVenue) throws SQLException {
        String name = addVenue.getVenueName();
        String location = addVenue.getVenueLocation();
        boolean isFestival = addVenue.getIsFestival();
        if (ReadDatabase.checkExists("Venue", "Name", "Location", name, location))
            return;
        String sql = "INSERT INTO Venue(Name, Location, isFestival) VALUES(?,?,?)";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, location);
            pstmt.setBoolean(3, isFestival);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
