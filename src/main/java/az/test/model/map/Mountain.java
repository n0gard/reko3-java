package az.test.model.map;

public class Mountain extends MapItem {

	public Mountain() {
		super();
		id = 0x02;
		name = "山地";
	}

	@Override
	public void drawMapItem() {
		System.out.print("m");
	}
}
