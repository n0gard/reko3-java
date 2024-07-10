package az.test.model.army.foot;

import az.test.battle.BattleInfo;
import az.test.model.army.BaseUnit;
import az.test.model.army.CornerAttack;

/**
 * 长兵
 */
public class LongArmed extends Infantry {

	public LongArmed(BattleInfo battleInfo) {
		super(battleInfo, "长兵");
		apBase = 60;
		dpBase = 60;
		attackRangeList.add(CornerAttack.getInstance());
	}

	public LongArmed(BaseUnit player) {
		super(player.battle, "长兵");
		apBase = 60;
		dpBase = 60;
		attackRangeList.add(CornerAttack.getInstance());

	}

}
