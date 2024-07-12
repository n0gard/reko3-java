package az.test.model.effect;

import az.test.exception.MoraleFullAndNotInChaosException;
import az.test.model.army.BaseUnit;
import az.test.util.RandomHelper;

import java.util.function.Function;

public class RestoreArmyMorale extends Effection {
    public int baseRestore;
    public boolean isItem = true;

    public RestoreArmyMorale(int baseRestore, boolean isItem) {
        this.baseRestore = baseRestore;
        this.isItem = isItem;
    }

    @Override
    public void effect(BaseUnit player, Function<Integer, Integer> extra, BaseUnit... targets) throws MoraleFullAndNotInChaosException {
        int baseMoraleRestore = 0;
        if (isItem) {
            baseMoraleRestore = baseRestore;
        } else {
            baseMoraleRestore = baseRestore + player.level / 10;
        }
        if (1 == targets.length) {
            BaseUnit target = targets[0];
            // 没有混乱
            if (!target.isInChaos) {
                // 士气满 不能生效
                if (100 == target.currentMorale) {
                    throw new MoraleFullAndNotInChaosException();
                }
            }
        }
        for (BaseUnit target : targets) {
            int random = RandomHelper.generateInt(0, baseMoraleRestore / 10 - 1);
            int totalRestore = baseMoraleRestore + random;
            if (totalRestore + target.currentMorale > 100) {
                totalRestore = 100 - target.currentMorale;
            }
            target.restoreMorale(totalRestore);
        }
    }
}
