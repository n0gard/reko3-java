package az.test.model.army.foot;

import az.test.battle.BattleInfo;
import az.test.model.army.CrossAttack;

public class ShortArmed extends Infantry {

	public ShortArmed(BattleInfo battleInfo) {
		super(battleInfo);
		apBase = 40;
		dpBase = 40;
	}

}
