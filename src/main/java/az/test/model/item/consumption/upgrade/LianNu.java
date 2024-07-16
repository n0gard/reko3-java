package az.test.model.item.consumption.upgrade;

import az.test.exception.BaseException;
import az.test.exception.InvalidItemUseTargetException;
import az.test.model.army.BaseUnit;
import az.test.model.army.bow.BowSoldier;
import az.test.model.army.bow.Liannu;

public class LianNu extends Upgrade {
    public LianNu() {
        super(0x15, "连弩");
    }

    @Override
    public void consume(BaseUnit player, BaseUnit... targets) throws BaseException {
        super.consume(player, targets);
        if (player instanceof BowSoldier) {
            player = new Liannu((BowSoldier) player);
        } else {
            throw new InvalidItemUseTargetException();
        }
    }
}
