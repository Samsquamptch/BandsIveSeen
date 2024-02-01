package src;

public class Band {
    final private String bandName;
    final private String bandGenre;
    final private String fromCountry;
    private int rating;

    public Band(String name, String genre, String country, int rating) {
        this.bandName = name;
        this.bandGenre = genre;
        this.fromCountry = country;
        this.rating = rating;
    }

    @Override
    public String toString() {return this.bandName + " - " + this.bandGenre + " - "
    + this.fromCountry + " - " + this.rating + "/10";}
    public void setRating(int value) { this.rating = value; }
    public String getBandName() {
        return this.bandName;
    }

    public String getBandGenre() { return this.bandGenre; }

    public String getFromCountry() { return this.fromCountry; }

    public int getRating() { return this.rating; }
}
