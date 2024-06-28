package az.test.model.strategy.defensive;

import az.test.exception.HPFullException;
import az.test.exception.MoraleFullException;
import az.test.exception.NotInChaosException;
import az.test.model.army.BaseUnit;
import az.test.model.item.Item;
import az.test.util.RandomHelper;

public abstract class RestoreArmyMorale extends Defensive implements RestoreMorale {

    public RestoreArmyMorale() {
    }

    public RestoreArmyMorale(int baseRestore) {
        this.baseRestore = baseRestore;
    }

    @Override
    public void restoreMorale(BaseUnit player, BaseUnit... targets) throws MoraleFullException, NotInChaosException {
        int baseMoraleRestore = 0;
        if (this instanceof Item) {
            baseMoraleRestore = baseRestore;
        } else {
            baseMoraleRestore = baseRestore + player.level / 10;
        }
        if (1 == targets.length) {
            BaseUnit target = targets[0];
            if (100 == target.currentMorale) {
                throw new MoraleFullException();
            }
            if (!target.isInChaos) {
                throw new NotInChaosException();
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
