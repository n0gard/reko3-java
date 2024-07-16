package az.test.model.army.ride;

import az.test.battle.BattleInfo;

public class HeavyRide extends Rider {

	public HeavyRide(BattleInfo battleInfo) {
		super(battleInfo, "重骑兵");
		apBase = 70;
		dpBase = 50;
		moveAbility = 5;
	}

	public HeavyRide(LightRide army) {
		super(army.battle, "重骑兵");
		apBase = 70;
		dpBase = 50;
		moveAbility = 5;
		copyProperties(army);
	}

}
