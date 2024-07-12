package az.test.model.item.consumption.transform;

import az.test.exception.BaseException;
import az.test.model.army.BaseUnit;
import az.test.model.army.other.Wizard;

public class Dunjiatianshu extends Transform {

    public Dunjiatianshu() {
        super(0x00, "遁甲天书");
    }

    @Override
    public void consume(BaseUnit player, BaseUnit... target) throws BaseException {
        effection.effect(player, null, target);
        player = new Wizard(player.battle, player);
    }
}
