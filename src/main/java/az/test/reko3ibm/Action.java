package az.test.reko3ibm;

import az.test.battle.enums.PlayerAction;
import az.test.model.army.BaseUnit;

public class Action {
	public int actionValue = 1;
	public BaseUnit target = null;
	public PlayerAction playerAction;
	public int itemIdx;

	public Action(PlayerAction playerAction, int actionValue) {
		this.playerAction = playerAction;
		this.actionValue = actionValue;
	}

	public Action(PlayerAction playerAction, int actionValue, int itemIdx) {
		this.playerAction = playerAction;
		this.actionValue = actionValue;
		this.itemIdx = itemIdx;
	}
}
