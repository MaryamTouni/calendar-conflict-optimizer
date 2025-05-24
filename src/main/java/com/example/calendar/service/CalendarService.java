package com.example.calendar.service;

import com.example.calendar.model.*;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

@Service
public class CalendarService {

    public Map<String, Object> processCalendar(CalendarRequest request) {
        List<Event> sortedEvents = new ArrayList<>(request.getEvents());
        sortedEvents.sort(Comparator.comparing(Event::getStartTime));

        Map<String, Object> result = new HashMap<>();
        result.put("conflicts", detectConflicts(sortedEvents));
        result.put("freeSlots", findFreeSlots(sortedEvents, request));
        return result;
    }

    private List<Map<String, Object>> detectConflicts(List<Event> events) {
        List<Map<String, Object>> conflicts = new ArrayList<>();
        for (int i = 0; i < events.size(); i++) {
            Event e1 = events.get(i);
            for (int j = i + 1; j < events.size(); j++) {
                Event e2 = events.get(j);
                if (isOverlapping(e1, e2)) {
                    conflicts.add(buildConflict(e1, e2));
                }
            }
        }
        return conflicts;
    }

    private boolean isOverlapping(Event e1, Event e2) {
        return e1.getEndTime().isAfter(e2.getStartTime()) && e1.getStartTime().isBefore(e2.getEndTime());
    }

    private Map<String, Object> buildConflict(Event e1, Event e2) {
        Map<String, Object> conflict = new HashMap<>();
        conflict.put("event1", e1.getTitle());
        conflict.put("event2", e2.getTitle());
        conflict.put("overlapStart", Collections.max(List.of(e1.getStartTime(), e2.getStartTime())).toString());
        conflict.put("overlapEnd", Collections.min(List.of(e1.getEndTime(), e2.getEndTime())).toString());
        return conflict;
    }

    private List<Map<String, String>> findFreeSlots(List<Event> events, CalendarRequest request) {
        List<Map<String, String>> freeSlots = new ArrayList<>();
        ZoneId zone = ZoneId.of(request.getTimeZone());
        LocalTime workStart = LocalTime.parse(request.getWorkingHours().getStart());
        LocalTime workEnd = LocalTime.parse(request.getWorkingHours().getEnd());

        ZonedDateTime dayStart = ZonedDateTime.of(events.get(0).getStartTime().toLocalDate(), workStart, zone);
        ZonedDateTime dayEnd = ZonedDateTime.of(events.get(0).getStartTime().toLocalDate(), workEnd, zone);

        ZonedDateTime current = dayStart;
        for (Event e : events) {
            if (current.isBefore(e.getStartTime())) {
                addFreeSlot(freeSlots, current, e.getStartTime());
            }
            if (current.isBefore(e.getEndTime())) {
                current = e.getEndTime();
            }
        }

        if (current.isBefore(dayEnd)) {
            addFreeSlot(freeSlots, current, dayEnd);
        }

        return freeSlots;
    }

    private void addFreeSlot(List<Map<String, String>> slots, ZonedDateTime start, ZonedDateTime end) {
        if (end.isAfter(start)) {
            Map<String, String> slot = new HashMap<>();
            slot.put("start", start.toString());
            slot.put("end", end.toString());
            slots.add(slot);
        }
    }
}
