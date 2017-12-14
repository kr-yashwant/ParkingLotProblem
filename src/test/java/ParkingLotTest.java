import com.entities.Car;
import com.entities.ParkingLot;
import static org.junit.Assert.*;
import org.junit.Test;


public class ParkingLotTest {

    @Test
    public void validateIfCarCanBeParked() {
        ParkingLot parkingLot = new ParkingLot();
        int carNumber = 999;
        Car car = new Car(carNumber);
        assertTrue(parkingLot.park(car));
    }

    @Test
    public void validateIfTheCarCanBeUnparked() {
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car(888);
        parkingLot.park(car);
        assertTrue(parkingLot.unPark(car.getId()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateTheExceptionIsThrownIfRequestedCarIsNotThere() {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.unPark(1000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionIsThrownUponMultipleUnparkingAttempts() {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.park(new Car(2000));
        parkingLot.unPark(2000);
        parkingLot.unPark(2000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateIfAParkedCarCanBeParkedWithoutUnparking() {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.park(new Car(2000));
        parkingLot.park(new Car(2000));
    }

}
