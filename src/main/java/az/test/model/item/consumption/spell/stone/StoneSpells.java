package az.test.model.item.consumption.spell.stone;

import az.test.battle.BattleInfo;
import az.test.battle.enums.Weather;
import az.test.exception.BaseException;
import az.test.model.Consume;
import az.test.model.army.BaseUnit;
import az.test.model.effect.DamageArmy;
import az.test.model.item.consumption.spell.Spells;
import az.test.model.map.Bridge;
import az.test.model.map.Mountain;
import az.test.model.map.Plain;
import az.test.model.map.Wasteland;

import java.util.function.Function;

public abstract class StoneSpells extends Spells implements Consume {

	public StoneSpells(int id, String name, int baseDamage) {
		super(id, name, new DamageArmy(name, baseDamage));
	}

	@Override
	public void consume(BaseUnit player, BaseUnit... targets) throws BaseException {
		effectAction.effect(player, null, targets);
	}

	@Override
	public boolean consumptionCouldBeHappened(BattleInfo info) {
		return true;
	}

	@Override
	public boolean consumptionCouldBeHappened(BaseUnit enemy) {
		return enemy.currentPositionMap instanceof Wasteland || enemy.currentPositionMap instanceof Mountain;
	}

}
