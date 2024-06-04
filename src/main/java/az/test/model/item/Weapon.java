package az.test.model.item;

public abstract class Weapon extends Item {
	public int apIncreasementPercentage;

	public Weapon(int apip) {
		super();
		this.apIncreasementPercentage = apip;
	}

}
