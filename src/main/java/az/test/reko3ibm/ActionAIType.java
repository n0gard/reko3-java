package az.test.reko3ibm;

import az.test.battle.BattleInfo;
import az.test.model.army.BaseUnit;

public abstract class ActionAIType {
	public boolean isEnemy;

	public void action(BattleInfo bi, BaseUnit army, boolean isSim) {
		System.out.println("AI not set yet.");
	}
}
