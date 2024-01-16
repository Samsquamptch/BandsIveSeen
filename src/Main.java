package src;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Band bandObj1 = new Band("Orange Goblin", "Stoner Metal", "England", 9);
        Band bandObj2 = new Band("Urne", "Heavy Metal", "England", 7);
        Band bandObj3 = new Band("King Creature", "Heavy Metal", "England", 7);
        Band bandObj4 = new Band("Kyary Pamyu Pamyu", "JPop", "Japan", 9);
        Band bandObj5 = new Band("Death Grips", "Industrial Hip Hop", "USA", 8);
        Venue venueObj1 = new Venue("The Underworld", "Camden, London", false);
        Venue venueObj2 = new Venue("Electric Ballroom", "Camden, London", false);
        Venue venueObj3 = new Venue("EartH", "Hackney, London", false);
        Venue venueObj4 = new Venue("Manchester Academy", "Manchester", false);
        // WriteDatabase.insertVenue(venueObj3);
        // WriteDatabase.insertBand(bandObj5);
        // System.out.println(WriteDatabase.insertPerformance("Kyary Pamyu Pamyu", "Japan", 9));
        Gig gigObj1 = new Gig("2021-12-17", venueObj1, bandObj1);
//        gigObj1.addPerformance(bandObj2);
//        gigObj1.addPerformance(bandObj3);
//        gigObj1.addWentWith("Jack");
        Gig gigObj2 = new Gig("2016-10-16", venueObj4, bandObj5);
        WriteDatabase.insertGig(gigObj2);
        ReadDatabase.selectBands();
        ReadDatabase.selectVenues();
    }
}
