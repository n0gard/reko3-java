package az.test.reko3ibm;

import az.test.battle.BattleInfo;
import az.test.model.army.BotUnit;

import java.io.Serializable;

public abstract class ActionAIType implements Serializable {
	public boolean isEnemy;

	public abstract void action(BattleInfo bi, BotUnit army, boolean isSim);
}
