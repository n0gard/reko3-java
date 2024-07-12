package az.test.model.army.ride;

import az.test.battle.BattleInfo;
import az.test.model.army.BaseUnit;
import az.test.model.army.CrossAttack;

public abstract class Rider extends BaseUnit {

	public Rider(BattleInfo battleInfo, String armyName) {
		super(battleInfo, armyName);
		armyHPBase = 500;
		armyHPInc = 60;
		moveAbility = 6;
		attackRangeList.add(CrossAttack.getInstance());
	}

}
