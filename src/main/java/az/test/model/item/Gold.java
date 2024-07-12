package az.test.model.item;

public class Gold extends BaseItem {
    public int liang;

    public Gold(int liang) {
        super(0xFF, "黄金");
        this.liang = liang;
    }
}
