package az.test.model.item;

import az.test.model.effect.PlayerGoldIncrease;

public class Gold extends BaseItem {
    public int liang;

    public Gold(int liang) {
        super(0xFF, "黄金", new PlayerGoldIncrease());
        this.liang = liang;
    }
}
