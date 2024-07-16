package az.test.model.effect;

import az.test.exception.BaseException;
import az.test.model.army.BaseUnit;

import java.util.function.Function;

public class UpgradeArmy extends Effection {

    public UpgradeArmy() {
    }

    @Override
    public void effect(BaseUnit player, Function<Integer, Integer> extra, BaseUnit... targets) throws BaseException {

    }
}
