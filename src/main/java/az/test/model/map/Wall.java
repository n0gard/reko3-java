package az.test.model.map;

public class Wall extends MapItem {

	public Wall() {
		super();
		id = 0x05;
		canStay = false;
		name = "城墙";
	}

	@Override
	public void drawMapItem() {
		System.out.print("WW ");
	}
}
