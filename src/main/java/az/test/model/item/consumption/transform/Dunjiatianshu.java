package az.test.model.item.consumption.transform;

import az.test.exception.BaseException;
import az.test.model.army.BaseUnit;
import az.test.model.army.other.Wizard;

public class Dunjiatianshu extends Transform {

    @Override
    public void consume(BaseUnit player, BaseUnit... target) throws BaseException {
        Wizard wizard = new Wizard(player.battle);
        pla
    }
}
