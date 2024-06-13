package az.test.model.army.other;

import az.test.battle.BattleInfo;
import az.test.model.army.BotUnit;
import az.test.model.army.CornerAttack;
import az.test.model.army.CrossAttack;

/**
 * 猛兽兵团
 */
public class BeastArmy extends BotUnit {

    public BeastArmy(BattleInfo battleInfo) {
        super(battleInfo);
        armyHPBase = 400;
        armyHPInc = 50;
        apBase = 80;
        dpBase = 30;
        moveAbility = 4;
        attackRangeList.add(CrossAttack.getInstance());
        attackRangeList.add(CornerAttack.getInstance());
    }

}
