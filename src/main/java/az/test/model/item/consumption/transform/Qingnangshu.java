package az.test.model.item.consumption.transform;

import az.test.exception.BaseException;
import az.test.exception.LiuBeiCannotTransformException;
import az.test.model.army.BaseUnit;
import az.test.model.army.other.TransportTeam;

public class Qingnangshu extends Transform {

    public Qingnangshu() {
        super(0x01);
    }

    @Override
    public void consume(BaseUnit player, BaseUnit... target) throws BaseException {
        for (BaseUnit t : target) {
            if (!consumptionCouldBeHappened(t)) {
                throw new LiuBeiCannotTransformException();
            }
        }
        player = new TransportTeam(player.battle, player);
    }
}
