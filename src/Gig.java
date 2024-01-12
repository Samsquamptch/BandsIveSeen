package src;

import java.util.ArrayList;

public class Gig extends Event {
    private Performance headlineAct;
    private ArrayList<Performance> supportingActs;
    private ArrayList<String> wentWith;

    public Gig(String dayDate, Venue location, Performance headline){
        super(dayDate, location);
        this.headlineAct = headline;
        this.supportingActs = new ArrayList<Performance>();
        this.wentWith = new ArrayList<String>();
    }

    public void addSupportingAct(Performance supportingBand) {
        this.supportingActs.add(supportingBand);
    }

    public void addWentWith(String name) {
        this.wentWith.add(name);
    }

    @Override
    public String toString(){
        if(this.supportingActs.isEmpty()) {
            return "Headliner: " + this.headlineAct.getBandName() + " | Date: " + super.getEventDay() + " | Venue: "
                    + super.getLocation() + " | Supporting: None | Went with: " + String.join(", ", this.wentWith);
        }
        else {
            ArrayList<String> supportingString = new ArrayList<String>();
            for (Performance support : this.supportingActs) {
                supportingString.add(support.getBandName());
            }
            return "Headliner: " + this.headlineAct.getBandName() + " | Date: " + super.getEventDay() + " | Venue: "
                    + super.getLocation() + " | Supporting: " + String.join(", ", supportingString)
                    + " | Went with: " + String.join(", ", wentWith);
        }
    }
}
