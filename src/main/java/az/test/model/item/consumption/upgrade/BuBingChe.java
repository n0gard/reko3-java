package az.test.model.item.consumption.upgrade;

import az.test.exception.BaseException;
import az.test.exception.InvalidItemUseTargetException;
import az.test.model.army.BaseUnit;
import az.test.model.army.foot.Chariot;
import az.test.model.army.foot.LongArmed;

public class BuBingChe extends Upgrade {
    public BuBingChe() {
        super(0x14, "步兵车");
    }

    @Override
    public void consume(BaseUnit player, BaseUnit... targets) throws BaseException {
        super.consume(player, targets);
        if (player instanceof LongArmed) {
            player = new Chariot((LongArmed) player);
        } else {
            throw new InvalidItemUseTargetException();
        }
    }
}
