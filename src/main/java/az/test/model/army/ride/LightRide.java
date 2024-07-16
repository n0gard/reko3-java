package az.test.model.army.ride;

import az.test.battle.BattleInfo;
import az.test.model.army.BaseUnit;

public class LightRide extends Rider {

	public LightRide(BattleInfo battleInfo) {
		super(battleInfo, "轻骑兵");
		apBase = 60;
		dpBase = 30;
	}

	public LightRide(BaseUnit transformFrom) {
		super(transformFrom.battle, "轻骑兵");
		apBase = 60;
		dpBase = 30;
		copyProperties(transformFrom);
	}

}
