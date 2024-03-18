package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FlightFilteringImplTest {
    FlightFilteringImpl flightFilteringTest = new FlightFilteringImpl();
    static LocalDateTime twoDaysBeforeNow = LocalDateTime.now().minusDays(2);
    static LocalDateTime twoDaysFromNow = LocalDateTime.now().plusDays(2);

    Segment segment_1 = new Segment(twoDaysBeforeNow, twoDaysBeforeNow.plusHours(2));
    Segment segment_2 = new Segment(twoDaysFromNow, twoDaysFromNow.minusHours(5));
    Segment segment_3 = new Segment(LocalDateTime.now(), LocalDateTime.now().plusHours(2));
    Segment segment_4 = new Segment(twoDaysFromNow, twoDaysFromNow.plusHours(2));
    Segment segment_5 = new Segment(twoDaysFromNow.plusHours(3), twoDaysFromNow.plusHours(6));
    Segment segment_6 = new Segment(twoDaysFromNow.plusHours(7), twoDaysFromNow.plusHours(10));

    List<Segment> segmentList_1 = new ArrayList<>();
    List<Segment> segmentList_2 = new ArrayList<>();
    List<Segment> segmentList_3 = new ArrayList<>();
    List<Segment> segmentList_4 = new ArrayList<>();
    List<Segment> segmentList_5 = new ArrayList<>();

    //A flight departing in the past
    Flight testFlight_1 = new Flight(segmentList_1);
    //A flight that departs before it arrives
    Flight testFlight_2 = new Flight(segmentList_2);
    //A normal flight with one segment
    Flight testFlight_3 = new Flight(segmentList_3);
    //A normal flight with less than two hours ground time
    Flight testFlight_4 = new Flight(segmentList_4);
    //A flight with more than two hours ground time
    Flight testFlight_5 = new Flight(segmentList_5);

    List<Flight> expected = new ArrayList<>();
    List<Flight> actual = new ArrayList<>();

    @BeforeEach
    void addSegment() {
        segmentList_1.add(segment_1);
        segmentList_2.add(segment_2);
        segmentList_3.add(segment_3);

        segmentList_4.add(segment_4);
        segmentList_4.add(segment_5);

        segmentList_5.add(segment_4);
        segmentList_5.add(segment_6);
    }

    @AfterEach
    void clearFlight() {
        expected.clear();
        actual.clear();
    }

    @Test
    void departureBeforeNow() {
        actual.add(testFlight_1);
        List<Flight> result = flightFilteringTest.departureBeforeNow(actual);
        assertTrue(result.isEmpty());
    }

    @Test
    void flightsBeforeDepartureDate() {
        expected.add(testFlight_3);
        actual.add(testFlight_2);
        actual.add(testFlight_3);
        List<Flight> result = flightFilteringTest.flightsBeforeDepartureDate(actual);
        assertEquals(result, expected);
    }

    @Test
    void twoHoursMoreGroundStay() {
        expected.add(testFlight_4);
        actual.add(testFlight_4);
        actual.add(testFlight_5);
        List<Flight> result = flightFilteringTest.twoHoursMoreGroundStay(actual);
        assertEquals(result, expected);
    }
}