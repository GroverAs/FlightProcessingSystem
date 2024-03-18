package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FlightFilteringImpl implements FlightFiltering {

    public FlightFilteringImpl() {
    }

    @Override
    public List<Flight> departureBeforeNow(List<Flight> flights) {
        if (flights != null) {
            return flights.stream()
                    .filter(flight -> flight.getSegments().stream()
                            .anyMatch(segment -> LocalDateTime.now().isBefore(segment.getDepartureDate())))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public List<Flight> flightsBeforeDepartureDate(List<Flight> flights) {
        List<Flight> resultList = new ArrayList<>(flights);
        resultList.removeIf(flight -> flight.getSegments().stream()
                .anyMatch(segment -> segment.getArrivalDate().isBefore(segment.getDepartureDate())));
        return resultList;
    }

    @Override
    public List<Flight> twoHoursMoreGroundStay(List<Flight> flightList) {
        List<Flight> resultList = new ArrayList<>(flightList);
        for (Flight flight : flightList) {
            List<Segment> segments = flight.getSegments();
            int groundTime;
            if (segments.size() > 1) {
                for (int i = 1; i < segments.size(); i++) {
                    groundTime = Math.abs(segments.get(i).getDepartureDate().getHour() - segments.get(i - 1).getArrivalDate().getHour());
                    if (groundTime >= 2) {
                        resultList.remove(flight);
                    }
                }
            }
        }
        return resultList;
    }
}
