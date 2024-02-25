package src.database;


import java.sql.*;

public class DatabaseConnector {

    public static Connection connect() {
        String url = "jdbc:sqlite:bisDatabase.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void buildDb(Connection conn) throws SQLException {
        String sqlQuery = "BEGIN TRANSACTION; CREATE TABLE Gig (Id INTEGER, Date TEXT, Venue_Id INTEGER NOT NULL, " +
                "Headline INTEGER NOT NULL, FOREIGN KEY(Venue_Id) REFERENCES Venue(Id), FOREIGN KEY(Headline) " +
                "REFERENCES Band(Id), PRIMARY KEY(Id AUTOINCREMENT)); CREATE TABLE Performance (Id INTEGER, " +
                "Gig_Id INTEGER, Band_Id INTEGER, Rating INTEGER, FOREIGN KEY(Band_Id) REFERENCES Band(Id) " +
                "ON DELETE CASCADE, FOREIGN KEY(Gig_Id) REFERENCES Gig(Id) ON DELETE CASCADE, PRIMARY KEY(Id AUTOINCREMENT)); " +
                "CREATE TABLE Venue (Id INTEGER, VenueName TEXT, Location TEXT, isFestival INTEGER, PRIMARY KEY(Id AUTOINCREMENT)); " +
                "CREATE TABLE Band (Id INTEGER, BandName TEXT, Genre TEXT, Country TEXT, PRIMARY KEY(Id AUTOINCREMENT)); " +
                "CREATE TABLE Friend (Id INTEGER, FriendName INTEGER, PRIMARY KEY(Id AUTOINCREMENT)); CREATE TABLE " +
                "Attended_with (Gig_Id INTEGER, Friend_Id INTEGER, FOREIGN KEY(Friend_Id) REFERENCES Friend(Id) " +
                "ON DELETE CASCADE, FOREIGN KEY(Gig_Id) REFERENCES Gig(Id) ON DELETE CASCADE); COMMIT";
        System.out.println(sqlQuery);
        Statement Stmt = conn.createStatement();
        Stmt.executeUpdate(sqlQuery);
    }

}