package src;

public class Band {
    private String bandName;
    private String bandGenre;
    private String fromCountry;
    private int rating;

    public Band(String name, String genre, String country, int rating) {
        this.bandName = name;
        this.bandGenre = genre;
        this.fromCountry = country;
        this.rating = rating;
    }

    public void setRating(int value) { this.rating = value; }
    public String getBandName() {
        return this.bandName;
    }

    public String getBandGenre() { return this.bandGenre; }

    public String getFromCountry() { return this.fromCountry; }

    public int getRating() { return this.rating; }

    public String getStringRating(){
        return this.rating + "/10";
    }
}
