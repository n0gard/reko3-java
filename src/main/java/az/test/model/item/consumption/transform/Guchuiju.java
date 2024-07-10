package az.test.model.item.consumption.transform;

import az.test.exception.BaseException;
import az.test.exception.LiuBeiCannotTransformException;
import az.test.model.army.BaseUnit;
import az.test.model.army.other.MilitaryBand;

public class Guchuiju extends Transform {

    public Guchuiju() {
        super(0x02, "鼓吹具");
    }

    @Override
    public void consume(BaseUnit player, BaseUnit... target) throws BaseException {
        effectAction.effect(player, null, target);
        player = new MilitaryBand(player);
    }
}
