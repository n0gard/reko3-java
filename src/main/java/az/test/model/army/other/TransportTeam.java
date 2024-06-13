package az.test.model.army.other;

import az.test.battle.BattleInfo;
import az.test.model.army.BotUnit;
import az.test.model.army.CornerAttack;
import az.test.model.army.CrossAttack;

public class TransportTeam extends BotUnit {

	public TransportTeam(BattleInfo battleInfo) {
		super(battleInfo);
		armyHPBase = 300;
		armyHPInc = 40;
		apBase = 20;
		dpBase = 20;
		moveAbility = 3;
		attackRangeList.add(CrossAttack.getInstance());
		attackRangeList.add(CornerAttack.getInstance());
	}

}
