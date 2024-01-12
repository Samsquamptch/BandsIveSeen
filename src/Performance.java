package src;

public class Performance {
    private Band act;
    private int rating;

    public Performance(Band act, int rating){
        this.act = act;
        this.rating = rating;
    }

    public String getBandName(){
        return this.act.getBandName();
    }

    public String getRating(){
        return this.rating + "/10";
    }
}
