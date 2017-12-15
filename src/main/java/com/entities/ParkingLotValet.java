package com.entities;

import com.exceptions.CarAlreadyParkedException;
import com.exceptions.ParkingLotFullException;
import javafx.util.Pair;

import java.util.List;

public class ParkingLotValet {
    private List<ParkingLot> parkingLots;
    private int numberOfCarsParked = 0;

    public ParkingLotValet(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
    }

    public Pair<Integer, Integer> park(Car car) throws CarAlreadyParkedException, ParkingLotFullException {
        int startingIndex = numberOfCarsParked%parkingLots.size();
        int indexOfCurrentParkingLot = findEmptyLotWithStartingIndex(startingIndex);
        int token = this.parkingLots.get(indexOfCurrentParkingLot).park(car);
        numberOfCarsParked++;
        return new Pair<>(indexOfCurrentParkingLot, token);
    }

    public Pair<Integer, Integer> parkForHandicappedUser(Car car) throws CarAlreadyParkedException, ParkingLotFullException {
        int startingIndex = 0;
        int indexOfCurrentParkingLot = findEmptyLotWithStartingIndex(startingIndex);
        int token = this.parkingLots.get(indexOfCurrentParkingLot).park(car);
        numberOfCarsParked++;
        return new Pair<>(indexOfCurrentParkingLot, token);
    }

    private int findEmptyLotWithStartingIndex(int startingIndex) throws ParkingLotFullException{
        int attemptsForParking = 0;
        int indexOfCurrentParkingLot = startingIndex;
        while(attemptsForParking < parkingLots.size()) {
            if(this.parkingLots.get(indexOfCurrentParkingLot).isFull()) {
                indexOfCurrentParkingLot++;
                attemptsForParking++;
            }else {
                return indexOfCurrentParkingLot;
            }
        }
        throw new ParkingLotFullException("All parking lots are full");
    }
}
