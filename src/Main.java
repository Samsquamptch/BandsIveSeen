package src;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Band bandObj1 = new Band("Orange Goblin", "Stoner Metal", "England", 9);
        Band bandObj2 = new Band("Kyary Pamyu Pamyu", "JPop", "Japan", 9);
        Venue venueObj1 = new Venue("The Underworld", "Camden, London", false);
        Venue venueObj2 = new Venue("Electric Ballroom", "Camden, London", false);
        WriteDatabase.insertBand(bandObj1);
        WriteDatabase.insertBand(bandObj2);
        WriteDatabase.insertBand(bandObj1);
        WriteDatabase.insertVenue(venueObj1);
        WriteDatabase.insertVenue(venueObj2);
        ReadDatabase.selectBands();
        ReadDatabase.selectVenues();
    }
}
