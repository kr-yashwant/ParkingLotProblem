package com.entities;

import java.util.HashSet;
import java.util.Set;

public class ParkingLot {
    private Set<Integer> parkedCarNums;

    public ParkingLot() {
        this.parkedCarNums = new HashSet<>();
    }

    public boolean park(Car car) {
        this.parkedCarNums.add(car.getId());
        return true;
    }

    public boolean unPark(int carId) {
        if(this.has(carId)) {
            return true;
        }
        throw new IllegalArgumentException("This car is not parked in this lot");
    }

    private boolean has(int carId) {
        return parkedCarNums.contains(carId);
    }
}
