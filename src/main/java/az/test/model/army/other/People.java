package az.test.model.army.other;

import az.test.battle.BattleInfo;
import az.test.model.army.BaseUnit;

public class People extends BaseUnit {

	public People(BattleInfo battleInfo) {
		super(battleInfo, "民众");
		armyHPBase = 500;
		armyHPInc = 50;
		apBase = 20;
		dpBase = 20;
		moveAbility = 4;
	}

}
