package az.test.model.item.consumption;

import az.test.battle.BattleInfo;
import az.test.battle.enums.Weather;
import az.test.model.army.BaseUnit;
import az.test.model.map.Forest;
import az.test.model.map.Grassland;
import az.test.model.map.Plain;
import az.test.util.RandomHelper;

public class FireSpells extends Spells implements Consume {

	public FireSpells(String name, double baseDamage) {
		super();
		super.name = name;
		super.baseDamage = baseDamage;
	}

	@Override
	public void consume(BaseUnit player, BaseUnit... target) {
		if (target.length <= 1) {
			BaseUnit enemy = target[0];
			caculateDamage(enemy);
		} else {
			for (BaseUnit enemy : target) {
				caculateDamage(enemy);
			}
		}
		if (costMana > 0) {
			player.currentMana -= costMana;
		} else {
			player.items.remove(this);
		}
	}

	private void caculateDamage(BaseUnit enemy) {
		double enemyStrategyAbility = enemy.intelligence * enemy.level / 50.0 + enemy.intelligence;
		double itemFinalDamage = super.baseDamage - enemyStrategyAbility;
		itemFinalDamage = reduceDamage(enemy, itemFinalDamage);
		itemFinalDamage = raiseDamage(enemy, itemFinalDamage);
		itemFinalDamage = itemFinalDamage + RandomHelper.generateInt(0, (int) (itemFinalDamage / 50.0));
		// TODO judge hit rate
		// TODO judge dodge rate
		enemy.currentArmyHP -= itemFinalDamage;
		enemy.currentMorale -= caculateMoralDamage(itemFinalDamage);
	}

	private double raiseDamage(BaseUnit enemy, double currentDamage) {
		if (enemy.currentPositionMap instanceof Forest) {
			return currentDamage + currentDamage / 4;
		}
		return currentDamage;
	}

	@Override
	public boolean consumptionCouldBeHappened(BattleInfo info) {
		return info.weather != Weather.RAIN;
	}

	@Override
	public boolean consumptionCouldBeHappened(BaseUnit enemy) {
		return enemy.currentPositionMap instanceof Forest || enemy.currentPositionMap instanceof Grassland
				|| enemy.currentPositionMap instanceof Plain;
	}

}
