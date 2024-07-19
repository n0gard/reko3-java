package az.test.model.map;

public class River extends MapItem {

	public River() {
		super();
		id = 0x03;
		canStay = false;
		name = "河流";
	}

	@Override
	public void drawMapItem() {
		System.out.print("rr ");
	}
}
