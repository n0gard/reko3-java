package az.test.model.map;

public class Barrack extends MapItem {

	public Barrack() {
		super();
		id = 0x0E;
		name = "兵营";
	}

	@Override
	public void drawMapItem() {
		System.out.print("B");
	}
}
