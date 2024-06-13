package az.test.model.army.other;

import az.test.battle.BattleInfo;
import az.test.model.army.BotUnit;
import az.test.model.army.CrossAttack;

public class Wizard extends BotUnit {

	public Wizard(BattleInfo battleInfo) {
		super(battleInfo);
		armyHPBase = 300;
		armyHPInc = 50;
		apBase = 20;
		dpBase = 20;
		moveAbility = 4;
		attackRangeList.add(CrossAttack.getInstance());
	}

}
