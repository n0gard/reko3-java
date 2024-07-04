package az.test.model.item;

public class Gold extends BaseItem {
    public int liang;

    public Gold(int liang) {
        super(0x99);
        this.liang = liang;
    }
}
