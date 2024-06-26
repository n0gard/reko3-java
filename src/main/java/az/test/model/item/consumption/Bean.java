package az.test.model.item.consumption;

import az.test.battle.BattleInfo;
import az.test.exception.BaseException;
import az.test.model.strategy.defensive.RestoreArmyHP;
import az.test.model.army.BaseUnit;

public class Bean extends RestoreArmyHP implements ConsumeItem {

	public Bean() {
		super(600);
	}

	@Override
	public boolean consumptionCouldBeHappened(BattleInfo info) {
		return true;
	}

	@Override
	public boolean consumptionCouldBeHappened(BaseUnit target) {
		return target.currentArmyHP < target.calculateMaxArmyHP();
	}

	@Override
	public void consume(BaseUnit player, BaseUnit... target) throws BaseException {
		super.restoreHP(player, target);
		reduceItem(player);
	}

	@Override
	public void reduceItem(BaseUnit army) {
		army.items.remove(this);
	}
}
