package com.gridnine.testing;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DepartureBeforeNowFilterTest {

    @Test
    void filter() {
        Segment segment = new Segment(LocalDateTime.now().minusHours(3), LocalDateTime.now());
        Flight flight = new Flight(new ArrayList<>(List.of(segment)));
        List<Flight> flights = new ArrayList<>();
        flights.add(flight);

        List<Flight> result = new DepartureBeforeNowFilter().filter(flights);

        assertEquals(0, result.size());
    }
}