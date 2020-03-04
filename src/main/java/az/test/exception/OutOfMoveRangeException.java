package az.test.exception;

import az.test.model.army.BaseUnit;
import az.test.model.map.MapItem;

public class OutOfMoveRangeException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4521037846587535471L;

	private BaseUnit who;
	private MapItem wannaMovedTo;

	public OutOfMoveRangeException(BaseUnit who, MapItem wannaMovedTo) {
		super();
		this.who = who;
		this.wannaMovedTo = wannaMovedTo;
	}

	public BaseUnit getWho() {
		return who;
	}

	public void setWho(BaseUnit who) {
		this.who = who;
	}

	public MapItem getWannaMovedTo() {
		return wannaMovedTo;
	}

	public void setWannaMovedTo(MapItem wannaMovedTo) {
		this.wannaMovedTo = wannaMovedTo;
	}

}
