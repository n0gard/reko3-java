package az.test.model.item.weapon;

import az.test.model.item.BaseItem;
import az.test.model.item.Item;

public abstract class Weapon extends BaseItem {
	public int apIncrementPercentage;

	public Weapon(int id, String name, int apIP) {
        super(id, name, null);
        this.apIncrementPercentage = apIP;
	}

}
