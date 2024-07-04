package az.test.model.army.other;

import az.test.battle.BattleInfo;
import az.test.model.army.BaseUnit;
import az.test.model.army.BotUnit;
import az.test.model.army.CrossAttack;
import az.test.model.item.Item;
import az.test.util.ObjectCopyUtil;

import java.util.List;

public class MilitaryBand extends BotUnit {

	public MilitaryBand(BattleInfo battleInfo) {
		super(battleInfo);
		armyHPBase = 300;
		armyHPInc = 40;
		apBase = 20;
		dpBase = 20;
		moveAbility = 4;
		attackRangeList.add(CrossAttack.getInstance());
	}

	public MilitaryBand(BattleInfo battleInfo, BaseUnit transformFrom) {
		super(battleInfo);
		armyHPBase = 300;
		armyHPInc = 40;
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
