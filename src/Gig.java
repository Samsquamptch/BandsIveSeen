package src;

import java.util.ArrayList;

public class Gig extends Event {
    private Band headlineAct;
    private ArrayList<Band> performances;


    public Gig(String dayDate, Venue location, Band headline){
        super(dayDate, location);
        this.headlineAct = headline;
        this.performances = new ArrayList<Band>();
        this.performances.add(headline);
    }

    public Gig(){
        super();
        this.headlineAct = null;
        this.performances = new ArrayList<Band>();
    }

    public void setHeadlineAct(Band headlineBand) {
        Band oldHeadline = this.headlineAct;
        this.headlineAct = headlineBand;
        if (oldHeadline!=null) {
            changePerformance(oldHeadline, this.headlineAct);
        } else {
            this.performances.add(headlineBand);
        }
    }

    public void changePerformance(Band oldPerformance, Band newPerformance) {
        int index = this.performances.indexOf(oldPerformance);
        this.performances.set(index, newPerformance);
    }


    public void addPerformance(Band supportingBand) {
        if (this.headlineAct != null) {
            this.performances.add(supportingBand);
        }
    }

    public void removePerformance(Band performance) {
        if (this.headlineAct != performance) {
            this.performances.remove(performance);
        }
    }

    public Band getHeadlineAct() {
        return this.headlineAct; }

    public ArrayList<Band> getPerformances() {
        return this.performances;
    }

    public boolean checkIfNull() {
        return super.getEventDay() != null && super.getLocation() != null && this.headlineAct != null;
    }

    @Override
    public String toString(){
            ArrayList<String> supportingString = new ArrayList<String>();
            for (Band support : this.performances) {
                supportingString.add(support.getBandName());
            }
            return "Gig: " + this.headlineAct.getBandName() + " | Date: " + super.getEventDay() + " | Venue: "
                    + super.getLocation() + " | Performances: " + String.join(", ", supportingString)
                    + " | Went with: " + String.join(", ", super.getWentWith());
    }
}
