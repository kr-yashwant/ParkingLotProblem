import com.entities.Car;
import com.entities.ParkingLot;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Random;

public class ParkingLotTest {

    private Random random = new Random();
    private ParkingLot parkingLot = new ParkingLot();

    @Test
    public void validateIfCarCanBeParked() {
        int carNumber = random.nextInt();
        Car car = new Car(carNumber);
        assertTrue(parkingLot.park(car));
    }

    @Test
    public void validateIfTheCarCanBeUnparked() {
        Car car = new Car(random.nextInt());
        parkingLot.park(car);
        assertTrue(parkingLot.unPark(car.getId()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateTheExceptionIsThrownIfRequestedCarIsNotThere() {
        parkingLot.unPark(random.nextInt());
    }

}
