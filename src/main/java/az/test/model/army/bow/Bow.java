package az.test.model.army.bow;

import az.test.model.army.BaseUnit;

public abstract class Bow extends BaseUnit {

	public Bow() {
		super();
		armyHPBase = 500;
		armyHPInc = 40;
		moveAbility = 4;
	}

}
