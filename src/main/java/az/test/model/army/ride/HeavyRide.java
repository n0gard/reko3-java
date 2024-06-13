package az.test.model.army.ride;

import az.test.battle.BattleInfo;

public class HeavyRide extends Rider {

	public HeavyRide(BattleInfo battleInfo) {
		super(battleInfo);
		apBase = 70;
		dpBase = 50;
		moveAbility = 5;
	}

}
