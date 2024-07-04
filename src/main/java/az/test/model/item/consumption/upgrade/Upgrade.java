package az.test.model.item.consumption.upgrade;

import az.test.model.item.BaseItem;
import az.test.model.item.consumption.ConsumeItem;

public abstract class Upgrade extends BaseItem implements ConsumeItem {

    public Upgrade(int id) {
        super(id);
    }


}
