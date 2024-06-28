package az.test.model.strategy.defensive;

import az.test.exception.HPFullException;
import az.test.model.army.BaseUnit;
import az.test.model.item.Item;
import az.test.util.RandomHelper;

public abstract class RestoreArmyHP extends Defensive implements RestoreHP {

    public RestoreArmyHP() {
    }

    public RestoreArmyHP(int baseRestore) {
        this.baseRestore = baseRestore;
    }

    @Override
    public void restoreHP(BaseUnit player, BaseUnit... targets) throws HPFullException {
        int baseHPRestore = 0;
        if (this instanceof Item) {
            baseHPRestore = baseRestore;
        } else {
            baseHPRestore = baseRestore + player.intelligence * player.level / 20;
        }
        if (1 == targets.length) {
            BaseUnit target = targets[0];
            if (target.currentArmyHP == target.calculateMaxArmyHP()) {
                throw new HPFullException();
            }
        }
        for (BaseUnit target : targets) {
            int random = RandomHelper.generateInt(0, baseHPRestore / 10 - 1);
            int totalRestore = baseHPRestore + random;
            if (totalRestore + target.currentArmyHP > target.calculateMaxArmyHP()) {
                totalRestore = target.calculateMaxArmyHP() - target.currentArmyHP;
            }
            target.restoreHP(totalRestore);
        }
    }
}
