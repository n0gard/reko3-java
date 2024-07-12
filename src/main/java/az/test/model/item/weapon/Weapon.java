package az.test.model.item.weapon;

import az.test.model.item.BaseItem;

public abstract class Weapon extends BaseItem {
	public int apIncrementPercentage;

	public Weapon(int id, String name, int apIP) {
        super(id, name);
        this.apIncrementPercentage = apIP;
	}

}
