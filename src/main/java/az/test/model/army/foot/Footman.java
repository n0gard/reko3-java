package az.test.model.army.foot;

import az.test.model.army.BaseUnit;

public class Footman extends BaseUnit {

	public Footman() {
		super();
		armyHPBase = 500;
		armyHPInc = 50;
		moveAbility = 4;
	}

}
