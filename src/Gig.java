package src;

import java.util.ArrayList;

public class Gig extends Event {
    private Band headlineAct;
    private ArrayList<Band> performances;
    private ArrayList<String> wentWith;

    public Gig(String dayDate, Venue location, Band headline){
        super(dayDate, location);
        this.headlineAct = headline;
        this.performances = new ArrayList<Band>();
        this.performances.add(headline);
        this.wentWith = new ArrayList<String>();
    }

    public void addPerformance(Band supportingBand) {
        this.performances.add(supportingBand);
    }

    public void addWentWith(String name) {
        this.wentWith.add(name);
    }

    public Band getHeadlineAct() { return this.headlineAct; }

    public ArrayList<Band> getPerformances() {
        return this.performances;
    }

    public ArrayList<String> getWentWith() {
        return this.wentWith;
    }

    @Override
    public String toString(){
            ArrayList<String> supportingString = new ArrayList<String>();
            for (Band support : this.performances) {
                supportingString.add(support.getBandName());
            }
            return "Gig: " + this.headlineAct.getBandName() + " | Date: " + super.getEventDay() + " | Venue: "
                    + super.getLocation() + " | Performances: " + String.join(", ", supportingString)
                    + " | Went with: " + String.join(", ", wentWith);
    }
}
