package az.test.model.effect;

import az.test.exception.HPFullException;
import az.test.model.army.BaseUnit;
import az.test.util.RandomHelper;

import java.util.function.Function;

public class RestoreArmyHP extends Effection {
    public int baseRestore;
    public boolean isItem;

    public RestoreArmyHP(int baseRestore, boolean isItem) {
        this.baseRestore = baseRestore;
        this.isItem = isItem;
    }

    @Override
    public void effect(BaseUnit player, Function<Integer, Integer> extra, BaseUnit... targets) throws HPFullException {
        int baseHPRestore = 0;
        if (isItem) {
            baseHPRestore = baseRestore + player.intelligence * player.level / 20;
        } else {
            baseHPRestore = baseRestore;
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
