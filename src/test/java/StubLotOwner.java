import com.entities.ParkingLotUser;

public class StubLotOwner implements ParkingLotUser {


    private boolean status;

    @Override
    public void notifyLotFull() {
        this.status=true;
    }

    @Override
    public boolean isNotified() {
        return status;
    }
}
