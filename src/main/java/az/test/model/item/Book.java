package az.test.model.item;

public class Book extends BaseItem {
	public int dpIncrementalPercentage;

	public Book(int id, String name, int dpip) {
		super(id, name);
		this.dpIncrementalPercentage = dpip;
	}

}
