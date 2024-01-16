package src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class WriteDatabase {

    public static void insertGig(Gig addGig) throws SQLException {
        String headline = addGig.getHeadlineAct().getBandName();
        String headlineCountry = addGig.getHeadlineAct().getFromCountry();
        String gigDate = addGig.getEventDay();
        ArrayList<Integer> performanceIds = new ArrayList<Integer>();
        ArrayList<Integer> friendIds = new ArrayList<Integer>();
        if (ReadDatabase.checkGig(headline, gigDate)) {
            return;
        }
        int venueId = insertVenue(addGig.getLocation());
        int headlineId = ReadDatabase.getBandId(headline, headlineCountry);

        int perfId = insertBand(addGig.getHeadlineAct());
        performanceIds.add(perfId);

//        for (Band performance : addGig.getPerformances()) {
//            int perfId = insertBand(performance);
//            performanceIds.add(perfId);
//        }
//        if (!addGig.getWentWith().isEmpty()) {
//            for (String friend : addGig.getWentWith()) {
//                int friendId = insertFriend(friend);
//            }
//        }

        System.out.println("test3");
        String sql = "INSERT INTO Gig(Date, Venue_Id, Headline) VALUES(?,?,?)";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, gigDate);
            pstmt.setInt(2, venueId);
            pstmt.setInt(3, headlineId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("test4");
        int gigID = ReadDatabase.getLastId("Gig");
        WriteDatabase.matchGigPerformances(gigID, performanceIds);
        if (!addGig.getWentWith().isEmpty()) {
            WriteDatabase.matchGigFriends(gigID, friendIds);
        }
    }

    public static int insertFriend(String friendName) throws SQLException {
        if (!ReadDatabase.checkFriend(friendName)) {
            String sql = "INSERT INTO Friend(FriendName) VALUES(?)";
            try (Connection conn = DatabaseConnector.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, friendName);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return ReadDatabase.getFriendId(friendName);
    }

    public static int insertBand(Band addBand) throws SQLException {
        String name = addBand.getBandName();
        String genre = addBand.getBandGenre();
        String country = addBand.getFromCountry();
        int performanceRating = addBand.getRating();
        System.out.println("Check1");
        if (!ReadDatabase.checkExists("Band", "BandName", "Country", name, country)) {
            String sql = "INSERT INTO Band(BandName,Genre, Country) VALUES(?,?,?)";
            System.out.println("Check2");
            try (Connection conn = DatabaseConnector.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setString(2, genre);
                pstmt.setString(3, country);
                pstmt.executeUpdate();
                System.out.println("Check3");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Check4");
        return insertPerformance(name, country, performanceRating);
    }

    public static int insertPerformance(String name, String country, int rating) throws SQLException {
        int bandId = ReadDatabase.getBandId(name, country);

        System.out.println("test2");
        String sql = "INSERT INTO Performance(Band_Id,Rating) VALUES(?,?)";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bandId);
            pstmt.setInt(2, rating);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ReadDatabase.getLastId("Performance");
    }

    public static void matchGigFriends(int gigID, ArrayList<Integer> friendIds) {
        for (int friend : friendIds) {
            String sql = "INSERT INTO Attended_with(Gig_Id,Friend_Id) VALUES(?,?) r";

            try (Connection conn = DatabaseConnector.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, gigID);
                pstmt.setInt(2, friend);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void matchGigPerformances(int gigID, ArrayList<Integer> performanceIds) {
        for (int performance : performanceIds) {
            String sql = "INSERT INTO gig_performance(Gig_Id,Performance_Id) VALUES(?,?) r";

            try (Connection conn = DatabaseConnector.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, gigID);
                pstmt.setInt(2, performance);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static int insertVenue(Venue addVenue) throws SQLException {
        String name = addVenue.getVenueName();
        String location = addVenue.getVenueLocation();
        boolean isFestival = addVenue.getIsFestival();
        if (!ReadDatabase.checkExists("Venue", "VenueName", "Location", name, location)) {
            String sql = "INSERT INTO Venue(VenueName, Location, isFestival) VALUES(?,?,?)";

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
        return ReadDatabase.getVenueId(name, location);
    }
}
