package az.test.model;

import az.test.battle.BattleInfo;
import az.test.exception.MaxItemsLimitedException;
import az.test.model.army.bow.BowSoldier;
import az.test.model.army.foot.ShortArmed;
import az.test.model.army.ride.GuardRide;
import az.test.model.army.ride.LightRide;
import az.test.model.army.ride.Rider;
import az.test.model.item.weapon.SnakeSpear;
import az.test.model.item.consumption.spell.fire.FireSpells;
import az.test.reko3ibm.ActionAIType;

public class PlayerUnitGenerator {
	static BattleInfo battleInfo;

	private static class SingletonInner {
		private static final PlayerUnitGenerator baseUnitGenerator = new PlayerUnitGenerator();
	}

	public static PlayerUnitGenerator getInstance(BattleInfo battleInfo) {
		PlayerUnitGenerator.battleInfo = battleInfo;
		return SingletonInner.baseUnitGenerator;
	}

	public ShortArmed loadLiuBei(int y, int x, ActionAIType aiType) {
		ShortArmed lb = new ShortArmed(battleInfo);
		lb.name = "LiuBei";
		lb.level = 1;
		lb.exp = 0;

		lb.force = 75;
		lb.intelligence = 64;
		lb.defense = 91;

		try {
//			lb.addItem(new GreenDragonCrescentBlade(12));
			lb.addItem(new FireSpells("Coke", 200));
			lb.addItem(new FireSpells("Coke", 200));
			lb.addItem(new FireSpells("Coke", 200));
			lb.addItem(new FireSpells("Coke", 200));
		} catch (MaxItemsLimitedException e) {
			System.err.println("Items full.");
		}
		lb.currentArmyHP = lb.calculateMaxArmyHP();
		lb.currentMorale = 100;
		lb.currentMana = lb.calculateMaxMana();

		// initPosition
		lb.x = x;
		lb.y = y;
		lb.aiType = aiType;
		return lb;
	}

	public LightRide loadGuanyu(int y, int x, ActionAIType aiType) {
		LightRide gy = new LightRide(battleInfo);
		gy.name = "Guanyu";
		gy.level = 1;
		gy.exp = 0;

		gy.force = 98;
		gy.intelligence = 80;
		gy.defense = 100;

//		try {
//			gy.addItem(new Weapon(12));
//			gy.addItem(new FireSpells("Coke", 200));
//		} catch (MaxItemsLimitedException e) {
//			e.printStackTrace();
//		}
		gy.currentArmyHP = gy.calculateMaxArmyHP();
		gy.currentMorale = 100;
		gy.currentMana = gy.calculateMaxMana();

		// initPosition
		gy.x = x;
		gy.y = y;
		gy.aiType = aiType;
		return gy;
	}

	public LightRide loadZhangfei(int y, int x, ActionAIType aiType) {
		LightRide zf = new LightRide(battleInfo);
		zf.name = "Zhangfei";
		zf.level = 1;
		zf.exp = 0;

		zf.force = 99;
		zf.intelligence = 42;
		zf.defense = 83;

		try {
			zf.addItem(new SnakeSpear(10));
		} catch (MaxItemsLimitedException e) {
			e.printStackTrace();
		}
		zf.currentArmyHP = zf.calculateMaxArmyHP();
		zf.currentMorale = 100;
		zf.currentMana = zf.calculateMaxMana();

		// initPosition
		zf.x = x;
		zf.y = y;
		zf.aiType = aiType;
		return zf;
	}

	public LightRide loadHuaxiong(int y, int x, ActionAIType aiType) {
		LightRide hx = new LightRide(battleInfo);
		hx.name = "Huaxiong";
		hx.level = 5;
		hx.exp = 0;

		hx.force = 90;
		hx.intelligence = 29;
		hx.defense = 88;

		hx.currentArmyHP = hx.calculateMaxArmyHP();
		hx.currentMorale = 100;
		hx.currentMana = hx.calculateMaxMana();

		// initPosition
		hx.x = x;
		hx.y = y;
		hx.aiType = aiType;
		return hx;
	}

	public BowSoldier loadLisu(int y, int x, ActionAIType aiType) {
		BowSoldier ls = new BowSoldier(battleInfo);
		ls.name = "Lisu";
		ls.level = 2;
		ls.exp = 0;

		ls.force = 54;
		ls.intelligence = 68;
		ls.defense = 50;

		ls.currentArmyHP = ls.calculateMaxArmyHP();
		ls.currentMorale = 100;
		ls.currentMana = ls.calculateMaxMana();

		// initPosition
		ls.x = x;
		ls.y = y;
		ls.aiType = aiType;
		return ls;
	}

	public ShortArmed loadHuzhen(int y, int x, ActionAIType aiType) {
		ShortArmed hz = new ShortArmed(battleInfo);
		hz.name = "Huzhen";
		hz.level = 2;
		hz.exp = 0;

		hz.force = 58;
		hz.intelligence = 30;
		hz.defense = 37;

		hz.currentArmyHP = hz.calculateMaxArmyHP();
		hz.currentMorale = 100;
		hz.currentMana = hz.calculateMaxMana();

		// initPosition
		hz.x = x;
		hz.y = y;
		hz.aiType = aiType;
		return hz;
	}

	public ShortArmed loadZhaocen(int y, int x, ActionAIType aiType) {
		ShortArmed zc = new ShortArmed(battleInfo);
		zc.name = "Zhaocen";
		zc.level = 2;
		zc.exp = 0;

		zc.force = 63;
		zc.intelligence = 25;
		zc.defense = 57;

		zc.currentArmyHP = zc.calculateMaxArmyHP();
		zc.currentMorale = 100;
		zc.currentMana = zc.calculateMaxMana();

		// initPosition
		zc.x = x;
		zc.y = y;
		zc.aiType = aiType;
		return zc;
	}

	public Rider loadCaocao(int y, int x, ActionAIType aiType) {
		GuardRide cc = new GuardRide(battleInfo);
		cc.name = "Caocao";
		cc.level = 68;
		cc.exp = 0;

		cc.force = 75;
		cc.intelligence = 94;
		cc.defense = 98;

		cc.currentArmyHP = cc.calculateMaxArmyHP();
		cc.currentMorale = 100;
		cc.currentMana = cc.calculateMaxMana();

		cc.y = y;
		cc.x = x;
		cc.aiType = aiType;
		return cc;
	}

	public ShortArmed loadFootmanArmy(int y, int x, int level, ActionAIType aiType) {
		ShortArmed bbd = new ShortArmed(battleInfo);
		bbd.name = "BuBingDui";
		bbd.level = level;
		bbd.exp = 0;

		bbd.force = 40;
		bbd.intelligence = 30;
		bbd.defense = 50;

		bbd.currentArmyHP = bbd.calculateMaxArmyHP();
		bbd.currentMorale = 100;
		bbd.currentMana = bbd.calculateMaxMana();

		// initPosition
		bbd.x = x;
		bbd.y = y;

		bbd.aiType = aiType;
		return bbd;
	}
}
