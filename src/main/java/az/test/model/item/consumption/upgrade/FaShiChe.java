package az.test.model.item.consumption.upgrade;

import az.test.exception.BaseException;
import az.test.exception.InvalidItemUseTargetException;
import az.test.model.army.BaseUnit;
import az.test.model.army.bow.Liannu;
import az.test.model.army.bow.StoneCar;

public class FaShiChe extends Upgrade {
    public FaShiChe() {
        super(0x16, "发石车");
    }

    @Override
    public void consume(BaseUnit player, BaseUnit... targets) throws BaseException {
        super.consume(player, targets);
        if (player instanceof Liannu) {
            player = new StoneCar((Liannu) player);
        } else {
            throw new InvalidItemUseTargetException();
        }
    }
}
