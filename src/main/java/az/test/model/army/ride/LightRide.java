package az.test.model.army.ride;

import az.test.battle.BattleInfo;

public class LightRide extends Rider {

	public LightRide(BattleInfo battleInfo) {
		super(battleInfo);
		apBase = 60;
		dpBase = 30;
	}

}
