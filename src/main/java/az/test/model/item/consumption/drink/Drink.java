package az.test.model.item.consumption.drink;

import az.test.exception.BaseException;
import az.test.model.army.BaseUnit;
import az.test.model.effect.RestoreArmyMorale;
import az.test.model.item.consumption.ConsumableItem;

public abstract class Drink extends ConsumableItem {
    public Drink(int id, String name, int baseRestore) {
        super(id, name, new RestoreArmyMorale(baseRestore));
    }

    @Override
    public boolean consumptionCouldBeHappened(BaseUnit target) {
        return super.consumptionCouldBeHappened(target);
    }

    @Override
    public void consume(BaseUnit player, BaseUnit... targets) throws BaseException {
        super.consume(player, targets);
        reduceItem(player);
    }
}
