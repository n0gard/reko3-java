package az.test.reko3ibm;

import az.test.battle.BattleInfo;
import az.test.model.army.BaseUnit;

import java.io.Serializable;

public abstract class ActionAIType implements Serializable {
	public boolean isEnemy;

	public void action(BattleInfo bi, BaseUnit army, boolean isSim) {
		System.out.println("AI not set yet.");
	}
}
