package src;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DaysOfFestival {
    private ArrayList<Band> dayPerformances;
    private final int dayNumber;
    private LocalDate dayDate;
    private Band headlineAct;

    public DaysOfFestival(LocalDate startDate, int dayCount) {
        this.dayDate = startDate.plusDays(dayCount);
        this.dayNumber = dayCount + 1;
        this.headlineAct = null;
        this.dayPerformances = new ArrayList<>();
    }

    public void setHeadlineAct(Band headlineBand) {
        Band oldHeadline = this.headlineAct;
        this.headlineAct = headlineBand;
        if (oldHeadline!=null) {
            changePerformance(oldHeadline, this.headlineAct);
        } else {
            this.dayPerformances.add(headlineBand);
        }
    }

    public Band getHeadlineAct() {
        return this.headlineAct; }

    public void changePerformance(Band oldPerformance, Band newPerformance) {
        int index = this.dayPerformances.indexOf(oldPerformance);
        this.dayPerformances.set(index, newPerformance);
    }

    public void addPerformance(Band supportingBand) {
        if (this.headlineAct != null) {
            this.dayPerformances.add(supportingBand);
        }
    }

    public void removePerformance(Band performance) {
        if (this.headlineAct != performance) {
            this.dayPerformances.remove(performance);
        }
    }

    public ArrayList<Band> getPerformances() {
        return this.dayPerformances;
    }

    public String getDayDate() {
        if (this.dayDate == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return this.dayDate.format(formatter);
    }

    public void setDayDate(LocalDate newDate) {
        this.dayDate = newDate.plusDays(dayNumber-1);
    }

    public String getDay() {
        return "Day " + this.dayNumber;
    }
}
