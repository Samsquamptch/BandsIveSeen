package src;

public class Venue {
    private String venueName;
    private String venueLocation;
    private boolean isFestival;

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

    public String checkIfFestival() {
        if(this.isFestival == true){
            return "This is a festival location";
        }
        else {
            return "This is not a festival location";
        }
    }
}
