package com.entities;

public class NotifiableUser implements ParkingLotUser{
    private boolean isNotified = false;

    public void notifyLotFull() {
        this.isNotified = true;
    }
    public boolean isNotified() {
        return this.isNotified;
    }
}
