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

    public ParkingLot(int initialCapacity, ParkingLotUser parkingLotOwner, ParkingLotUser securityPerson) {
        MAX_CARS = initialCapacity;
        this.parkedCars = new HashMap<>();
        this.concernedPeopleList = new ArrayList<>();
        this.concernedPeopleList.add(parkingLotOwner);
        this.concernedPeopleList.add(securityPerson);
    }

    public int park(Car car) throws CarAlreadyParkedException, ParkingLotFullException {
        if (this.isParked(car)) {
            throw new CarAlreadyParkedException("Car is already parked with this number");
        }
        if (parkedCars.size() == MAX_CARS) {
            throw new ParkingLotFullException("Slots are full");
        }
        int slotNumber = this.parkedCars.size() + 1;
        this.parkedCars.put(slotNumber, car);
        if (this.parkedCars.size() == MAX_CARS) this.notifyConcernedUsersAboutFullParkingLot();
        return slotNumber;
    }

    public Car unPark(int tokenNumber) throws CarNotParkedException {
        Car car = this.parkedCars.remove(tokenNumber);
        if(car == null)throw new CarNotParkedException("This car is not parked in this lot");
        return car;
    }

    private boolean isParked(Car car) {
        return this.parkedCars.containsValue(car);
    }

    public List<ParkingLotUser> getConcernedPeopleList() {
        return concernedPeopleList;
    }

    private void notifyConcernedUsersAboutFullParkingLot() {
        for(ParkingLotUser user: this.concernedPeopleList) {
            user.notifyLotFull();
        }
    }
}
