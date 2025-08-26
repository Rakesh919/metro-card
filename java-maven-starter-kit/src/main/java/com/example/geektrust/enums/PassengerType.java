package com.example.geektrust.enums;

import com.example.geektrust.constants.Fare;

public enum PassengerType {

    ADULT(Fare.ADULT_FARE),
    SENIOR_CITIZEN(Fare.SENIOR_CITIZEN_FARE),
    KID(Fare.KID_FARE);

    private final int baseFare;

    PassengerType(int baseFare) {
        this.baseFare = baseFare;
    }

    public int getBaseFare() {
        return baseFare;
    }
}
