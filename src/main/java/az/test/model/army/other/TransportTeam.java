package az.test.model.army.other;

import az.test.battle.BattleInfo;
import az.test.model.army.BaseUnit;
import az.test.model.army.CornerAttack;
import az.test.model.army.CrossAttack;

/**
 * 运输队
 */
public class TransportTeam extends BaseUnit {

	public TransportTeam(BattleInfo battleInfo) {
		super(battleInfo, "运输队");
		armyHPBase = 300;
		armyHPInc = 40;
		apBase = 20;
		dpBase = 20;
		moveAbility = 3;
		attackRangeList.add(CrossAttack.getInstance());
		attackRangeList.add(CornerAttack.getInstance());
	}

	public TransportTeam(BaseUnit transformFrom) {
		super(transformFrom.battle, "运输队");
		armyHPBase = 300;
		armyHPInc = 40;
		apBase = 20;
		dpBase = 20;
		moveAbility = 3;
		attackRangeList.add(CrossAttack.getInstance());
		attackRangeList.add(CornerAttack.getInstance());
		copyProperties(transformFrom);
	}

}
