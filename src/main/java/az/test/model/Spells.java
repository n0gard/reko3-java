package az.test.model;

import az.test.model.army.BaseUnit;
import az.test.model.army.other.MilitaryBand;
import az.test.model.army.other.TransportTeam;
import az.test.model.army.other.Wizard;

public abstract class Spells {
	public int baseDamage;
	public int costMana;

	public Spells() {
		super();
	}

	public int reduceDamage(BaseUnit enemy, int currentDamage) {
		if (enemy instanceof MilitaryBand || enemy instanceof TransportTeam || enemy instanceof Wizard) {
			return currentDamage / 2;
		}
		return currentDamage;
	}

	public int calculateMoralDamage(int finalDamage) {
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
