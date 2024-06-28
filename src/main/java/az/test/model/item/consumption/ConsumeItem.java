package az.test.model.item.consumption;

import az.test.model.Consume;
import az.test.model.army.BaseUnit;
import az.test.model.item.Item;

public interface ConsumeItem extends Item, Consume {
    public void reduceItem(BaseUnit army);
}
