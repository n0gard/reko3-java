package az.test.model.effect;

import az.test.exception.BaseException;
import az.test.model.army.BaseUnit;
import az.test.model.army.foot.LongArmed;
import az.test.model.army.foot.ShortArmed;

import java.util.function.Function;

public class UpgradeArmy extends Effection {

    public UpgradeArmy() {
    }

    @Override
    public void effect(BaseUnit player, Function<Integer, Integer> extra, BaseUnit... targets) throws BaseException {
        BaseUnit t = targets[0];
        if (t instanceof ShortArmed) {
            t = new LongArmed(t);
        }
    }
}
