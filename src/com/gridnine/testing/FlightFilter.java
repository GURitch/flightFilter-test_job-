package com.gridnine.testing;

import java.util.List;

public interface FlightFilter {
    List<Flight> filteringByDepartureBeforeNow(List<Flight> flights);

    List<Flight> filteringByArrivalBeforeDeparture(List<Flight> flights);

    List<Flight> filteringByTimeOnGround(List<Flight> flights, int hours);
}
