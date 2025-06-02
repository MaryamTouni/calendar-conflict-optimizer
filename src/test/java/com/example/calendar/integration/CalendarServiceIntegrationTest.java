package com.example.calendar.integration;

import com.example.calendar.model.CalendarRequest;
import com.example.calendar.model.Event;
import com.example.calendar.model.WorkingHours;
import com.example.calendar.repository.EventRepository;
import com.example.calendar.service.CalendarService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author Maryam Ahmed <Maryam.Ahmed@santechture.com>
 */
@SpringBootTest
@ActiveProfiles("test")
public class CalendarServiceIntegrationTest {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CalendarService calendarService;

    @BeforeEach
    void setup() {
        eventRepository.deleteAll();

        eventRepository.saveAll(List.of(
                new Event("Team Standup", ZonedDateTime.parse("2025-05-14T09:30:00+03:00"), ZonedDateTime.parse("2025-05-14T10:00:00+03:00")),
                new Event("Client Meeting", ZonedDateTime.parse("2025-05-14T10:00:00+03:00"), ZonedDateTime.parse("2025-05-14T11:00:00+03:00")),
                new Event("1:1 Review", ZonedDateTime.parse("2025-05-14T10:30:00+03:00"), ZonedDateTime.parse("2025-05-14T11:30:00+03:00")),
                new Event("Lunch Break", ZonedDateTime.parse("2025-05-14T13:00:00+03:00"), ZonedDateTime.parse("2025-05-14T14:00:00+03:00"))
        ));
    }

    @Test
    void testProcessCalendar() {
        List<com.example.calendar.model.Event> events = eventRepository.findAll();

        CalendarRequest request = new CalendarRequest();
        request.setEvents(events);
        request.setWorkingHours(new WorkingHours("09:00", "17:00"));
        request.setTimeZone("Asia/Riyadh");

        Map<String, Object> result = calendarService.processCalendar(request);

        List<Map<String, Object>> conflicts = (List<Map<String, Object>>) result.get("conflicts");
        assertThat(conflicts.size()).isEqualTo(1);
        assertThat(conflicts.get(0).get("event1")).isEqualTo("Client Meeting");
        assertThat(conflicts.get(0).get("event2")).isEqualTo("1:1 Review");

        List<Map<String, String>> freeSlots = (List<Map<String, String>>) result.get("freeSlots");
        assertThat(freeSlots.size()).isEqualTo(3);
        assertThat(freeSlots.get(0).get("start").substring(0, 22)).isEqualTo("2025-05-14T09:00+03:00");
        assertThat(freeSlots.get(0).get("end").substring(0, 22)).isEqualTo("2025-05-14T09:30+03:00");
    }
}
