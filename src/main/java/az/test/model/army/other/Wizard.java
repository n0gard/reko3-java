package az.test.model.army.other;

import az.test.battle.BattleInfo;
import az.test.model.army.BaseUnit;
import az.test.model.army.BotUnit;
import az.test.model.army.CrossAttack;
import az.test.model.item.Item;
import az.test.util.ObjectCopyUtil;

import java.util.List;

public class Wizard extends BotUnit {

	public Wizard(BattleInfo battleInfo) {
		super(battleInfo);
		armyHPBase = 300;
		armyHPInc = 50;
		apBase = 20;
		dpBase = 20;
		moveAbility = 4;
		attackRangeList.add(CrossAttack.getInstance());
	}

	public Wizard(BattleInfo battleInfo, BaseUnit transformFrom) {
		super(battleInfo);
		armyHPBase = 300;
		armyHPInc = 50;
		apBase = 20;
		dpBase = 20;
		moveAbility = 4;
		attackRangeList.add(CrossAttack.getInstance());
		this.name = (String) ObjectCopyUtil.deepCopy(transformFrom.name);
		this.intelligence = transformFrom.intelligence;
		this.force = transformFrom.force;
		this.defense = transformFrom.defense;
		this.items = (List<Item>) ObjectCopyUtil.deepCopy(transformFrom.items);
		this.exp = transformFrom.exp;
		this.currentArmyHP = transformFrom.currentArmyHP;
		this.currentMorale = transformFrom.currentMorale;
		this.currentMana = transformFrom.currentMana;
	}

}
