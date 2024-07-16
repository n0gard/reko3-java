package az.test.model.army.other;

import az.test.battle.BattleInfo;
import az.test.model.army.BaseUnit;
import az.test.model.army.CornerAttack;
import az.test.model.army.CrossAttack;

/** 武术家 */
public class MartialArtist extends BaseUnit {

	public MartialArtist(BattleInfo battleInfo) {
		super(battleInfo, "武术家");
		armyHPBase = 600;
		armyHPInc = 50;
		apBase = 70;
		dpBase = 60;
		moveAbility = 5;
		attackRangeList.add(CrossAttack.getInstance());
		attackRangeList.add(CornerAttack.getInstance());
	}

}
