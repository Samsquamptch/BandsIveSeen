package src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class InsertToDatabase {

    public static void insertGig(Connection conn, Gig addGig) throws SQLException {
        String headline = addGig.getHeadlineAct().getBandName();
        String headlineCountry = addGig.getHeadlineAct().getFromCountry();
        String gigDate = addGig.getEventDay();
        ArrayList<Integer> performanceIds = new ArrayList<>();
        ArrayList<Integer> friendIds = new ArrayList<>();
        if (ReadFromDatabase.checkGig(conn, headline, gigDate)) {
            return;
        }
        int venueId = insertVenue(conn, addGig.getLocation());


        for (Band performance : addGig.getPerformances()) {
            int perfId = insertPerformance(conn, performance);
            performanceIds.add(perfId);
        }
        if (!addGig.getWentWith().isEmpty()) {
            for (String friend : addGig.getWentWith()) {
                int friendId = insertFriend(conn, friend);
                friendIds.add(friendId);
            }
        }

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
        InsertToDatabase.matchGigPerformances(conn, gigID, performanceIds);
        if (!addGig.getWentWith().isEmpty()) {
            InsertToDatabase.matchGigFriends(conn, gigID, friendIds);
        }
    }

    public static int insertFriend(Connection conn, String friendName) throws SQLException {
        if (!ReadFromDatabase.checkFriend(conn, friendName)) {
            String sql = "INSERT INTO Friend(FriendName) VALUES(?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, friendName);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return ReadFromDatabase.getFriendId(conn, friendName);
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

    public static int insertPerformance(Connection conn, Band addBand) throws SQLException {
        String name = addBand.getBandName();
        String country = addBand.getFromCountry();
        int rating = addBand.getRating();

        int bandId = ReadFromDatabase.getBandId(conn, name, country);

        String sql = "INSERT INTO Performance(Band_Id,Rating) VALUES(?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bandId);
            pstmt.setInt(2, rating);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ReadFromDatabase.getLastId(conn, "Performance");
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
        return ReadFromDatabase.getVenueId(conn, name, location);
    }
}
