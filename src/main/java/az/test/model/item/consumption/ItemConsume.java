package az.test.model.item.consumption;

import az.test.model.Consume;
import az.test.model.army.BaseUnit;
import az.test.model.item.BaseItem;

public interface ItemConsume extends Consume {
    public void reduceItem(BaseUnit player);
}
