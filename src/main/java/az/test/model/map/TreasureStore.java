package az.test.model.map;

public class TreasureStore extends MapItem {

	public TreasureStore() {
		super();
		id = 0x10;
		name = "宝物库";
	}

	@Override
	public void drawMapItem() {
		System.out.print("T");
	}
}
