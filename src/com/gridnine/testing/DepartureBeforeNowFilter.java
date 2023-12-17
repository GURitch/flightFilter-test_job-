package com.gridnine.testing;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DepartureBeforeNowFilter implements FlightFilter {
    @Override
    public List<Flight> filter(List<Flight> flights) {
        if (flights == null || flights.isEmpty()) {
            throw new IllegalArgumentException("Flights cannot be null or empty");
        }
        LocalDateTime now = LocalDateTime.now();
        List<Flight> result = new ArrayList<>();
        for (Flight flight : flights) {
            if (flight == null) {
                throw new IllegalArgumentException("Flight cannot be null");
            }
            List<Segment> segments = flight.getSegments();
            if (segments == null || segments.isEmpty()) {
                throw new IllegalArgumentException("Segments cannot be null or empty");
            }
            if (segments.get(0).getDepartureDate().isAfter(now)) {
                result.add(flight);
            }
        }
        return result;
    }
}
