package az.test.model.map;

public class Plain extends MapItem {

	public Plain() {
		super();
		id = 0x00;
		name = "平原";
	}

	@Override
	public void drawMapItem() {
		System.out.print(".");
	}
}
