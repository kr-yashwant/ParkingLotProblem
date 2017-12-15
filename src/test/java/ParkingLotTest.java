import com.entities.*;

import static org.junit.Assert.*;

import com.exceptions.CarAlreadyParkedException;
import com.exceptions.CarNotParkedException;
import com.exceptions.ParkingLotFullException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;


public class ParkingLotTest {

    @Test
    public void validateIfCarCanBeParked() throws CarAlreadyParkedException, ParkingLotFullException {
        ParkingLot parkingLot = new ParkingLot(3, new ArrayList<>());
        int carNumber = 999;
        Car car = new Car(carNumber);
        int expectedTokenNumber = car.hashCode() + 1 + 0;
        assertEquals(expectedTokenNumber, parkingLot.park(car));
    }

    @Test
    public void validateIfTheCarCanBeUnparked() throws CarAlreadyParkedException, CarNotParkedException, ParkingLotFullException {
        ParkingLot parkingLot = new ParkingLot(3, new ArrayList<>());
        Car car = new Car(888);
        int expectedTokenNumber = car.hashCode() + 1 + 0;
        int tokenNumber = parkingLot.park(car);
        assertEquals(expectedTokenNumber, tokenNumber);
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
        int expectedTokenNumber = car.hashCode() + 1 + 0;
        assertEquals(expectedTokenNumber, tokenNumber);
        assertTrue(car.equals(parkingLot.unPark(tokenNumber)));
        parkingLot.unPark(tokenNumber);
    }

    @Test(expected = CarAlreadyParkedException.class)
    public void validateIfExceptionIsThrownIfAParkedCarIsParkedWithoutUnparking() throws CarAlreadyParkedException, ParkingLotFullException {
        ParkingLot parkingLot = new ParkingLot(3, new ArrayList<>());
        Car car = new Car(2000);
        int tokenNumber = parkingLot.park(car);
        int expectedTokenNumber = car.hashCode() + 1 + 0;
        assertEquals(expectedTokenNumber, tokenNumber);
        parkingLot.park(new Car(2000));
    }

    @Test(expected = ParkingLotFullException.class)
    public void validateIfExceptionIsThrownWhenParkingInAFullParkingLot() throws CarAlreadyParkedException, ParkingLotFullException {
        ParkingLot parkingLot = new ParkingLot(1, new ArrayList<>());
        Car car = new Car(2000);
        int expectedTokenNumber = car.hashCode() + 1 + 0;
        int tokenNumber = parkingLot.park(car);
        assertEquals(expectedTokenNumber, tokenNumber);
        parkingLot.park(new Car(2003));
    }

    @Test
    public void validateIfSingleSubscriberIsNotifiedIfLotIsFull() throws CarAlreadyParkedException, ParkingLotFullException {
        ParkingLotUser parkingLotOwner = mock(NotifiableUser.class);
        ParkingLot parkingLot = new ParkingLot(1, Arrays.asList(new ParkingLotUser[]{parkingLotOwner}));
        Car car = new Car(2000);
        int expectedTokenNumber = car.hashCode() + 1 + 0;
        int tokenNumber = parkingLot.park(car);
        assertEquals(expectedTokenNumber, tokenNumber);
        verify(parkingLotOwner, times(1)).notifyLotFull();
    }

    @Test
    public void validateIfAllConcernedPeopleAreNotifiedIfLotIsFull() throws CarAlreadyParkedException, ParkingLotFullException {
        ParkingLotUser parkingLotOwner = mock(ParkingLotUser.class);
        ParkingLotUser securityPersonnel = mock(ParkingLotUser.class);
        ParkingLotUser roadTrafficCop = mock(ParkingLotUser.class);
        ParkingLot parkingLot = new ParkingLot(1, Arrays.asList(new ParkingLotUser[]{parkingLotOwner, securityPersonnel, roadTrafficCop}));
        Car car = new Car(2000);
        int expectedTokenNumber = car.hashCode() + 1 + 0;
        int tokenNumber = parkingLot.park(car);
        assertEquals(expectedTokenNumber, tokenNumber);
        verify(parkingLotOwner, times(1)).notifyLotFull();
        verify(securityPersonnel, times(1)).notifyLotFull();
        verify(roadTrafficCop, times(1)).notifyLotFull();
    }

    @Test
    public void validateIfAllConcernedPeopleAreNotifiedIfLotIsEmptyAgain() throws CarAlreadyParkedException, ParkingLotFullException, CarNotParkedException {
        ParkingLotUser parkingLotOwner = mock(ParkingLotUser.class);
        ParkingLotUser securityPersonnel = mock(ParkingLotUser.class);
        ParkingLotUser roadTrafficCop = mock(ParkingLotUser.class);

        List<ParkingLotUser> parkingNotifiableUsers = Arrays.asList(new ParkingLotUser[]{parkingLotOwner, securityPersonnel, roadTrafficCop});

        ParkingLot parkingLot = new ParkingLot(1, parkingNotifiableUsers);
        Car car = new Car(888);
        int expectedTokenNumber = car.hashCode() + 1 + 0;
        int parkedTokenNumber = parkingLot.park(car);
        assertEquals(expectedTokenNumber, parkedTokenNumber);

        verify(parkingLotOwner, times(1)).notifyLotFull();
        verify(securityPersonnel, times(1)).notifyLotFull();
        verify(roadTrafficCop, times(1)).notifyLotFull();

        assertEquals(car, parkingLot.unPark(parkedTokenNumber));

        verify(parkingLotOwner, times(1)).notifyLotAvailable();
        verify(securityPersonnel, times(1)).notifyLotAvailable();
        verify(roadTrafficCop, times(1)).notifyLotAvailable();


    }

    @Test
    public void validateIfAllConcernedPeopleAreNotNotifiedIfLotHasBeenEmptyBeforeUnparking() throws CarAlreadyParkedException, ParkingLotFullException, CarNotParkedException {
        ParkingLotUser parkingLotOwner = mock(ParkingLotUser.class);
        ParkingLotUser securityPersonnel = mock(ParkingLotUser.class);
        ParkingLotUser roadTrafficCop = mock(ParkingLotUser.class);

        List<ParkingLotUser> parkingNotifiableUsers = Arrays.asList(new ParkingLotUser[]{parkingLotOwner, securityPersonnel, roadTrafficCop});

        ParkingLot parkingLot = new ParkingLot(3, parkingNotifiableUsers);
        Car car = new Car(888);
        int expectedTokenNumber = car.hashCode() + 1 + 0;
        int parkedTokenNumber = parkingLot.park(car);
        assertEquals(expectedTokenNumber, parkedTokenNumber);

        verify(parkingLotOwner, times(0)).notifyLotFull();
        verify(securityPersonnel, times(0)).notifyLotFull();
        verify(roadTrafficCop, times(0)).notifyLotFull();

        assertEquals(car, parkingLot.unPark(parkedTokenNumber));

        verify(parkingLotOwner, times(0)).notifyLotAvailable();
        verify(securityPersonnel, times(0)).notifyLotAvailable();
        verify(roadTrafficCop, times(0)).notifyLotAvailable();


    }

}
