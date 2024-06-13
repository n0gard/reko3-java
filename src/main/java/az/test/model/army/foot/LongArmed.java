package az.test.model.army.foot;

import az.test.battle.BattleInfo;
import az.test.model.army.CornerAttack;

public class LongArmed extends Infantry {

	public LongArmed(BattleInfo battleInfo) {
		super(battleInfo);
		apBase = 60;
		dpBase = 60;
		attackRangeList.add(CornerAttack.getInstance());
	}

}
