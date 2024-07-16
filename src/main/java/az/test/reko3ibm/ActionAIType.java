package az.test.reko3ibm;

import az.test.battle.BattleInfo;
import az.test.model.army.BaseUnit;
import az.test.model.army.BotAction;

import java.io.Serializable;

/**
 * 00-防守模式，对应运行时AI=03（休息）
 * 01-出击模式，对应运行时AI=01（攻击最近敌）
 * 02-坚守模式，对应运行时AI=02（不动）
 * 03-追击模式，对应运行时AI=00（向敌方某人移动）
 * 04-行军模式，对应运行时AI=00（向指定坐标移动）
 * 05-追随模式，对应运行时AI=00（向己方某人移动）
 * 06-逃跑模式，对应运行时AI=04（无攻击移动）
 *
 * 00 不动（坚守原地）
 * 01 休息（被动出击）
 * 02 攻击最近敌（主动出击）
 * 03 某某移动（追击仇人，修改器没有仇人选项）
 * 04 X,Y移动（移动过程中会对移动范围内敌人攻击）
 * 05 某某无攻击移动（向“仇人”移动，不攻击）
 * 06 无攻击移动（移动过程中不会对移动范围内敌人攻击）
 */
public abstract class ActionAIType implements Serializable {
	public boolean isEnemy;
	public BaseUnit attackTarget;
	public int targetY;
	public int targetX;
	public BaseUnit moveTarget;
	public BotAction botAction;

	public abstract void action(BattleInfo bi, BaseUnit army, boolean isSim);
}
