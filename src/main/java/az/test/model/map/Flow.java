package az.test.model.map;

public class Flow extends MapItem {

	public Flow() {
		super();
		id = 0x13;
		canStay = false;
		name = "浊流";
	}

	@Override
	public void drawMapItem() {
		System.out.print("~~ ");
	}
}
