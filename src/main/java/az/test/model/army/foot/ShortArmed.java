package az.test.model.army.foot;

import az.test.battle.BattleInfo;

/**
 * 短兵
 */
public class ShortArmed extends Infantry {
	public ShortArmed(BattleInfo battleInfo) {
		super(battleInfo, "短兵");
		apBase = 40;
		dpBase = 40;
	}
}
