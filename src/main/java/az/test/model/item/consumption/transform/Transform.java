package az.test.model.item.consumption.transform;

import az.test.battle.BattleInfo;
import az.test.exception.BaseException;
import az.test.model.army.BaseUnit;
import az.test.model.effect.TransformArmyType;
import az.test.model.item.BaseItem;
import az.test.model.item.consumption.ConsumableItem;

public abstract class Transform extends ConsumableItem {

    public Transform(int id, String name) {
        super(id, name, new TransformArmyType());
    }

    @Override
    public void reduceItem(BaseUnit army) {
        army.items.remove(this);
    }

    @Override
    public boolean consumptionCouldBeHappened(BattleInfo info) {
        return true;
    }

    @Override
    public boolean consumptionCouldBeHappened(BaseUnit target) {
        return !"LiuBei".equalsIgnoreCase(target.name);
    }

    @Override
    abstract public void consume(BaseUnit player, BaseUnit... target) throws BaseException;
}
