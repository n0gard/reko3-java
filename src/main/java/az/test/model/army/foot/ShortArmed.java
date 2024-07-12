package az.test.model.army.foot;

import az.test.battle.BattleInfo;
import az.test.model.army.BaseUnit;

/**
 * 短兵
 */
public class ShortArmed extends Infantry {
	public ShortArmed(BattleInfo battleInfo) {
		super(battleInfo, "短兵");
		apBase = 40;
		dpBase = 40;
	}

	public ShortArmed(BaseUnit transformFrom) {
		super(transformFrom.battle, "短兵");
		apBase = 40;
		dpBase = 40;
		copyProperties(transformFrom);
	}

}
