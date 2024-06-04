package az.test.model.army.ride;

import az.test.model.army.BaseUnit;

public abstract class Rider extends BaseUnit {

	public Rider() {
		super();
		armyHPBase = 500;
		armyHPInc = 60;
		moveAbility = 6;
	}

}
