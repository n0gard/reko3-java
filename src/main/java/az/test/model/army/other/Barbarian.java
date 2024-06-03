package az.test.model.army.other;

import az.test.model.army.BaseUnit;
// 异民族
public class Barbarian extends BaseUnit {

	public Barbarian() {
		super();
		armyHPBase = 700;
		armyHPInc = 50;
		apBase = 70;
		dpBase = 80;
		moveAbility = 5;
	}

}
