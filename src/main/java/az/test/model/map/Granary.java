package az.test.model.map;

public class Granary extends MapItem {

	public Granary() {
		super();
		id = 0x0F;
		name = "粮仓";
	}

	@Override
	public void drawMapItem() {
		System.out.print("G");
	}
}
