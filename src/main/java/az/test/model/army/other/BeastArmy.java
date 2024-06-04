package az.test.model.army.other;

import az.test.model.army.BaseUnit;
// 猛兽兵团
public class BeastArmy extends BaseUnit {

	public BeastArmy() {
		super();
		armyHPBase = 400;
		armyHPInc = 50;
		apBase = 80;
		dpBase = 30;
		moveAbility = 4;
	}

}
