package az.test.model.item.consumption.upgrade;

import az.test.exception.BaseException;
import az.test.exception.InvalidItemUseTargetException;
import az.test.model.army.BaseUnit;
import az.test.model.army.foot.LongArmed;
import az.test.model.army.foot.ShortArmed;

public class ChangQiang extends Upgrade {
    public ChangQiang() {
        super(0x13, "长枪");
    }

    @Override
    public void consume(BaseUnit player, BaseUnit... targets) throws BaseException {
        super.consume(player, targets);
        if (player instanceof ShortArmed) {
            player = new LongArmed((ShortArmed) player);
        } else {
            throw new InvalidItemUseTargetException();
        }
    }
}
