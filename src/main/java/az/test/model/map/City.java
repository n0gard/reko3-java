package az.test.model.map;

public class City extends MapItem {

	public City() {
		super();
		id = 0x06;
		name = "城池";
	}

	@Override
	public void drawMapItem() {
		System.out.print("c");
	}
}
