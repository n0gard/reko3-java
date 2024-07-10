package az.test.model.item.consumption.spell;

import az.test.model.effect.EffectAction;
import az.test.model.item.consumption.ConsumableItem;

public abstract class Spells extends ConsumableItem {
	public Spells(int id, String name, EffectAction effectAction) {
		super(id, name, effectAction);
	}
}
