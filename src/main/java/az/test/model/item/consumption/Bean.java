package az.test.model.item.consumption;

import az.test.battle.BattleInfo;
import az.test.model.army.BaseUnit;

public class Bean extends Food implements Consume {

	@Override
	public boolean consumptionCouldBeHappened(BattleInfo info) {
		return true;
	}

	@Override
	public boolean consumptionCouldBeHappened(BaseUnit target) {
		return target.currentArmyHP < target.initMaxArmyHP();
	}

	@Override
	public void consume(BaseUnit player, BaseUnit... target) {
		// TODO Auto-generated method stub

	}

}
