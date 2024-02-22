package src;

import java.time.LocalDate;
import java.util.ArrayList;

public class Festival extends Event {
    private String festivalName;
    private ArrayList<DaysOfFestival> festivalDays;

    public Festival() {
        super();
        this.festivalName = "";
        this.festivalDays = new ArrayList<>();
    }

    public Festival(String arrivalDate, Venue location, String festivalName) {
        super(arrivalDate, location);
        this.festivalName = festivalName + super.getEventYear();
        this.festivalDays = new ArrayList<>();
    }



    public void setFestivalName(String newName) { this.festivalName = newName; }

    public String getFestivalName() { return this.festivalName; }

    public void addDays() {
        int dayCount = this.festivalDays.size();
        this.festivalDays.add(new DaysOfFestival(super.getLocalDate(), dayCount));
    }

    public void removeDays() {
        int dayCount = this.festivalDays.size();
        this.festivalDays.remove(dayCount-1);
    }

    public int getNumberOfDays() {
        return this.festivalDays.size();
    }

    public ArrayList<DaysOfFestival> getFestivalDays() {
        return this.festivalDays;
    }

    public void updateDaysDate() {
        LocalDate festivalDate = super.getLocalDate();
        for (DaysOfFestival day : festivalDays) {
            day.setDayDate(festivalDate);
        }
    }

}
