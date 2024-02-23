package src;

import java.time.LocalDate;

public class FestivalDay extends Gig {
    private final int dayNumber;

    public FestivalDay(LocalDate startDate, int dayCount) {
        super();
        this.setEventDay(startDate.plusDays(dayCount));
        this.dayNumber = dayCount + 1;
    }

    public void removePerformance(int index) {
            super.getPerformances().remove(index);
    }

    public String getDay() {
        return "Day " + this.dayNumber;
    }
}
