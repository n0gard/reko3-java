package az.test.model.effect;

import az.test.exception.BaseException;
import az.test.model.army.BaseUnit;
import az.test.model.army.other.MilitaryBand;
import az.test.model.army.other.TransportTeam;
import az.test.model.army.other.Wizard;
import az.test.util.LogUtil;
import az.test.util.RandomHelper;

import java.util.function.Function;

public class DamageArmy extends EffectAction {
    public int baseDamage;


    public DamageArmy(String spellName, int baseDamage) {
        super.spellName = spellName;
        this.baseDamage = baseDamage;
    }

    @Override
    public void effect(BaseUnit player, Function<Integer, Integer> calculateExtra, BaseUnit... targets) throws BaseException {
        for (BaseUnit t : targets) {
            int fdp1 = calculateDamageStep1(t);
            int fdp2 = calculateDamageStep2(t, fdp1);
            int fdp3 = null != calculateExtra ? calculateExtra.apply(fdp2) : fdp2;
            int fdp = calculateDamageStep4(fdp3);
            int fmdp = calculateMoralDamage(fdp);
            fdp = t.reduceHP(fdp, t);
            fmdp = t.reduceMorale(fmdp, fdp, t);

            t.judgeKickOut(t, false);

            int baseExp = player.calculateBaseExp(t);
            int extraExp = player.calculateExtraExp(t);
            if (baseExp + extraExp > 0) {
                player.gainExp(baseExp + extraExp);
            }
            LogUtil.printLog(player.battle.map.getCurrentRoundNo(), "Damaged", player.toString(),
                    t.name, "hpDamage: " + fdp + " moraleDamage: " + fmdp + " spell: " + this.spellName
                            + " gain base exp: " + baseExp + ", extra exp: " + extraExp + ", target: " + t + ", kick-out?" + (t.isEvacuated));
        }
    }

    /** step 1 人物策略能力＝智力×等级÷50＋智力<br>
     *          道具攻击杀伤＝道具基本杀伤－防御方人物策略能力
     */
    private int calculateDamageStep1(BaseUnit target) {
        int enemyStrategyAbility = target.intelligence * target.level / 50 + target.intelligence;
        return baseDamage - enemyStrategyAbility;
    }

    /**
     * step 2 如果防御方是军乐队、运输队或妖术师<br>
     *          策略攻击杀伤＝策略攻击杀伤÷2
     */
    private int calculateDamageStep2(BaseUnit target, int lastStepResult) {
        return reduceDamage(target, lastStepResult);
    }

    /**
     * step 3 如果被防御方在树林中，且策略是焦热系策略<br>
     *          策略攻击杀伤＝策略攻击杀伤＋策略攻击杀伤÷4<br>
     *          如果当前天气是雨天，且策略是漩涡系策略<br>
     *          策略攻击杀伤＝策略攻击杀伤＋策略攻击杀伤÷4<br>
     */
    private int calculateDamageStep3(int lastStepResult, Function<Integer, Integer> calculateExtra) {
        return calculateExtra.apply(lastStepResult);
    }

    /**
     * step 4
     */
    private int calculateDamageStep4(int lastStepResult) {
        return lastStepResult + RandomHelper.generateInt(0, lastStepResult / 50);
    }

    private int reduceDamage(BaseUnit target, int currentDamage) {
        if (target instanceof MilitaryBand || target instanceof TransportTeam || target instanceof Wizard) {
            return currentDamage / 2;
        }
        return currentDamage;
    }

    private int calculateMoralDamage(int finalDamage) {
        int moralDamage = 0;
        if (0 == finalDamage) {
            return moralDamage;
        }
        moralDamage = finalDamage / 100;
        if (0 == moralDamage && finalDamage > 0) {
            moralDamage = 1;
        }
        return moralDamage;
    }
}
