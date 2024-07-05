package az.test.model.item.restore;

import az.test.model.effect.EffectAction;
import az.test.model.item.BaseItem;
import az.test.model.item.Item;

public abstract class RestoreItem extends BaseItem implements Item {
	public boolean canRestoreHP = false;
	public boolean canRestoreMorale = false;

	public RestoreItem(int id, String name, EffectAction effectAction) {
		super(id, name, effectAction);
	}
}
