package com.example.geektrust.domain;

import com.example.geektrust.enums.PassengerType;
import com.example.geektrust.enums.StationType;

import java.util.HashMap;
import java.util.Map;

public class Station {

    private final StationType stationType;
    private int totalCollection;
    private int totalDiscount;
    private final Map<PassengerType, Integer> passengerCounts;

    public Station(StationType stationType) {
        this.stationType = stationType;
        this.totalCollection = 0;
        this.totalDiscount = 0;
        this.passengerCounts = new HashMap<>();
    }

    public void addCollection(int fare, int discount) {
        this.totalCollection += fare;
        this.totalDiscount += discount;
    }

    public void addPassenger(PassengerType type) {
        passengerCounts.put(type, passengerCounts.getOrDefault(type, 0) + 1);
    }

    public int getTotalCollection() {
        return totalCollection;
    }

    public int getTotalDiscount() {
        return totalDiscount;
    }

    public Map<PassengerType, Integer> getPassengerCounts() {
        return passengerCounts;
    }

    public StationType getStationType() {
        return stationType;
    }
}
