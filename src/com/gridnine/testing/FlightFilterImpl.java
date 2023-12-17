package com.gridnine.testing;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class FlightFilterImpl implements FlightFilter {

    /**
     * Filters flights with departure dates before the current moment.
     *
     * @param flights The list of flights to filter.
     * @return A list of flights with departure dates after the current moment.
     * @throws IllegalArgumentException if the flights, flight, or segments are null or empty.
     */
    @Override
    public List<Flight> filteringByDepartureBeforeNow(List<Flight> flights) {
        if (flights == null || flights.isEmpty()) {
            throw new IllegalArgumentException("Flights cannot be null or empty");
        }
        LocalDateTime now = LocalDateTime.now();
        return flights.stream()
                .filter(flight -> {
                    if (flight == null) {
                        throw new IllegalArgumentException("Flight cannot be null");
                    }
                    List<Segment> segments = validateSegments(flight.getSegments());
                    return segments.stream()
                            .allMatch(segment -> segment.getDepartureDate().isAfter(now));
                })
                .collect(Collectors.toList());
    }

    //тут можно не фильтровать а выбрасывать исключение, в зависимости от задачи
    /**
     * Filters flights with arrival dates before their respective departure dates.
     *
     * @param flights The list of flights to filter.
     * @return A list of flights with arrival dates after their respective departure dates.
     * @throws IllegalArgumentException if the flights, flight, or segments are null or empty.
     */
    @Override
    public List<Flight> filteringByArrivalBeforeDeparture(List<Flight> flights) {
        if (flights == null || flights.isEmpty()) {
            throw new IllegalArgumentException("Flights cannot be null or empty");
        }
        return flights.stream()
                .filter(flight -> {
                    if (flight == null) {
                        throw new IllegalArgumentException("Flight cannot be null");
                    }
                    List<Segment> segments = validateSegments(flight.getSegments());
                    return segments.stream()
                            .allMatch(segment -> segment.getArrivalDate().isAfter(segment.getDepartureDate()));
                })
                .collect(Collectors.toList());
    }

    /**
     * Filters flights with total ground time exceeding the specified number of hours.
     *
     * @param flights The list of flights to filter.
     * @param hours   The maximum allowed ground time in hours.
     * @return A list of flights with ground time less than the specified number of hours.
     * @throws IllegalArgumentException if the flights, flight, or segments are null or empty.
     */
    @Override
    public List<Flight> filteringByTimeOnGround(List<Flight> flights, int hours) {
        if (flights == null || flights.isEmpty()) {
            throw new IllegalArgumentException("Flights cannot be null or empty");
        }
        return flights.stream()
                .filter(flight -> {
                    if (flight == null) {
                        throw new IllegalArgumentException("Flight cannot be null");
                    }
                    return !(calculateGroundTime(validateSegments(flight.getSegments())) > hours);
                })
                .collect(Collectors.toList());
    }

    /**
     * Calculates the total ground time in hours for a list of flight segments.
     *
     * @param segments The list of flight segments.
     * @return The total ground time in hours.
     */
    private int calculateGroundTime(List<Segment> segments) {
        int hoursOnGround = 0;
        for (int i = 1; i < segments.size(); i++) {
            hoursOnGround += Duration.between(segments.get(i - 1).getArrivalDate(), segments.get(i).getDepartureDate()).toHours();
        }
        return hoursOnGround;
    }

    /**
     * Validates that the departure date of each segment is before the arrival date of the next segment.
     *
     * @param segments The list of flight segments to validate.
     * @return The list of flight segments if validation passes.
     * @throws IllegalArgumentException if the segments are null or empty or if the first segment is null.
     * @throws IncorrectDateBetweenSegmentsException if there is an issue with the dates between segments.
     */
    private List<Segment> validateSegments(List<Segment> segments) {
        if (segments == null || segments.isEmpty() || segments.get(0) == null) {
            throw new IllegalArgumentException("Segments cannot be null or empty, and the first segment cannot be null");
        }
        for (int i = 1; i < segments.size(); i++) {
            if (segments.get(i) == null) {
                throw new IllegalArgumentException("Segment cannot be null");
            }
            if (segments.get(i - 1).getArrivalDate().isAfter(segments.get(i).getDepartureDate())) {
                throw new IncorrectDateBetweenSegmentsException("arrival date in last segment " + segments.get(i - 1).getArrivalDate() + " - departure date in next segment " + segments.get(i).getDepartureDate());
            }
        }
        return segments;
    }
}
