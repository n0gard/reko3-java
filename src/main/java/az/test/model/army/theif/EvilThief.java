package az.test.model.army.theif;

import az.test.battle.BattleInfo;
import az.test.model.army.CornerAttack;
import az.test.model.army.CrossAttack;

public class EvilThief extends Thief {

	public EvilThief(BattleInfo battleInfo) {
		super(battleInfo);
		apBase = 60;
		dpBase = 50;
		attackRangeList.add(CornerAttack.getInstance());
	}

}
