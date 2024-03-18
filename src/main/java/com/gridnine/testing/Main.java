package com.gridnine.testing;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.service.FlightFilteringImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();

        FlightFilteringImpl flightFilter = new FlightFilteringImpl();
        System.out.println("\n Full flight list without filter:");
        printResultFilterList(flights);

        System.out.println("\n Flight list excluding flights departure before now:");
        printResultFilterList(flightFilter.departureBeforeNow(flights));

        System.out.println("\n Flight list excluding flights arrival before departure:");
        printResultFilterList(flightFilter.flightsBeforeDepartureDate(flights));

        System.out.println("\n Flight list excluding flights with more than two hours ground time:");
        printResultFilterList(flightFilter.twoHoursMoreGroundStay(flights));
    }

    public static void printResultFilterList(List<Flight> list) {
        list.forEach(System.out::println);
    }
}
