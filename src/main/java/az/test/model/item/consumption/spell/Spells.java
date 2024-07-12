package az.test.model.item.consumption.spell;

import az.test.model.effect.Effection;
import az.test.model.item.consumption.ConsumableItem;

public abstract class Spells extends ConsumableItem {
	public Spells(int id, String name, Effection effection) {
		super(id, name, effection);
	}
}
