package az.test.model.map;

public class Grassland extends MapItem {

	public Grassland() {
		super();
		id = 0x07;
		name = "草地";
	}

	@Override
	public void drawMapItem() {
		System.out.print("g");
	}
}
