package src;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event {
    private LocalDate eventDay;
    private Venue location;

    public Event(String dayDate, Venue location){
        this.eventDay = LocalDate.parse(dayDate);
        this.location = location;
    }

    public String getEventDay() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return this.eventDay.format(formatter);
    }

    public Venue getLocation() {
        return this.location;
    }
}
