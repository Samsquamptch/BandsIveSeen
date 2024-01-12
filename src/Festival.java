package src;

import java.time.LocalDate;
import java.util.ArrayList;

public class Festival extends Event {
    private String festivalName;
    private int daysDuration;
    private ArrayList<DaysOfFestival> festivalDays;

    public Festival(String arrivalDate, Venue location, String festivalName, int numberOfDays){
        super(arrivalDate, location);
        this.festivalName = festivalName;
        this.daysDuration = numberOfDays;
        this.festivalDays = new ArrayList<DaysOfFestival>();
    }

/*    public void createFestivalDays(){
        if (festivalDays.isEmpty()){
            for(int i = 0; i<=this.daysDuration; i++) {
            }
        }
    }*/
}
