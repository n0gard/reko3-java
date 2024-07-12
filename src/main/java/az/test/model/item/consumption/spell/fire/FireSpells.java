package az.test.model.item.consumption.spell.fire;

import az.test.battle.BattleInfo;
import az.test.battle.enums.Weather;
import az.test.exception.BaseException;
import az.test.model.army.BaseUnit;
import az.test.model.effect.DamageArmy;
import az.test.model.item.consumption.spell.Spells;
import az.test.model.map.City;
import az.test.model.map.Forest;
import az.test.model.map.Grassland;
import az.test.model.map.Plain;

import java.util.function.Function;

/**
 * 火系施法（施展策略）
 */
public abstract class FireSpells extends Spells {

    public FireSpells(int id, String name, int baseDamage) {
        super(id, name, new DamageArmy(name, baseDamage));
    }

    @Override
    public void consume(BaseUnit player, BaseUnit... targets) throws BaseException {
        for (BaseUnit target : targets) {
            Function<Integer, Integer> calculateExtra = damage -> {
                if (target.currentPositionMap instanceof Forest) {
                    return damage + damage / 4;
                } else {
                    return damage;
                }
            };
            effection.effect(player, calculateExtra, target);
        }
    }

    @Override
    public boolean consumptionCouldBeHappened(BattleInfo info) {
        return info.weatherList.get(info.map.getCurrentRoundNo() - 1) != Weather.RAIN;
    }

    @Override
    public boolean consumptionCouldBeHappened(BaseUnit enemy) {
        return enemy.currentPositionMap instanceof Forest || enemy.currentPositionMap instanceof Grassland
                || enemy.currentPositionMap instanceof Plain || enemy.currentPositionMap instanceof City;
    }

}
