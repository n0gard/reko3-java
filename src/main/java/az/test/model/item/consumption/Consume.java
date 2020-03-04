package az.test.model.item.consumption;

import az.test.battle.BattleInfo;
import az.test.model.army.BaseUnit;

public interface Consume {
	boolean consumptionCouldBeHappened(BattleInfo info);

	boolean consumptionCouldBeHappened(BaseUnit target);

	void consume(BaseUnit player, BaseUnit... target);
}
