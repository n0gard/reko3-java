package az.test.model.army.theif;

import az.test.battle.BattleInfo;

public class JusticeThief extends Thief {

	public JusticeThief(BattleInfo battleInfo) {
		super(battleInfo, "义贼");
		apBase = 70;
		dpBase = 60;
	}

	public JusticeThief(EvilThief army) {
		super(army.battle, "义贼");
		apBase = 70;
		dpBase = 60;
		copyProperties(army);
	}

}
