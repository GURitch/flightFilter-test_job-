package com.gridnine.testing;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class GroundTimeExceedsTwoHoursFilter implements FlightFilter {
    @Override
    public List<Flight> filter(List<Flight> flights) {
        if (flights == null || flights.isEmpty()) {
            throw new IllegalArgumentException("Flights cannot be null or empty");
        }
        List<Flight> result = new ArrayList<>();
        for (Flight flight : flights) {
            if (flight == null) {
                throw new IllegalArgumentException("Flight cannot be null");
            }
            List<Segment> segments = flight.getSegments();
            if (segments == null || segments.isEmpty()) {
                throw new IllegalArgumentException("Segments cannot be null or empty");
            }
            int index = 1;
            int hoursInGround = 0;
            for (int i = 0; i < segments.size(); i++) {
                if (segments.size() > index && segments.get(index - 1).getArrivalDate().isAfter(segments.get(index).getDepartureDate())) {
                    throw new IncorrectDateBetweenSegmentsException("arrival date in last segment " + segments.get(index - 1).getArrivalDate() + " - departure date in next segment " + segments.get(index).getDepartureDate());
                }
                if (segments.size() > index) {
                    hoursInGround += Duration.between(segments.get(index - 1).getArrivalDate(), segments.get(index).getDepartureDate()).toHours();
                }
                index++;
            }
            if (hoursInGround <= 2) {
                result.add(flight);
            }
        }
        return result;
    }
}
