package az.test.model.army.foot;

import az.test.battle.BattleInfo;
import az.test.model.army.BaseUnit;
import az.test.model.army.CrossAttack;

/**
 * 步兵
 */
public abstract class Infantry extends BaseUnit {

    public Infantry(BattleInfo battleInfo, String name) {
        super(battleInfo, name);
        armyHPBase = 500;
        armyHPInc = 50;
        moveAbility = 4;
        attackRangeList.add(CrossAttack.getInstance());
    }

}
