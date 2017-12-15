package com.entities;


import com.exceptions.CarAlreadyParkedException;
import com.exceptions.CarNotParkedException;
import com.exceptions.ParkingLotFullException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLot {
    private Map<Integer, Car> parkedCars;
    private final int MAX_CARS;
    private List<ParkingLotUser> concernedPeopleList;

    public ParkingLot(int initialCapacity, List<ParkingLotUser> concernedPeopleList) {
        MAX_CARS = initialCapacity;
        this.parkedCars = new HashMap<>();
        this.concernedPeopleList = new ArrayList<>();
        this.concernedPeopleList = concernedPeopleList;
    }

    public int park(Car car) throws CarAlreadyParkedException, ParkingLotFullException {
        if (this.isParked(car)) {
            throw new CarAlreadyParkedException("Car is already parked with this number");
        }
        if (this.isFull()) {
            throw new ParkingLotFullException("Slots are full");
        }
        int slotNumber = this.parkedCars.size() + 1;
        this.parkedCars.put(slotNumber, car);
        if (this.isFull()) this.notifyConcernedUsersAboutFullParkingLot();
        return slotNumber;
    }

    private boolean isFull() {
        return parkedCars.size() == MAX_CARS;
    }

    public Car unPark(int tokenNumber) throws CarNotParkedException {
        Car car = this.parkedCars.remove(tokenNumber);
        if(car == null)throw new CarNotParkedException("This car is not parked in this lot");
        return car;
    }

    private boolean isParked(Car car) {
        return this.parkedCars.containsValue(car);
    }

    private List<ParkingLotUser> getConcernedPeopleList() {
        return concernedPeopleList;
    }

    private void notifyConcernedUsersAboutFullParkingLot() {
        this.concernedPeopleList.forEach(parkingLotUser -> parkingLotUser.notifyLotFull());
    }
}
