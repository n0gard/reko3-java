package az.test.model.item.consumption;

import az.test.model.army.BaseUnit;
import az.test.model.army.other.MilitaryBand;
import az.test.model.army.other.TransportTeam;
import az.test.model.army.other.Wizard;
import az.test.model.item.Item;

public abstract class Spells extends Item {
	public int baseDamage;
	public int costMana;

	public Spells() {
		super();
	}

	int reduceDamage(BaseUnit enemy, int currentDamage) {
		if (enemy instanceof MilitaryBand || enemy instanceof TransportTeam || enemy instanceof Wizard) {
			return currentDamage / 2;
		}
		return currentDamage;
	}

	int calculateMoralDamage(int finalDamage) {
		int moralDamage = 0;
		if (0 == finalDamage) {
			return moralDamage;
		}
		moralDamage = finalDamage / 100;
		if (0 == moralDamage && finalDamage > 0) {
			moralDamage = 1;
		}
		return moralDamage;
	}
}
