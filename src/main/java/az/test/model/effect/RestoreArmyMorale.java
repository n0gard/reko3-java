package az.test.model.effect;

import az.test.exception.MoraleFullAndNotInChaosException;
import az.test.exception.NotInChaosException;
import az.test.model.army.BaseUnit;
import az.test.model.item.Item;
import az.test.util.RandomHelper;

public class RestoreArmyMorale extends EffectAction {
    int baseRestore;
    public RestoreArmyMorale() {
    }

    public RestoreArmyMorale(int baseRestore) {
        this.baseRestore = baseRestore;
    }

    @Override
    public void effect(BaseUnit player, BaseUnit... targets) throws MoraleFullAndNotInChaosException {
        int baseMoraleRestore = 0;
        if (this instanceof Item) {
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
