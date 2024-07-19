package az.test.model.map;

public class House extends MapItem {

	public House() {
		super();
		id = 0x11;
		canStay = false;
		name = "房舍";
	}

	@Override
	public void drawMapItem() {
		System.out.print("hh ");
	}
}
