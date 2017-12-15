import com.entities.*;

import static org.junit.Assert.*;

import com.exceptions.CarAlreadyParkedException;
import com.exceptions.CarNotParkedException;
import com.exceptions.ParkingLotFullException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.*;


public class ParkingLotTest {

    @Test
    public void validateIfCarCanBeParked() throws CarAlreadyParkedException, ParkingLotFullException {
        ParkingLot parkingLot = new ParkingLot(3, new ArrayList<>());
        int carNumber = 999;
        Car car = new Car(carNumber);
        assertTrue(parkingLot.park(car) > 0);
    }

    @Test
    public void validateIfTheCarCanBeUnparked() throws CarAlreadyParkedException, CarNotParkedException, ParkingLotFullException {
        ParkingLot parkingLot = new ParkingLot(3, new ArrayList<>());
        Car car = new Car(888);
        int tokenNumber = parkingLot.park(car);
        assertTrue(car.equals(parkingLot.unPark(tokenNumber)));
    }

    @Test(expected = CarNotParkedException.class)
    public void validateTheExceptionIsThrownIfRequestedCarIsNotThere() throws CarNotParkedException {
        ParkingLot parkingLot = new ParkingLot(3, new ArrayList<>());
        parkingLot.unPark(1000);
    }

    @Test(expected = CarNotParkedException.class)
    public void validateExceptionIsThrownUponMultipleUnparkingAttemptsOnSameCar() throws CarAlreadyParkedException, CarNotParkedException, ParkingLotFullException {
        ParkingLot parkingLot = new ParkingLot(3, new ArrayList<>());
        Car car = new Car(2000);
        int tokenNumber = parkingLot.park(car);
        assertTrue(car.equals(parkingLot.unPark(tokenNumber)));
        assertTrue(tokenNumber > 0);
        parkingLot.unPark(tokenNumber);
    }

    @Test(expected = CarAlreadyParkedException.class)
    public void validateIfExceptionIsThrownIfAParkedCarIsParkedWithoutUnparking() throws CarAlreadyParkedException, ParkingLotFullException {
        ParkingLot parkingLot = new ParkingLot(3, new ArrayList<>());
        Car car = new Car(2000);
        int tokenNumber = parkingLot.park(car);
        assertTrue(tokenNumber > 0);
        parkingLot.park(new Car(2000));
    }

    @Test(expected = ParkingLotFullException.class)
    public void validateIfExceptionIsThrownWhenParkingInAFullParkingLot() throws CarAlreadyParkedException, ParkingLotFullException {
        ParkingLot parkingLot = new ParkingLot(1, new ArrayList<>());
        Car car = new Car(2000);
        int tokenNumber = parkingLot.park(car);
        assertTrue(tokenNumber > 0);
        parkingLot.park(new Car(2003));
    }

    @Test
    public void validateIfSingleSubscriberIsNotifiedIfLotIsFull() throws CarAlreadyParkedException, ParkingLotFullException {
        ParkingLotUser parkingLotOwner = mock(NotifiableUser.class);
        ParkingLot parkingLot = new ParkingLot(1, Arrays.asList(new ParkingLotUser[]{parkingLotOwner}));
        assertTrue(parkingLot.park(new Car(888)) > 0);
        verify(parkingLotOwner, times(1)).notifyLotFull();
    }

    @Test
    public void validateIfAllConcernedPeopleAreNotifiedIfLotIsFull() throws CarAlreadyParkedException, ParkingLotFullException {
        ParkingLotUser parkingLotOwner = mock(ParkingLotUser.class);
        ParkingLotUser securityPersonnel = mock(ParkingLotUser.class);
        ParkingLotUser roadTrafficCop = mock(ParkingLotUser.class);
        ParkingLot parkingLot = new ParkingLot(1, Arrays.asList(new ParkingLotUser[]{parkingLotOwner, securityPersonnel, roadTrafficCop}));
        assertTrue(parkingLot.park(new Car(888)) > 0);
        verify(parkingLotOwner, times(1)).notifyLotFull();
        verify(securityPersonnel, times(1)).notifyLotFull();
        verify(roadTrafficCop, times(1)).notifyLotFull();
    }

}
