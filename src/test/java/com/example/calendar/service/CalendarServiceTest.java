package com.example.calendar.service;

import com.example.calendar.model.*;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class CalendarServiceTest {

     private final CalendarService service = new CalendarService();

    @Test
    void testProcessCalendar_conflictsAndFreeSlots() {
        CalendarRequest request = buildSampleRequest();

        Map<String, Object> result = service.processCalendar(request);

        // Test conflicts
        List<Map<String, Object>> conflicts = (List<Map<String, Object>>) result.get("conflicts");
        assertEquals(1, conflicts.size());

        Map<String, Object> conflict = conflicts.get(0);
        assertEquals("Client Meeting", conflict.get("event1"));
        assertEquals("1:1 Review", conflict.get("event2"));
        assertEquals("2025-05-14T10:30+03:00", conflict.get("overlapStart").toString().substring(0, 22)); 

        // Test free slots
        List<Map<String, String>> freeSlots = (List<Map<String, String>>) result.get("freeSlots");
        assertEquals(3, freeSlots.size());

        assertEquals("2025-05-14T09:00+03:00", freeSlots.get(0).get("start").substring(0, 22));
        assertEquals("2025-05-14T09:30+03:00", freeSlots.get(0).get("end").substring(0, 22));
    }

    private CalendarRequest buildSampleRequest() {
        WorkingHours hours = new WorkingHours("09:00", "17:00");
        String tz = "Asia/Riyadh";

        List<Event> events = List.of(
            new Event("Team Standup", ZonedDateTime.parse("2025-05-14T09:30:00+03:00"), ZonedDateTime.parse("2025-05-14T10:00:00+03:00")),
            new Event("Client Meeting", ZonedDateTime.parse("2025-05-14T10:00:00+03:00"), ZonedDateTime.parse("2025-05-14T11:00:00+03:00")),
            new Event("1:1 Review", ZonedDateTime.parse("2025-05-14T10:30:00+03:00"), ZonedDateTime.parse("2025-05-14T11:30:00+03:00")),
            new Event("Lunch Break", ZonedDateTime.parse("2025-05-14T13:00:00+03:00"), ZonedDateTime.parse("2025-05-14T14:00:00+03:00"))
        );

        CalendarRequest request = new CalendarRequest();
        request.setWorkingHours(hours);
        request.setTimeZone(tz);
        request.setEvents(events);

        return request;
    }
}