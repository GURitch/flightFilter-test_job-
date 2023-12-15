package com.gridnine.testing;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        FlightFilter filter = new FlightFilterImpl();
        int hoursOnGround = 1;

        System.out.println("Origin flights:");
        flights.forEach(System.out::println);
        System.out.println();

        System.out.println("filteringByDepartureBeforeNow");
        filter.filteringByDepartureBeforeNow(flights).forEach(System.out::println);
        System.out.println();

        System.out.println("filteringByArrivalBeforeDeparture");
        filter.filteringByArrivalBeforeDeparture(flights).forEach(System.out::println);
        System.out.println();

        System.out.println("filteringByTimeOnGround >" + hoursOnGround);
        filter.filteringByTimeOnGround(flights, hoursOnGround).forEach(System.out::println);

    }
}