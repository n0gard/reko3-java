package az.test.model.army.ride;

import az.test.battle.BattleInfo;

public class GuardRide extends Rider {

	public GuardRide(BattleInfo battleInfo) {
		super(battleInfo, "近卫队");
		apBase = 80;
		dpBase = 60;
	}

	public GuardRide(HeavyRide army) {
		super(army.battle, "近卫队");
		apBase = 80;
		dpBase = 60;
		copyProperties(army);
	}

}
