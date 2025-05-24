package com.example.calendar.model;

import java.time.ZonedDateTime;

public class Event {
    private String title;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public ZonedDateTime getStartTime() { return startTime; }
    public void setStartTime(ZonedDateTime startTime) { this.startTime = startTime; }

    public ZonedDateTime getEndTime() { return endTime; }
    public void setEndTime(ZonedDateTime endTime) { this.endTime = endTime; }
}
