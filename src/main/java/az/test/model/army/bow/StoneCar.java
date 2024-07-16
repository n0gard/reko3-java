package az.test.model.army.bow;

import az.test.battle.BattleInfo;
import az.test.model.army.Corner3Attack;

public class StoneCar extends Bow {

	public StoneCar(BattleInfo battleInfo) {
		super(battleInfo, "投石车");
		apBase = 80;
		dpBase = 50;
		moveAbility = 3;
		attackRangeList.add(Corner3Attack.getInstance());
	}

	public StoneCar(Liannu army) {
		super(army.battle, "投石车");
		apBase = 80;
		dpBase = 50;
		moveAbility = 3;
		attackRangeList.add(Corner3Attack.getInstance());
		copyProperties(army);
	}

}
