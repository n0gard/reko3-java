package az.test.model.item.consumption.upgrade;

import az.test.exception.BaseException;
import az.test.exception.InvalidItemUseTargetException;
import az.test.model.army.BaseUnit;
import az.test.model.army.theif.EvilThief;
import az.test.model.army.theif.MountainThief;

public class WuLaiJingShen extends Upgrade {
    public WuLaiJingShen() {
        super(0x19, "无赖精神");
    }

    @Override
    public void consume(BaseUnit player, BaseUnit... targets) throws BaseException {
        super.consume(player, targets);
        if (player instanceof MountainThief) {
            player = new EvilThief((MountainThief) player);
        } else {
            throw new InvalidItemUseTargetException();
        }
    }
}
