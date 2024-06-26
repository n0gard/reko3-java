package az.test.model.army.ride;

import az.test.battle.BattleInfo;
import az.test.model.army.BotUnit;
import az.test.model.army.CrossAttack;

public abstract class Rider extends BotUnit {

	public Rider(BattleInfo battleInfo) {
		super(battleInfo);
		armyHPBase = 500;
		armyHPInc = 60;
		moveAbility = 6;
		attackRangeList.add(CrossAttack.getInstance());
	}

}
