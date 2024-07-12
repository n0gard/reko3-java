package az.test.model.item.consumption.spell.stone;

import az.test.battle.BattleInfo;
import az.test.exception.BaseException;
import az.test.model.Consume;
import az.test.model.army.BaseUnit;
import az.test.model.effect.DamageArmy;
import az.test.model.item.consumption.spell.Spells;
import az.test.model.map.Mountain;
import az.test.model.map.Wasteland;

public abstract class StoneSpells extends Spells implements Consume {

	public StoneSpells(int id, String name, int baseDamage) {
		super(id, name, new DamageArmy(name, baseDamage));
	}

	@Override
	public void consume(BaseUnit player, BaseUnit... targets) throws BaseException {
		effection.effect(player, null, targets);
	}

	@Override
	public boolean consumptionCouldBeHappened(BattleInfo info) {
		return true;
	}

	@Override
	public boolean consumptionCouldBeHappened(BaseUnit target) {
		return target.currentPositionMap instanceof Wasteland || target.currentPositionMap instanceof Mountain;
	}

}
