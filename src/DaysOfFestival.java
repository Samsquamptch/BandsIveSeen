package src;

import java.time.LocalDate;
import java.util.ArrayList;

public class DaysOfFestival {
    private ArrayList<Band> dayPerformances;
    private final int dayNumber;
    private LocalDate dayDate;
    private Band headline;

    public DaysOfFestival(LocalDate startDate, int dayCount) {
        this.dayDate = startDate.plusDays(dayCount);
        this.dayNumber = dayCount + 1;
        this.headline = new Band();
        this.dayPerformances = new ArrayList<>();
    }

    private void addPerformance(Band performance) {
        this.dayPerformances.add(performance);
    }


}
