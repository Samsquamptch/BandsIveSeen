package src.eventObjects;

public class Venue {
    private final String venueName;
    private final String venueLocation;
    private final boolean isFestival;

    public Venue() {
        this.venueName = "";
        this.venueLocation = "";
        this.isFestival = false;
    }

    public Venue(String name, String location, boolean festival){
        this.venueName = name;
        this.venueLocation = location;
        this.isFestival = festival;
    }

    @Override
    public String toString(){
        return this.venueName + " - " + this.venueLocation;
    }

    public String getVenueName() {
        return this.venueName;
    }

    public String getVenueLocation() {
        return this.venueLocation;
    }

    public boolean getIsFestival() {
        return this.isFestival;
    }

}
