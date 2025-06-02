package com.example.calendar.model;

import java.util.List;

public class CalendarRequest {
    private WorkingHours workingHours;
    private String timeZone;
    private List<Event> events;

    public CalendarRequest() {
    }

    public CalendarRequest(WorkingHours workingHours, String timeZone, List<Event> events) {
        this.workingHours = workingHours;
        this.timeZone = timeZone;
        this.events = events;
    }
    
    public WorkingHours getWorkingHours() { return workingHours; }
    public void setWorkingHours(WorkingHours workingHours) { this.workingHours = workingHours; }

    public String getTimeZone() { return timeZone; }
    public void setTimeZone(String timeZone) { this.timeZone = timeZone; }

    public List<Event> getEvents() { return events; }
    public void setEvents(List<Event> events) { this.events = events; }
}
