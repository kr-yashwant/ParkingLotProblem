import com.entities.Car;
import com.entities.ParkingLot;
import com.entities.ParkingLotValet;
import com.exceptions.CarAlreadyParkedException;
import com.exceptions.ParkingLotFullException;
import javafx.util.Pair;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class ParkingLotValetTest {

    @Test
    public void valetShouldBeAbleToPark() throws CarAlreadyParkedException, ParkingLotFullException {
        Car car = mock(Car.class);
        ParkingLot firstParkingLot = mock(ParkingLot.class);
        when(firstParkingLot.park(any())).thenReturn(200);
        List<ParkingLot> parkingLots = Arrays.asList(new ParkingLot[]{firstParkingLot});
        ParkingLotValet valet = new ParkingLotValet(parkingLots);
        Pair<Integer, Integer> valetToken = valet.park(car);
        int tokenValue = valetToken.getValue();
        int lotIndex = valetToken.getKey();
        verify(parkingLots.get(lotIndex), times(1)).park(car);
        assertEquals(200, tokenValue);
    }

    @Test(expected = ParkingLotFullException.class)
    public void shouldThrowExceptionIfAllLotsAreFull() throws CarAlreadyParkedException, ParkingLotFullException {
        Car car = mock(Car.class);
        ParkingLot firstParkingLot = mock(ParkingLot.class);
        when(firstParkingLot.isFull()).thenReturn(true);
        List<ParkingLot> parkingLots = Arrays.asList(new ParkingLot[]{firstParkingLot});
        ParkingLotValet valet = new ParkingLotValet(parkingLots);
        valet.park(car);
    }

    @Test
    public void shouldParkAtNearestLotForHandicappedUsers() throws CarAlreadyParkedException, ParkingLotFullException {
        Car firstCar = mock(Car.class); Car secondCar = mock(Car.class); Car thirdCar = mock(Car.class);
        ParkingLot firstParkingLot = mock(ParkingLot.class);
        when(firstParkingLot.isFull()).thenReturn(false);
        ParkingLot secondParkingLot = mock(ParkingLot.class);
        when(secondParkingLot.isFull()).thenReturn(false);
        when(secondParkingLot.park(any())).thenReturn(200);
        ParkingLot thirdParkingLot = mock(ParkingLot.class);
        when(thirdParkingLot.isFull()).thenReturn(false);
        List<ParkingLot> parkingLots = Arrays.asList(new ParkingLot[]{firstParkingLot, secondParkingLot, thirdParkingLot});
        ParkingLotValet valet = new ParkingLotValet(parkingLots);
        Pair<Integer, Integer> valetToken = valet.parkForHandicappedUser(firstCar);
        int lotIndex = valetToken.getKey();
        assertEquals(0, lotIndex);
        valetToken = valet.parkForHandicappedUser(secondCar);
        lotIndex = valetToken.getKey();
        assertEquals(0, lotIndex);
        when(firstParkingLot.isFull()).thenReturn(true);
        valetToken = valet.parkForHandicappedUser(thirdCar);
        lotIndex = valetToken.getKey();
        assertEquals(1, lotIndex);
    }

    @Test
    public void shouldNotBotherAboutNearestLotForNotHandicappedUsers() throws CarAlreadyParkedException, ParkingLotFullException {
        Car firstCar = mock(Car.class); Car secondCar = mock(Car.class); Car thirdCar = mock(Car.class);
        ParkingLot firstParkingLot = mock(ParkingLot.class);
        when(firstParkingLot.isFull()).thenReturn(false);
        ParkingLot secondParkingLot = mock(ParkingLot.class);
        when(secondParkingLot.isFull()).thenReturn(false);
        when(secondParkingLot.park(any())).thenReturn(200);
        ParkingLot thirdParkingLot = mock(ParkingLot.class);
        when(thirdParkingLot.isFull()).thenReturn(false);
        List<ParkingLot> parkingLots = Arrays.asList(new ParkingLot[]{firstParkingLot, secondParkingLot, thirdParkingLot});
        ParkingLotValet valet = new ParkingLotValet(parkingLots);
        Pair<Integer, Integer> valetToken = valet.park(firstCar);
        int lotIndex = valetToken.getKey();
        assertEquals(0, lotIndex);
        valetToken = valet.park(secondCar);
        lotIndex = valetToken.getKey();
        assertEquals(1, lotIndex);
        valetToken = valet.park(thirdCar);
        lotIndex = valetToken.getKey();
        assertEquals(2, lotIndex);
    }
}
