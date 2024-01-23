package src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class WriteDatabase {

    public static void insertGig(Connection conn, Gig addGig) throws SQLException {
        String headline = addGig.getHeadlineAct().getBandName();
        String headlineCountry = addGig.getHeadlineAct().getFromCountry();
        String gigDate = addGig.getEventDay();
        ArrayList<Integer> performanceIds = new ArrayList<>();
        ArrayList<Integer> friendIds = new ArrayList<>();
        if (ReadDatabase.checkGig(conn, headline, gigDate)) {
            return;
        }
        int venueId = insertVenue(conn, addGig.getLocation());


        for (Band performance : addGig.getPerformances()) {
            int perfId = insertBand(conn, performance);
            performanceIds.add(perfId);
        }
        if (!addGig.getWentWith().isEmpty()) {
            for (String friend : addGig.getWentWith()) {
                int friendId = insertFriend(conn, friend);
                friendIds.add(friendId);
            }
        }

        int headlineId = ReadDatabase.getBandId(conn, headline, headlineCountry);
        System.out.println(headlineId);

        String sql = "INSERT INTO Gig(Date, Venue_Id, Headline) VALUES(?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, gigDate);
            pstmt.setInt(2, venueId);
            pstmt.setInt(3, headlineId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        int gigID = ReadDatabase.getLastId(conn, "Gig");
        WriteDatabase.matchGigPerformances(conn, gigID, performanceIds);
        if (!addGig.getWentWith().isEmpty()) {
            WriteDatabase.matchGigFriends(conn, gigID, friendIds);
        }
    }

    public static int insertFriend(Connection conn, String friendName) throws SQLException {
        if (!ReadDatabase.checkFriend(conn, friendName)) {
            String sql = "INSERT INTO Friend(FriendName) VALUES(?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, friendName);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return ReadDatabase.getFriendId(conn, friendName);
    }

    public static int insertBand(Connection conn, Band addBand) throws SQLException {
        String name = addBand.getBandName();
        String genre = addBand.getBandGenre();
        String country = addBand.getFromCountry();
        int performanceRating = addBand.getRating();
        if (ReadDatabase.checkExists(conn, "Band", "BandName", "Country", name, country)) {
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
        return insertPerformance(conn, name, country, performanceRating);
    }

    public static int insertPerformance(Connection conn, String name, String country, int rating) throws SQLException {
        int bandId = ReadDatabase.getBandId(conn, name, country);

        String sql = "INSERT INTO Performance(Band_Id,Rating) VALUES(?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bandId);
            pstmt.setInt(2, rating);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ReadDatabase.getLastId(conn, "Performance");
    }

    public static void matchGigFriends(Connection conn, int gigID, ArrayList<Integer> friendIds) {
        for (int friend : friendIds) {
            String sql = "INSERT INTO Attended_with(Gig_Id,Friend_Id) VALUES(?,?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, gigID);
                pstmt.setInt(2, friend);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void matchGigPerformances(Connection conn, int gigID, ArrayList<Integer> performanceIds) {
        for (int performance : performanceIds) {
            String sql = "UPDATE Performance SET Gig_Id = ? WHERE Id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, gigID);
                pstmt.setInt(2, performance);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static int insertVenue(Connection conn, Venue addVenue) throws SQLException {
        String name = addVenue.getVenueName();
        String location = addVenue.getVenueLocation();
        boolean isFestival = addVenue.getIsFestival();
        if (ReadDatabase.checkExists(conn, "Venue", "VenueName", "Location", name, location)) {
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
        return ReadDatabase.getVenueId(conn, name, location);
    }
}
