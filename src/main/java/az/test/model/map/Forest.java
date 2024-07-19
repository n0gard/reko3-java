package az.test.model.map;

public class Forest extends MapItem {

	public Forest() {
		super();
		id = 0x01;
		name = "树林";
	}

	@Override
	public void drawMapItem() {
		System.out.print("F");
	}
}
