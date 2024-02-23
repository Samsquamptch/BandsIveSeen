package src;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Event {
    private LocalDate eventDay;
    private Venue location;
    private ArrayList<String> wentWith;

    public Event(String dayDate, Venue location) {
        this.eventDay = LocalDate.parse(dayDate);
        this.location = location;
        this.wentWith = new ArrayList<String>();
    }

    public Event() {
        this.eventDay = null;
        this.location = null;
        this.wentWith = new ArrayList<String>();
    }

    public void setLocation(Venue location) {
        this.location = location;
    }

    public Venue getLocation() {
        return this.location;
    }

    public void setEventDay(String dayDate) {
        this.eventDay = LocalDate.parse(dayDate);
    }

    public void setEventDay(LocalDate dayDate) {
        this.eventDay = dayDate;
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

    public void addWentWith(String name) {
        this.wentWith.add(name);
    }

    public void removeWentWith(String name) {
        this.wentWith.remove(name);
    }

    public ArrayList<String> getWentWith() {
        return this.wentWith;
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

    public LocalDate getLocalDate() {
        return this.eventDay;
    }
}
