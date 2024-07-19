package az.test.model.map;

public class Gate extends MapItem {

	public Gate() {
		super();
		id = 0x0A;
		canStay = false;
		name = "城门";
	}

	@Override
	public void drawMapItem() {
		System.out.print("gg ");
	}
}
