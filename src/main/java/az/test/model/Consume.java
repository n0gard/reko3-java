package az.test.model;

import az.test.battle.BattleInfo;
import az.test.exception.BaseException;
import az.test.model.army.BaseUnit;

public interface Consume {

    boolean consumptionCouldBeHappened(BattleInfo info);

    boolean consumptionCouldBeHappened(BaseUnit target);

    void consume(BaseUnit player, BaseUnit... target) throws BaseException;
}
