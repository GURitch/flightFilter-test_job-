package com.gridnine.testing;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();

        System.out.println("Original flights:");
        flights.forEach(System.out::println);

        System.out.println("\nDepartureBeforeNowFilter:");
        new DepartureBeforeNowFilter().filter(flights).forEach(System.out::println);

        System.out.println("\nArrivalBeforeDepartureFilter:");
        new ArrivalBeforeDepartureFilter().filter(flights).forEach(System.out::println);

        System.out.println("\nGroundTimeExceedsTwoHoursFilter:");
        new GroundTimeExceedsTwoHoursFilter().filter(flights).forEach(System.out::println);

        FlightFilterChain filterChain = new FlightFilterChain(List.of(
                new DepartureBeforeNowFilter(),
                new ArrivalBeforeDepartureFilter(),
                new GroundTimeExceedsTwoHoursFilter()
        ));

        System.out.println("\nFlightFilterChain:");
        filterChain.filter(flights).forEach(System.out::println);
    }
}