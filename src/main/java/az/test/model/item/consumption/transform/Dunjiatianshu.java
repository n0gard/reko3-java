package az.test.model.item.consumption.transform;

import az.test.exception.BaseException;
import az.test.exception.LiuBeiCannotTransformException;
import az.test.model.army.BaseUnit;
import az.test.model.army.other.Wizard;

public class Dunjiatianshu extends Transform {

    public Dunjiatianshu() {
        super(0x00);
    }

    @Override
    public void consume(BaseUnit player, BaseUnit... target) throws BaseException {
        effectAction.effect(player, target);
        player = new Wizard(player.battle, player);
    }
}
