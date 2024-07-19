package az.test.model.map;

public class Fence extends MapItem {

	public Fence() {
		super();
		id = 0x0C;
		canStay = false;
		name = "栅栏";
	}

	@Override
	public void drawMapItem() {
		System.out.print("++");
	}
}
