package az.test.model.item.consumption;

import az.test.battle.BattleInfo;
import az.test.exception.BaseException;
import az.test.model.effect.RestoreArmyHP;
import az.test.model.item.BaseItem;
import az.test.model.strategy.defensive.RestoreArmyHP;
import az.test.model.army.BaseUnit;

public class Bean extends ConsumableItem {

	public Bean() {
		super(0x1E, "豆", new RestoreArmyHP(600));
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
	public void consume(BaseUnit player, BaseUnit... targets) throws BaseException {
		effectAction.effect(player, targets);
		reduceItem(player);
	}

}
