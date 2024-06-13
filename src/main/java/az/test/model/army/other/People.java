package az.test.model.army.other;

import az.test.battle.BattleInfo;
import az.test.model.army.BotUnit;

public class People extends BotUnit {

	public People(BattleInfo battleInfo) {
		super(battleInfo);
		armyHPBase = 500;
		armyHPInc = 50;
		apBase = 20;
		dpBase = 20;
		moveAbility = 4;
	}

}
