package src;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event {
    private LocalDate eventDay;
    private Venue location;

    public Event(String dayDate, Venue location) {
        this.eventDay = LocalDate.parse(dayDate);
        this.location = location;
    }

    public Event() {
        this.eventDay = null;
        this.location = null;
    }

    public void setEventDay(String dayDate) {
        this.eventDay = LocalDate.parse(dayDate);
    }

    public void setLocation(Venue location) {
        this.location = location;
    }

    public String getEventDay() {
        if (this.eventDay == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return this.eventDay.format(formatter);
    }

    public String getEventYear() {
        if (this.eventDay == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        return this.eventDay.format(formatter);
    }

    public LocalDate getLocalDate() {
        return this.eventDay;
    }

    public Venue getLocation() {
        return this.location;
    }
}
