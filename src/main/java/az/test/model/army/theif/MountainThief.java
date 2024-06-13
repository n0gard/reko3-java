package az.test.model.army.theif;

import az.test.battle.BattleInfo;
import az.test.model.army.CrossAttack;

public class MountainThief extends Thief {

	public MountainThief(BattleInfo battleInfo) {
		super(battleInfo);
		apBase = 50;
		dpBase = 40;
	}

}
