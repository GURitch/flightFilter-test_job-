package com.gridnine.testing;

import java.util.List;

public class FlightFilterChain implements FlightFilter {
    private final List<FlightFilter> filters;

    public FlightFilterChain(List<FlightFilter> filters) {
        this.filters = filters;
    }

    @Override
    public List<Flight> filter(List<Flight> flights) {
        List<Flight> result = flights;
        for (FlightFilter filter : filters) {
            result = filter.filter(result);
        }
        return result;
    }
}
