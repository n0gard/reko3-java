package az.test.model.map;

public class Abatis extends MapItem {

	public Abatis() {
		super();
		id = 0x0D;
		name = "鹿砦";
	}

	@Override
	public void drawMapItem() {
		System.out.print("A");
	}
}
