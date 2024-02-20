package src;

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

        String sql = "INSERT INTO Gig(Date, Venue_Id, Headline) VALUES(?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, gigDate);
            pstmt.setInt(2, venueId);
            pstmt.setInt(3, headlineId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        int gigID = ReadFromDatabase.getLastId(conn, "Gig");
        for (Band performance : addGig.getPerformances()) {
            insertPerformance(conn, performance, gigID);
        }
        if (!addGig.getWentWith().isEmpty()) {
            for (String friend : addGig.getWentWith()) {
                InsertToDatabase.matchGigFriends(conn, gigID, friend);
            }
        }
    }

    public static void insertFriend(Connection conn, String friendName) throws SQLException {
        if (!ReadFromDatabase.checkFriend(conn, friendName)) {
            String sql = "INSERT INTO Friend(FriendName) VALUES(?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, friendName);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void addBand(Connection conn, String name, String genre, String country) throws SQLException {
        if (ReadFromDatabase.checkExists(conn, "Band", "BandName", "Country", name, country)) {
            String sql = "INSERT INTO Band(BandName,Genre, Country) VALUES(?,?,?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setString(2, genre);
                pstmt.setString(3, country);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void insertPerformance(Connection conn, Band addBand, int gigId) throws SQLException {
        String name = addBand.getBandName();
        String country = addBand.getFromCountry();
        int rating = addBand.getRating();
        int bandId = ReadFromDatabase.getBandId(conn, name, country);

        String sql = "INSERT INTO Performance(Gig_Id,Band_Id,Rating) VALUES(?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, gigId);
            pstmt.setInt(2, bandId);
            pstmt.setInt(3, rating);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void matchGigFriends(Connection conn, int gigID, String friendName) throws SQLException {
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
            String sql = "INSERT INTO Venue(VenueName, Location, isFestival) VALUES(?,?,?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setString(2, location);
                pstmt.setBoolean(3, isFestival);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
