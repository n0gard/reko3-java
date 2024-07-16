package az.test.model.army.foot;

import az.test.battle.BattleInfo;

/**
 * 步兵车
 */
public class Chariot extends Infantry {

	public Chariot(BattleInfo battleInfo) {
		super(battleInfo, "步兵车");
		apBase = 60;
		dpBase = 80;
		moveAbility = 5;
	}

	public Chariot(LongArmed army) {
		super(army.battle, "步兵车");
		apBase = 60;
		dpBase = 80;
		moveAbility = 5;
		copyProperties(army);
	}

}
