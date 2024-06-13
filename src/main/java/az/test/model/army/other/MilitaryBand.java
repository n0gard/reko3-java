package az.test.model.army.other;

import az.test.battle.BattleInfo;
import az.test.model.army.BotUnit;
import az.test.model.army.CrossAttack;

public class MilitaryBand extends BotUnit {

	public MilitaryBand(BattleInfo battleInfo) {
		super(battleInfo);
		armyHPBase = 300;
		armyHPInc = 40;
		apBase = 20;
		dpBase = 20;
		moveAbility = 4;
		attackRangeList.add(CrossAttack.getInstance());
	}

}
