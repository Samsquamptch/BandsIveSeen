package src;

public class Band {
    private String bandName;
    private String bandGenre;
    private String fromCountry;

    public Band(String name, String genre, String country) {
        this.bandName = name;
        this.bandGenre = genre;
        this.fromCountry = country;
    }


    public String getBandName() {
        return bandName;
    }
}
