package az.test.model.army.bow;

import az.test.battle.BattleInfo;
import az.test.model.army.Corner2Attack;

public class Liannu extends Bow {

	public Liannu(BattleInfo battleInfo) {
		super(battleInfo);
		apBase = 60;
		dpBase = 40;
		attackRangeList.add(Corner2Attack.getInstance());
	}

}
