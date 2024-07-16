package az.test.model.army.other;

import az.test.battle.BattleInfo;
import az.test.model.army.BaseUnit;
import az.test.model.army.CornerAttack;
import az.test.model.army.CrossAttack;

/**
 * 猛兽兵团
 */
public class BeastArmy extends BaseUnit {

    public BeastArmy(BattleInfo battleInfo) {
        super(battleInfo, "猛兽兵团");
        armyHPBase = 400;
        armyHPInc = 50;
        apBase = 80;
        dpBase = 30;
        moveAbility = 4;
        attackRangeList.add(CrossAttack.getInstance());
        attackRangeList.add(CornerAttack.getInstance());
    }

}
