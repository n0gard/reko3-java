package az.test.model.map;

public class Bridge extends MapItem {

	public Bridge() {
		super();
		id = 0x04;
		name = "桥梁";
	}

	@Override
	public void drawMapItem() {
		System.out.print("b");
	}
}
