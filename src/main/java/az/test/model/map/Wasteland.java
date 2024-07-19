package az.test.model.map;

public class Wasteland extends MapItem {

	public Wasteland() {
		super();
		id = 0x0B;
		name = "荒地";
	}

	@Override
	public void drawMapItem() {
		System.out.print("w");
	}
}
