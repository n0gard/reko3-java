package az.test.model.army.bow;

import az.test.battle.BattleInfo;
import az.test.model.army.Corner2Attack;

public class Liannu extends Bow {

	public Liannu(BattleInfo battleInfo) {
		super(battleInfo, "连弩兵");
		apBase = 60;
		dpBase = 40;
		attackRangeList.add(Corner2Attack.getInstance());
	}

	public Liannu(BowSoldier army) {
		super(army.battle, "连弩兵");
		apBase = 60;
		dpBase = 40;
		attackRangeList.add(Corner2Attack.getInstance());
		copyProperties(army);
	}

}
