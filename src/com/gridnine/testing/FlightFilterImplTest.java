package com.gridnine.testing;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightFilterImplTest {
    private final FlightFilter out = new FlightFilterImpl();

    @Test
    void filteringByDepartureBeforeNowTest() {
        Segment segment = new Segment(LocalDateTime.now(), LocalDateTime.now().plusHours(3));
        Flight flight = new Flight(new ArrayList<>(List.of(segment)));
        List<Flight> flights = new ArrayList<>();
        flights.add(flight);

        List<Flight> result = out.filteringByDepartureBeforeNow(flights);

        assertEquals(0, result.size());
    }

    @Test
    void filteringByArrivalBeforeDepartureTest() {
        Segment segment = new Segment(LocalDateTime.now(), LocalDateTime.now().minusHours(3));
        Flight flight = new Flight(new ArrayList<>(List.of(segment)));
        List<Flight> flights = new ArrayList<>();
        flights.add(flight);

        List<Flight> result = out.filteringByArrivalBeforeDeparture(flights);

        assertEquals(0, result.size());
    }


    @Test
    void filteringByTimeOnGroundTest() {
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
    @Test
    void exceptionTest(){
        Segment segment1 = new Segment(LocalDateTime.now(), LocalDateTime.now().plusHours(3));
        Segment segment2 = new Segment(LocalDateTime.now(), LocalDateTime.now().plusHours(3));

        List<Segment> segments = new ArrayList<>();
        segments.add(segment1);
        segments.add(segment2);

        Flight flight1 = new Flight(new ArrayList<>(segments));
        List<Flight> flights1 = new ArrayList<>();
        flights1.add(flight1);

        assertThrows(IncorrectDateBetweenSegmentsException.class, ()-> out.filteringByDepartureBeforeNow(flights1));

        Segment segment3 = null;
        segments.add(1,segment3);
        Flight flight2 = new Flight(new ArrayList<>(segments));
        List<Flight> flights2 = new ArrayList<>();
        flights2.add(flight2);

        assertThrows(IllegalArgumentException.class, ()-> out.filteringByArrivalBeforeDeparture(flights2));

        Flight flight3 = null;
        List<Flight> flights3 = new ArrayList<>();
        flights3.add(flight3);

        assertThrows(IllegalArgumentException.class, ()-> out.filteringByArrivalBeforeDeparture(flights3));

    }
}