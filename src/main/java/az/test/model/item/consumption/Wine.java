package az.test.model.item.consumption;

import az.test.battle.BattleInfo;
import az.test.exception.BaseException;
import az.test.model.army.BaseUnit;
import az.test.model.item.BaseItem;
import az.test.model.effect.RestoreArmyMorale;

public class Wine extends ConsumableItem {

	public Wine() {
		super(0x1B, "酒", new RestoreArmyMorale(30));
	}

	@Override
	public boolean consumptionCouldBeHappened(BattleInfo info) {
		return true;
	}

	@Override
	public boolean consumptionCouldBeHappened(BaseUnit target) {
		return target.currentMorale < 100;
	}

	@Override
	public void consume(BaseUnit player, BaseUnit... targets) throws BaseException {
		effectAction.effect(player, targets);
		reduceItem(player);
	}

	@Override
	public void reduceItem(BaseUnit army) {
		army.items.remove(this);
	}
}
