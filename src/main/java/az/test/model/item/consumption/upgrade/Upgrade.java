package az.test.model.item.consumption.upgrade;

import az.test.model.effect.UpgradeArmy;
import az.test.model.item.consumption.ConsumableItem;

public abstract class Upgrade extends ConsumableItem {

    public Upgrade(int id, String name) {
        super(id, name, new UpgradeArmy());
    }

}
