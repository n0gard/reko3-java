package az.test.model.army.other;

import az.test.battle.BattleInfo;
import az.test.model.army.BotUnit;
import az.test.model.army.CornerAttack;
import az.test.model.army.CrossAttack;

/** 武术家 */
public class MartialArtist extends BotUnit {

	public MartialArtist(BattleInfo battleInfo) {
		super(battleInfo);
		armyHPBase = 600;
		armyHPInc = 50;
		apBase = 70;
		dpBase = 60;
		moveAbility = 5;
		attackRangeList.add(CrossAttack.getInstance());
		attackRangeList.add(CornerAttack.getInstance());
	}

}
