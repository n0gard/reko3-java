package az.test.model.army.foot;

import az.test.battle.BattleInfo;

public class Chariot extends Infantry {

	public Chariot(BattleInfo battleInfo) {
		super(battleInfo);
		apBase = 60;
		dpBase = 80;
		moveAbility = 5;
	}

}
