package src;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event {
    final private LocalDate eventDay;
    final private Venue location;

    public Event(String dayDate, Venue location){
        this.eventDay = LocalDate.parse(dayDate);
        this.location = location;
    }

    public String getEventDay() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return this.eventDay.format(formatter);
    }

    public Venue getLocation() {
        return this.location;
    }
}
