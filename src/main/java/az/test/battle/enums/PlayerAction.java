package az.test.battle.enums;

public enum PlayerAction {
	REST(0), //
	ATTACK(1), //
	USE_ITEM(2), //
	TRANSPORT_ITEM(3), //
	DROP_ITEM(4), //
	STRATEGY(5),//
	;

	private PlayerAction(int code) {
		this.code = code;
	}

	private int code;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
