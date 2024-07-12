package az.test.model.item.consumption.spell.water;

import az.test.battle.BattleInfo;
import az.test.battle.enums.Weather;
import az.test.exception.BaseException;
import az.test.model.Consume;
import az.test.model.army.BaseUnit;
import az.test.model.effect.DamageArmy;
import az.test.model.item.consumption.spell.Spells;
import az.test.model.map.Bridge;
import az.test.model.map.Plain;

import java.util.function.Function;

public abstract class WaterSpells extends Spells implements Consume {

	public WaterSpells(int id, String name, int baseDamage) {
		super(id, name, new DamageArmy(name, baseDamage));
	}

	@Override
	public void consume(BaseUnit player, BaseUnit... targets) throws BaseException {
		Function<Integer, Integer> calculateExtra = damage -> {
			if (player.battle.weatherList.get(player.battle.map.getCurrentRoundNo() - 1) == Weather.RAIN) {
				return damage + damage / 4;
			} else {
				return damage;
			}
		};
		effection.effect(player, calculateExtra, targets);
	}

	@Override
	public boolean consumptionCouldBeHappened(BattleInfo info) {
		return true;
	}

	@Override
	public boolean consumptionCouldBeHappened(BaseUnit enemy) {
		return enemy.currentPositionMap instanceof Plain || enemy.currentPositionMap instanceof Bridge;
	}

}
