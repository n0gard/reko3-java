package az.test.model.item.consumption.transform;

import az.test.exception.BaseException;
import az.test.model.army.BaseUnit;
import az.test.model.effect.TransformArmy;
import az.test.model.item.consumption.ConsumableItem;

public abstract class Transform extends ConsumableItem {

    public Transform(int id, String name) {
        super(id, name, new TransformArmy());
    }

    @Override
    public boolean consumptionCouldBeHappened(BaseUnit target) {
        return !"LiuBei".equalsIgnoreCase(target.name);
    }

    @Override
    abstract public void consume(BaseUnit player, BaseUnit... target) throws BaseException;
}
