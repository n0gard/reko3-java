package az.test.model.item.consumption;

import az.test.battle.BattleInfo;
import az.test.battle.enums.Weather;
import az.test.model.army.BaseUnit;
import az.test.model.map.City;
import az.test.model.map.Forest;
import az.test.model.map.Grassland;
import az.test.model.map.Plain;
import az.test.util.RandomHelper;

import static sun.audio.AudioPlayer.player;

public class FireSpells extends Spells implements Consume {

    public FireSpells(String name, int baseDamage) {
        super();
        super.name = name;
        super.baseDamage = baseDamage;
    }

    @Override
    public void consume(BaseUnit player, BaseUnit... target) {
        for (BaseUnit t : target) {
            int fdp = calculateDamage(t);
            int fmdp = calculateMoralDamage(fdp);
            fdp = player.reduceHP(fdp, t);
            fmdp = player.reduceMorale(fmdp, fdp, t);

            int baseExp = player.calculateBaseExp(t);
            int extraExp = player.calculateExtraExp(t);
            if (baseExp + extraExp > 0) {
                player.gainExp(baseExp + extraExp);
            }
        }
        if (costMana > 0) {
            player.currentMana -= costMana;
        } else {
            player.items.remove(this);
        }
    }

    private int calculateDamage(BaseUnit enemy) {
        // step 1 人物策略能力＝智力×等级÷50＋智力
        //　　　   道具攻击杀伤＝道具基本杀伤－防御方人物策略能力
        int enemyStrategyAbility = enemy.intelligence * enemy.level / 50 + enemy.intelligence;
        int itemFinalDamage = super.baseDamage - enemyStrategyAbility;
        // step 2 如果防御方是军乐队、运输队或妖术师
        //　　　　　策略攻击杀伤＝策略攻击杀伤A÷2
        itemFinalDamage = reduceDamage(enemy, itemFinalDamage);
        // step 3 如果被防御方在树林中，且策略是焦热系策略
        //　　　　　策略攻击杀伤＝策略攻击杀伤＋策略攻击杀伤÷4
        //　　　   如果当前天气是雨天，且策略是漩涡系策略
        //　　　　　策略攻击杀伤＝策略攻击杀伤＋策略攻击杀伤÷4
        itemFinalDamage = raiseDamage(enemy, itemFinalDamage);
        // step 4
        itemFinalDamage = itemFinalDamage + RandomHelper.generateInt(0, itemFinalDamage / 50);
        return itemFinalDamage;
    }

    private int raiseDamage(BaseUnit enemy, int currentDamage) {
        if (enemy.currentPositionMap instanceof Forest) {
            return currentDamage + currentDamage / 4;
        }
        return currentDamage;
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
