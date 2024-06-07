package az.test.model.item;

public abstract class Weapon extends Item {
	public int apIncrementPercentage;

	public Weapon(int apIP) {
		super();
		this.apIncrementPercentage = apIP;
	}

}
