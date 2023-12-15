package com.gridnine.testing;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class FlightFilterImpl implements FlightFilter {
    @Override
    public List<Flight> filteringByDepartureBeforeNow(List<Flight> flights) {
        LocalDateTime now = LocalDateTime.now();
        return flights.stream()
                .filter(flight -> validateByDateBetweenSegments(flight.getSegments()).stream()
                        .allMatch(segment -> segment.getDepartureDate().isAfter(now)))
                .collect(Collectors.toList());
    }

    //тут можно не фильтровать а выбрасывать исключение, в зависимости от задачи
    @Override
    public List<Flight> filteringByArrivalBeforeDeparture(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> validateByDateBetweenSegments(flight.getSegments()).stream()
                        .allMatch(segment -> segment.getArrivalDate().isAfter(segment.getDepartureDate())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Flight> filteringByTimeOnGround(List<Flight> flights, int hours) {
        return flights.stream()
                .filter(flight -> calculateGroundTime(flight.getSegments()) < hours)
                .collect(Collectors.toList());
    }

    //    Считаем сумму часов на земле в одном перелете
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

    //    проверяем что дата отправления у следующего сегмента не раньше даты прибытия в предыдущем
    private List<Segment> validateByDateBetweenSegments(List<Segment> segments) {
        for (int i = 1; i < segments.size(); i++) {
            if (segments.get(i - 1).getArrivalDate().isAfter(segments.get(i).getDepartureDate())) {
                throw new IncorrectDateBetweenSegmentsException("arrival date in last segment " + segments.get(i - 1).getArrivalDate() + " - departure date in next segment " + segments.get(i).getDepartureDate());
            }
        }
        return segments;
    }
}
