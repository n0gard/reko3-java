package az.test.model.map;

public class Village extends MapItem {

	public Village() {
		super();
		id = 0x08;
		name = "村庄";
	}

	@Override
	public void drawMapItem() {
		System.out.print("V");
	}
}
