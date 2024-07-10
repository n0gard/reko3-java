package az.test.model.item;

import az.test.model.effect.GoldIncrease;

public class Gold extends BaseItem {
    public int liang;

    public Gold(int liang) {
        super(0xFF, "黄金", new GoldIncrease());
        this.liang = liang;
    }
}
