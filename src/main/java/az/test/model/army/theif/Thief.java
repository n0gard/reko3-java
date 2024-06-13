package az.test.model.army.theif;

import az.test.battle.BattleInfo;
import az.test.model.army.BotUnit;
import az.test.model.army.CrossAttack;

public class Thief extends BotUnit {

	public Thief(BattleInfo battleInfo) {
		super(battleInfo);
		armyHPBase = 800;
		armyHPInc = 40;
		moveAbility = 4;
		attackRangeList.add(CrossAttack.getInstance());
	}

}
