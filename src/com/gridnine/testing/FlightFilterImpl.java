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
     */
    @Override
    public List<Flight> filteringByDepartureBeforeNow(List<Flight> flights) {
        LocalDateTime now = LocalDateTime.now();
        return flights.stream()
                .filter(flight -> validateByDateBetweenSegments(flight.getSegments()).stream()
                        .allMatch(segment -> segment.getDepartureDate().isAfter(now)))
                .collect(Collectors.toList());
    }

    //тут можно не фильтровать а выбрасывать исключение, в зависимости от задачи
    /**
     * Filters flights with arrival dates before their respective departure dates.
     *
     * @param flights The list of flights to filter.
     * @return A list of flights with arrival dates after their respective departure dates.
     */
    @Override
    public List<Flight> filteringByArrivalBeforeDeparture(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> validateByDateBetweenSegments(flight.getSegments()).stream()
                        .allMatch(segment -> segment.getArrivalDate().isAfter(segment.getDepartureDate())))
                .collect(Collectors.toList());
    }

    /**
     * Filters flights with total ground time exceeding the specified number of hours.
     *
     * @param flights The list of flights to filter.
     * @param hours   The maximum allowed ground time in hours.
     * @return A list of flights with ground time less than the specified number of hours.
     */
    @Override
    public List<Flight> filteringByTimeOnGround(List<Flight> flights, int hours) {
        return flights.stream()
                .filter(flight -> calculateGroundTime(flight.getSegments()) < hours)
                .collect(Collectors.toList());
    }

    /**
     * Calculates the total ground time in hours for a list of flight segments.
     *
     * @param segments The list of flight segments.
     * @return The total ground time in hours.
     * @throws IncorrectDateBetweenSegmentsException if there is an issue with the dates between segments.
     */
    private int calculateGroundTime(List<Segment> segments) {
        int groundTime = 0;
        for (int i = 1; i < segments.size(); i++) {
            groundTime += Duration.between(segments.get(i - 1).getArrivalDate(), segments.get(i).getDepartureDate()).toHours();
            //   если хоть в одной итерации получаем отрицательное число, то выбрасываем исключение
            if (groundTime < 0) {
                throw new IncorrectDateBetweenSegmentsException("arrival date in last segment " + segments.get(i - 1).getArrivalDate() + " - departure date in next segment " + segments.get(i).getDepartureDate());
            }
        }
        return groundTime;
    }

    /**
     * Validates that the departure date of each segment is before the arrival date of the next segment.
     *
     * @param segments The list of flight segments to validate.
     * @return The list of flight segments if validation passes.
     * @throws IncorrectDateBetweenSegmentsException if there is an issue with the dates between segments.
     */
    private List<Segment> validateByDateBetweenSegments(List<Segment> segments) {
        for (int i = 1; i < segments.size(); i++) {
            if (segments.get(i - 1).getArrivalDate().isAfter(segments.get(i).getDepartureDate())) {
                throw new IncorrectDateBetweenSegmentsException("arrival date in last segment " + segments.get(i - 1).getArrivalDate() + " - departure date in next segment " + segments.get(i).getDepartureDate());
            }
        }
        return segments;
    }
}
