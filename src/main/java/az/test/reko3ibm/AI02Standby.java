package az.test.reko3ibm;

import az.test.battle.BattleInfo;
import az.test.exception.CounterattackHappenedException;
import az.test.exception.OutOfAttackRangeException;
import az.test.model.army.BaseUnit;
import az.test.model.army.BotUnit;
import az.test.util.LogUtil;

public class AI02Standby extends ActionAIType {
	public AI02Standby(boolean isEnemy) {
		super();
		super.isEnemy = isEnemy;
	}

	@Override
	public void action(BattleInfo battle, BotUnit army, boolean isSim) {
		LogUtil.printLog(battle.map.getCurrentRoundNo(), "action", army.toString(), "AI02", "action start");
		// calculate attack target
		BaseUnit target = army.calculateCurrentPositionAttackTarget(battle);
		if (null != target) {
			try {
				army.attack(battle, target, false, isEnemy, isSim);
			} catch (OutOfAttackRangeException ooare) {
				ooare.printStackTrace();
			} catch (CounterattackHappenedException che) {

			}
		} else {
			LogUtil.printInfo(battle.map.getCurrentRoundNo(), "AI02", "No player could be attacked.");
		}
	}

}
