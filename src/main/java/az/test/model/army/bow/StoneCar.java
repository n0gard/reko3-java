package az.test.model.army.bow;

import az.test.battle.BattleInfo;
import az.test.model.army.Corner3Attack;

public class StoneCar extends Bow {

	public StoneCar(BattleInfo battleInfo) {
		super(battleInfo);
		apBase = 80;
		dpBase = 50;
		moveAbility = 3;
		attackRangeList.add(Corner3Attack.getInstance());
	}

}
