package com.example.geektrust.domain;

import com.example.geektrust.enums.PassengerType;

import java.util.EnumMap;
import java.util.Map;

public class PassengerSummary {

    private final Map<PassengerType, Integer> passengerCounts;

    public PassengerSummary() {
        this.passengerCounts = new EnumMap<>(PassengerType.class);
        for (PassengerType type : PassengerType.values()) {
            passengerCounts.put(type, 0);
        }
    }

    public void addPassenger(PassengerType type) {
        passengerCounts.put(type, passengerCounts.get(type) + 1);
    }

    public int getCount(PassengerType type) {
        return passengerCounts.get(type);
    }

    public Map<PassengerType, Integer> getSummary() {
        return passengerCounts;
    }
}
