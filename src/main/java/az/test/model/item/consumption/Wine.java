package az.test.model.item.consumption;

import az.test.battle.BattleInfo;
import az.test.exception.BaseException;
import az.test.model.army.BaseUnit;
import az.test.model.strategy.defensive.RestoreArmyHP;
import az.test.model.strategy.defensive.RestoreArmyMorale;

public class Wine extends RestoreArmyMorale implements ConsumeItem {

	public Wine() {
		super(30);
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
	public void consume(BaseUnit player, BaseUnit... target) throws BaseException {
		super.restoreMorale(player, target);
		reduceItem(player);
	}

	@Override
	public void reduceItem(BaseUnit army) {
		army.items.remove(this);
	}
}
