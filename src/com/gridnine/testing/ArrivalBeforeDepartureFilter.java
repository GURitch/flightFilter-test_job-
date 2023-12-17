package com.gridnine.testing;

import java.util.ArrayList;
import java.util.List;


public class ArrivalBeforeDepartureFilter implements FlightFilter {
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
            List<Segment> newSegments = new ArrayList<>();
            int index = 1;
            for (int i = 0; i < segments.size(); i++) {
                if (segments.size() > index && segments.get(index - 1).getArrivalDate().isAfter(segments.get(index).getDepartureDate())) {
                    throw new IncorrectDateBetweenSegmentsException("arrival date in last segment " + segments.get(index - 1).getArrivalDate() + " - departure date in next segment " + segments.get(index).getDepartureDate());
                }
                if (segments.get(i).getArrivalDate().isAfter(segments.get(i).getDepartureDate())) {
                    newSegments.add(segments.get(i));
                }
                index++;
            }
            if (!newSegments.isEmpty()) {
                result.add(new Flight(newSegments));
            }
        }
        return result;
    }
}
