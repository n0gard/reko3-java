package az.test.model.army.ride;

import az.test.battle.BattleInfo;

public class GuardRide extends Rider {

	public GuardRide(BattleInfo battleInfo) {
		super(battleInfo);
		apBase = 80;
		dpBase = 60;
	}

}
