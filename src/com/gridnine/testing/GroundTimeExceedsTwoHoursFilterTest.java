package com.gridnine.testing;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroundTimeExceedsTwoHoursFilterTest {

    @Test
    void filter() {
        Segment segment = new Segment(LocalDateTime.now().minusHours(3), LocalDateTime.now());
        Segment segment2 = new Segment(LocalDateTime.now().plusHours(3), LocalDateTime.now().plusHours(5));
        Flight flight = new Flight(new ArrayList<>(List.of(segment, segment2)));
        List<Flight> flights = new ArrayList<>();
        flights.add(flight);

        List<Flight> result = new GroundTimeExceedsTwoHoursFilter().filter(flights);

        assertEquals(0, result.size());
    }
}