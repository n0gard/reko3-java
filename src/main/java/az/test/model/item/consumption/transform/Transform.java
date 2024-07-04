package az.test.model.item.consumption.transform;

import az.test.battle.BattleInfo;
import az.test.exception.BaseException;
import az.test.model.army.BaseUnit;
import az.test.model.item.BaseItem;
import az.test.model.item.consumption.ConsumeItem;

public abstract class Transform extends BaseItem implements ConsumeItem {

    public Transform(int id) {
        super(id);
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
