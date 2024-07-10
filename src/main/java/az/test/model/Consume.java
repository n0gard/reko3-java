package az.test.model;

import az.test.battle.BattleInfo;
import az.test.exception.BaseException;
import az.test.model.army.BaseUnit;

public interface Consume {

    /** 判断战场环境是否可以执行消费（使用物品或是执行策略）*/
    boolean consumptionCouldBeHappened(BattleInfo info);

    /** 判断我方单位和目标单位是否可以执行消费（使用物品或是执行策略）*/
    boolean consumptionCouldBeHappened(BaseUnit target);

    /** 执行消费（使用物品或是执行策略）*/
    void consume(BaseUnit player, BaseUnit... targets) throws BaseException;
}
