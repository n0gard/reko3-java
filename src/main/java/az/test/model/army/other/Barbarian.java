package az.test.model.army.other;

import az.test.battle.BattleInfo;
import az.test.model.army.BaseUnit;
import az.test.model.army.BotUnit;
import az.test.model.army.CornerAttack;
import az.test.model.army.CrossAttack;

/**
 * 异民族
 */
public class Barbarian extends BotUnit {

    public Barbarian(BattleInfo battleInfo) {
        super(battleInfo);
        armyHPBase = 700;
        armyHPInc = 50;
        apBase = 70;
        dpBase = 80;
        moveAbility = 5;
        attackRangeList.add(CrossAttack.getInstance());
        attackRangeList.add(CornerAttack.getInstance());
    }

}
