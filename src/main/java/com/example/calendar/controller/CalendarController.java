package com.example.calendar.controller;

import com.example.calendar.model.CalendarRequest;
import com.example.calendar.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

    @Autowired
    private CalendarService calendarService;

    @PostMapping("/optimize")
    public Map<String, Object> optimizeCalendar(@RequestBody CalendarRequest request) {
        return calendarService.processCalendar(request);
    }
}
