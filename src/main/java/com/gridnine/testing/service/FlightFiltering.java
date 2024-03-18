package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;

import java.util.List;

public interface FlightFiltering {
    List<Flight> departureBeforeNow(List<Flight> flights);

    List<Flight> flightsBeforeDepartureDate(List<Flight> flights);

    List<Flight> twoHoursMoreGroundStay(List<Flight> flights);

}
