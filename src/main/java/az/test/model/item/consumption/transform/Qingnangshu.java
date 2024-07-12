package az.test.model.item.consumption.transform;

import az.test.exception.BaseException;
import az.test.model.army.BaseUnit;
import az.test.model.army.other.TransportTeam;

public class Qingnangshu extends Transform {

    public Qingnangshu() {
        super(0x01, "青囊书");
    }

    @Override
    public void consume(BaseUnit player, BaseUnit... target) throws BaseException {
        effection.effect(player, null, target);
        player = new TransportTeam(player);
    }
}
