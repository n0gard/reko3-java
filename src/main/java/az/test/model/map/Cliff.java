package az.test.model.map;

public class Cliff extends MapItem {

	public Cliff() {
		super();
		id = 0x07;
		canStay = false;
		name = "悬崖";
	}

	@Override
	public void drawMapItem() {
		System.out.print("cc ");
	}
}
