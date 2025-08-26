package com.example.geektrust.service;

import com.example.geektrust.domain.Station;
import com.example.geektrust.model.MetroCard;
import com.example.geektrust.enums.PassengerType;
import com.example.geektrust.enums.StationType;
import com.example.geektrust.constants.Fare;

import java.util.*;

public class MetroService {

    private final Map<String, MetroCard> cards = new HashMap<>();
    private final Map<StationType, Station> stations = new HashMap<>();
    private final Map<String, Boolean> awaitingReturn = new HashMap<>();

    public MetroService() {
        stations.put(StationType.CENTRAL, new Station(StationType.CENTRAL));
        stations.put(StationType.AIRPORT, new Station(StationType.AIRPORT));
    }

    public void processCommand(String line) {
        String[] parts = line.split(" ");
        String command = parts[0];
        switch (command) {
            case "BALANCE":
                processBalance(parts[1], Integer.parseInt(parts[2]));
                break;
            case "CHECK_IN":
                processCheckIn(parts[1], PassengerType.valueOf(parts[2]), StationType.valueOf(parts[3]));
                break;
            case "PRINT_SUMMARY":
                printSummary();
                break;
            default:
                System.out.println("Unknown command: " + line);
        }
    }

    public void processBalance(String cardNumber, int balance) {
        cards.put(cardNumber, new MetroCard(cardNumber, balance));
    }

    public void processCheckIn(String cardNumber, PassengerType passengerType, StationType fromStation) {
        MetroCard card = cards.get(cardNumber);
        if (card == null) return;

        boolean isReturn = awaitingReturn.getOrDefault(cardNumber, false);
        int baseFare = getBaseFare(passengerType);
        int discount = isReturn ? baseFare / 2 : 0;
        int fare = baseFare - discount;

        if (card.getBalance() < fare) {
            int rechargeAmount = fare - card.getBalance();
            card.recharge(rechargeAmount);
            int serviceFee = (int) Math.ceil(rechargeAmount * Fare.SERVICE_FEE_PERCENT);
            stations.get(fromStation).addCollection(serviceFee, 0);
        }

        card.deduct(fare);
        stations.get(fromStation).addCollection(fare, discount);
        stations.get(fromStation).addPassenger(passengerType);

        awaitingReturn.put(cardNumber, !isReturn);
    }

    private int getBaseFare(PassengerType type) {
        switch (type) {
            case ADULT: return Fare.ADULT_FARE;
            case SENIOR_CITIZEN: return Fare.SENIOR_CITIZEN_FARE;
            case KID: return Fare.KID_FARE;
        }
        return 0;
    }

    public void printSummary() {
        for (StationType st : Arrays.asList(StationType.CENTRAL, StationType.AIRPORT)) {
            Station station = stations.get(st);
            System.out.println("TOTAL_COLLECTION " + st.name() + " " +
                    station.getTotalCollection() + " " + station.getTotalDiscount());
            System.out.println("PASSENGER_TYPE_SUMMARY");
            printPassengerSummary(station.getPassengerCounts());
        }
    }

    private void printPassengerSummary(Map<PassengerType, Integer> passengerCounts) {
        List<Map.Entry<PassengerType, Integer>> list = new ArrayList<>(passengerCounts.entrySet());
        list.sort((a, b) -> {
            if (!a.getValue().equals(b.getValue())) return b.getValue() - a.getValue();
            return a.getKey().name().compareTo(b.getKey().name());
        });
        for (Map.Entry<PassengerType, Integer> entry : list) {
            System.out.println(entry.getKey().name() + " " + entry.getValue());
        }
    }
}
