package az.test.model.army.theif;

import az.test.battle.BattleInfo;
import az.test.model.army.BaseUnit;
import az.test.model.army.CrossAttack;

public class Thief extends BaseUnit {

	public Thief(BattleInfo battleInfo, String armyName) {
		super(battleInfo, armyName);
		armyHPBase = 800;
		armyHPInc = 40;
		moveAbility = 4;
		attackRangeList.add(CrossAttack.getInstance());
	}

}
