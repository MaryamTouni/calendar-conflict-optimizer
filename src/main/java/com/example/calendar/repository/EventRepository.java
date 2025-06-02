package com.example.calendar.repository;

import com.example.calendar.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

/**
 *
 * @author Maryam Ahmed <Maryam.Ahmed@santechture.com>
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
 
}
