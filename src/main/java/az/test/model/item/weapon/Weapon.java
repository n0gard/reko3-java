package az.test.model.item.weapon;

import az.test.model.item.Item;

public abstract class Weapon extends Item {
	public int apIncrementPercentage;

	public Weapon(int apIP) {
		super();
		this.apIncrementPercentage = apIP;
	}

}
