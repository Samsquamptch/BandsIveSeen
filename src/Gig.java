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

    public Gig(){
        super();
        this.headlineAct = null;
        this.performances = new ArrayList<Band>();
        this.wentWith = new ArrayList<String>();
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

    public void addWentWith(String name) {
        this.wentWith.add(name);
    }

    public void removePerformance(Band performance) {
        if (this.headlineAct != performance) {
            this.performances.remove(performance);
        }
    }

    public void removeWentWith(String name) {
        this.wentWith.remove(name);
    }

    public Band getHeadlineAct() {
        return this.headlineAct; }

    public ArrayList<Band> getPerformances() {
        return this.performances;
    }

    public String getFriendsString() {
        if (this.wentWith.isEmpty()) {
            return "";
        }
        StringBuilder friendsString = new StringBuilder();
        for (String friend : this.wentWith) {
            friendsString.append(friend + ", ");
        }
        friendsString.delete(friendsString.length() - 2, friendsString.length() - 1);
        return friendsString.toString();
    }

    public ArrayList<String> getWentWith() {
        return this.wentWith;
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
                    + " | Went with: " + String.join(", ", wentWith);
    }
}
