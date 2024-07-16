package az.test.model.item.consumption.upgrade;

import az.test.exception.BaseException;
import az.test.exception.InvalidItemUseTargetException;
import az.test.model.army.BaseUnit;
import az.test.model.army.ride.GuardRide;
import az.test.model.army.ride.HeavyRide;

public class JinWeiDeng extends Upgrade {
    public JinWeiDeng() {
        super(0x18, "近卫镫");
    }

    @Override
    public void consume(BaseUnit player, BaseUnit... targets) throws BaseException {
        super.consume(player, targets);
        if (player instanceof HeavyRide) {
            player = new GuardRide((HeavyRide) player);
        } else {
            throw new InvalidItemUseTargetException();
        }
    }
}
