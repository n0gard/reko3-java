package az.test.model.item.consumption.transform;

import az.test.exception.BaseException;
import az.test.model.army.BaseUnit;
import az.test.model.army.foot.ShortArmed;

public class FencingGuide extends Transform {
    public FencingGuide(int id, String name) {
        super(0x10, "剑术指南书");
    }

    @Override
    public void consume(BaseUnit player, BaseUnit... target) throws BaseException {
        effection.effect(player, null, target);
        player = new ShortArmed(player);
    }
}
