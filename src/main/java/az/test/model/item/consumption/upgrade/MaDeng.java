package az.test.model.item.consumption.upgrade;

import az.test.exception.BaseException;
import az.test.exception.InvalidItemUseTargetException;
import az.test.model.army.BaseUnit;
import az.test.model.army.ride.HeavyRide;
import az.test.model.army.ride.LightRide;

public class MaDeng extends Upgrade {
    public MaDeng() {
        super(0x17, "马镫");
    }

    @Override
    public void consume(BaseUnit player, BaseUnit... targets) throws BaseException {
        super.consume(player, targets);
        if (player instanceof LightRide) {
            player = new HeavyRide((LightRide) player);
        } else {
            throw new InvalidItemUseTargetException();
        }
    }
}
