package az.test.model.army.foot;

import az.test.battle.BattleInfo;
import az.test.model.army.BotUnit;
import az.test.model.army.CrossAttack;

/**
 * 步兵
 */
public abstract class Infantry extends BotUnit {

    public Infantry(BattleInfo battleInfo) {
        super(battleInfo);
        armyHPBase = 500;
        armyHPInc = 50;
        moveAbility = 4;
        attackRangeList.add(CrossAttack.getInstance());
    }

}
