package az.test.model.item.consumption.food;

import az.test.exception.BaseException;
import az.test.model.army.BaseUnit;
import az.test.model.effect.RestoreArmyHP;
import az.test.model.item.consumption.ConsumableItem;

public abstract class Food extends ConsumableItem {
    public Food(int id, String name, int baseRestore) {
        super(id, name, new RestoreArmyHP(baseRestore, true));
    }

    @Override
    public boolean consumptionCouldBeHappened(BaseUnit target) {
        return target.currentArmyHP < target.calculateMaxArmyHP();
    }

    @Override
    public void consume(BaseUnit player, BaseUnit... targets) throws BaseException {
        effection.effect(player, null, targets);
        reduceItem(player);
    }
}
