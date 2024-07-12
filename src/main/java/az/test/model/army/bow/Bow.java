package az.test.model.army.bow;

import az.test.battle.BattleInfo;
import az.test.model.army.BaseUnit;
import az.test.model.army.BotUnit;
import az.test.model.army.CornerAttack;
import az.test.model.army.Cross2Attack;

public abstract class Bow extends BaseUnit {

	public Bow(BattleInfo battle, String armyName) {
		super(battle, armyName);
		armyHPBase = 500;
		armyHPInc = 40;
		moveAbility = 4;
		attackRangeList.add(CornerAttack.getInstance());
		attackRangeList.add(Cross2Attack.getInstance());
	}

}
