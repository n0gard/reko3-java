package az.test.model.army.bow;

import az.test.battle.BattleInfo;

public class BowSoldier extends Bow {

	public BowSoldier(BattleInfo battleInfo) {
		super(battleInfo);
		apBase = 30;
		dpBase = 40;
	}

}
