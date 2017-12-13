import com.entities.Car;
import com.entities.ParkingLot;
import org.junit.Assert;
import org.junit.Test;

/**
 * Problem statement:
 * As a driver I want to be able to park my car so that
 * I can catch a flight
 */
class ParkingLotTest {

    @Test
    public void validateIfParkingLotCanAccommodate() {
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car();
        Assert.assertTrue(parkingLot.accommodate(car));
    }

}
