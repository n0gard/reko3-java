package az.test.model.item.consumption.upgrade;

import az.test.exception.BaseException;
import az.test.exception.InvalidItemUseTargetException;
import az.test.model.army.BaseUnit;
import az.test.model.army.theif.EvilThief;
import az.test.model.army.theif.JusticeThief;

public class XiaYiJingShen extends Upgrade {
    public XiaYiJingShen() {
        super(0x1a, "侠义精神");
    }

    @Override
    public void consume(BaseUnit player, BaseUnit... targets) throws BaseException {
        super.consume(player, targets);
        if (player instanceof EvilThief) {
            player = new JusticeThief((EvilThief) player);
        } else {
            throw new InvalidItemUseTargetException();
        }
    }
}
