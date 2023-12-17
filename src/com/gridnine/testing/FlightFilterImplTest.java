package com.gridnine.testing;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightFilterImplTest {

    @Test
    void filteringByDepartureBeforeNow() {
        Segment segment = new Segment(LocalDateTime.now().minusDays(3), LocalDateTime.now().plusDays(3));
        Flight flight = new Flight(new ArrayList<>(List.of(segment)));
        List<Flight> flights = new ArrayList<>();
        flights.add(flight);

        FlightFilter flightFilter = new FlightFilterImpl();

        List<Flight> result = flightFilter.filteringByDepartureBeforeNow(flights);

        assertEquals(0, result.size());
    }

    @Test
    void filteringByArrivalBeforeDeparture() {
        Segment segment = new Segment(LocalDateTime.now(), LocalDateTime.now().minusDays(3));
        Flight flight = new Flight(new ArrayList<>(List.of(segment)));
        List<Flight> flights = new ArrayList<>();
        flights.add(flight);

        FlightFilter flightFilter = new FlightFilterImpl();

        List<Flight> result = flightFilter.filteringByArrivalBeforeDeparture(flights);

        assertEquals(0, result.size());
    }

    @Test
    void filteringByTimeOnGround() {
        Segment segment1 = new Segment(LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        Segment segment2 = new Segment(LocalDateTime.now().plusHours(4), LocalDateTime.now().plusHours(5));
        Flight flight = new Flight(new ArrayList<>(List.of(segment1, segment2)));
        List<Flight> flights = new ArrayList<>();
        flights.add(flight);

        FlightFilter flightFilter = new FlightFilterImpl();
        int hoursOnGround = 2;
        List<Flight> result = flightFilter.filteringByTimeOnGround(flights, hoursOnGround);

        assertEquals(0, result.size());
    }
}