package az.test.model.army.other;

import az.test.battle.BattleInfo;
import az.test.model.army.BaseUnit;
import az.test.model.army.BotUnit;
import az.test.model.army.CrossAttack;

/**
 * 妖术师
 */
public class Wizard extends BotUnit {

	public Wizard(BattleInfo battleInfo) {
		super(battleInfo, "妖术师");
		armyHPBase = 300;
		armyHPInc = 50;
		apBase = 20;
		dpBase = 20;
		moveAbility = 4;
		attackRangeList.add(CrossAttack.getInstance());
	}

	public Wizard(BattleInfo battleInfo, BaseUnit transformFrom) {
		super(battleInfo, "妖术师");
		armyHPBase = 300;
		armyHPInc = 50;
		apBase = 20;
		dpBase = 20;
		moveAbility = 4;
		attackRangeList.add(CrossAttack.getInstance());
		copyProperties(transformFrom);
	}

}
