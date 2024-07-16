package az.test.model.item.horse;

import az.test.model.item.BaseItem;

public abstract class Horse extends BaseItem {
    public int extraMoveAbility;

    public Horse(int id, String name, int extraMoveAbility) {
        super(id, name);
    }
}
