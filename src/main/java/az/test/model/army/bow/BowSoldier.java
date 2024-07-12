package az.test.model.army.bow;

import az.test.battle.BattleInfo;
import az.test.model.army.BaseUnit;

public class BowSoldier extends Bow {

	public BowSoldier(BattleInfo battleInfo) {
		super(battleInfo, "弓兵队");
		apBase = 30;
		dpBase = 40;
	}

	public BowSoldier(BaseUnit transformFrom) {
		super(transformFrom.battle, "弓兵队");
		apBase = 30;
		dpBase = 40;
		copyProperties(transformFrom);
	}
}
