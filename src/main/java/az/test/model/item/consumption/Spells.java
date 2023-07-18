package az.test.model.item.consumption;

import az.test.model.army.BaseUnit;
import az.test.model.army.other.MilitaryBand;
import az.test.model.army.other.TransportTeam;
import az.test.model.army.other.Wizard;
import az.test.model.item.Item;

public abstract class Spells extends Item {
	public double baseDamage;
	public int costMana;

	public Spells() {
		super();
		// TODO Auto-generated constructor stub
	}

	double reduceDamage(BaseUnit enemy, double currentDamage) {
		if (enemy instanceof MilitaryBand || enemy instanceof TransportTeam || enemy instanceof Wizard) {
			return currentDamage / 2;
		}
		return currentDamage;
	}

	int caculateMoralDamage(double finalDamage) {
		int moralDamage = 0;
		if (0 == finalDamage) {
			return moralDamage;
		}
		moralDamage = (int) (finalDamage / 100.0);
		if (0 == moralDamage && finalDamage > 0) {
			moralDamage = 1;
		}
		return moralDamage;
	}
}
