package az.test.model;

import az.test.exception.MaxItemsLimitedException;
import az.test.model.army.bow.BowSoilder;
import az.test.model.army.foot.ShortArmed;
import az.test.model.army.ride.LightRide;
import az.test.model.item.Weapon;
import az.test.model.item.consumption.FireSpells;
import az.test.reko3ibm.ActionAIType;

public class PlayerUnitGenerator {
	public static ShortArmed loadLiuBei(int y, int x, ActionAIType aiType) {
		ShortArmed lb = new ShortArmed();
		lb.name = "LiuBei";
		lb.level = 1;
		lb.exp = 0;

		lb.force = 75;
		lb.intelligence = 64;
		lb.defense = 91;

		try {
			lb.addItem(new Weapon(12));
			lb.addItem(new FireSpells("Coke", 200));
			lb.addItem(new FireSpells("Coke", 200));
			lb.addItem(new FireSpells("Coke", 200));
			lb.addItem(new FireSpells("Coke", 200));
		} catch (MaxItemsLimitedException e) {
			e.printStackTrace();
		}
		lb.currentArmyHP = lb.initMaxArmyHP();
		lb.currentMorale = 100;
		lb.currentMana = lb.initMaxMana();

		// initPosition
		lb.x = x;
		lb.y = y;
		lb.aiType = aiType;
		return lb;
	}

	public static LightRide loadGuanyu(int y, int x, ActionAIType aiType) {
		LightRide gy = new LightRide();
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
		gy.currentArmyHP = gy.initMaxArmyHP();
		gy.currentMorale = 100;
		gy.currentMana = gy.initMaxMana();

		// initPosition
		gy.x = x;
		gy.y = y;
		gy.aiType = aiType;
		return gy;
	}

	public static LightRide loadZhangfei(int y, int x, ActionAIType aiType) {
		LightRide zf = new LightRide();
		zf.name = "Zhangfei";
		zf.level = 1;
		zf.exp = 0;

		zf.force = 99;
		zf.intelligence = 42;
		zf.defense = 83;

		try {
			zf.addItem(new Weapon(10));
		} catch (MaxItemsLimitedException e) {
			e.printStackTrace();
		}
		zf.currentArmyHP = zf.initMaxArmyHP();
		zf.currentMorale = 100;
		zf.currentMana = zf.initMaxMana();

		// initPosition
		zf.x = x;
		zf.y = y;
		zf.aiType = aiType;
		return zf;
	}

	public static LightRide loadHuaxiong(int y, int x, ActionAIType aiType) {
		LightRide hx = new LightRide();
		hx.name = "Huaxiong";
		hx.level = 5;
		hx.exp = 0;

		hx.force = 90;
		hx.intelligence = 29;
		hx.defense = 88;

		hx.currentArmyHP = hx.initMaxArmyHP();
		hx.currentMorale = 100;
		hx.currentMana = hx.initMaxMana();

		// initPosition
		hx.x = x;
		hx.y = y;
		hx.aiType = aiType;
		return hx;
	}

	public static BowSoilder loadLisu(int y, int x, ActionAIType aiType) {
		BowSoilder ls = new BowSoilder();
		ls.name = "Lisu";
		ls.level = 2;
		ls.exp = 0;

		ls.force = 54;
		ls.intelligence = 68;
		ls.defense = 50;

		ls.currentArmyHP = ls.initMaxArmyHP();
		ls.currentMorale = 100;
		ls.currentMana = ls.initMaxMana();

		// initPosition
		ls.x = x;
		ls.y = y;
		ls.aiType = aiType;
		return ls;
	}

	public static ShortArmed loadHuzhen(int y, int x, ActionAIType aiType) {
		ShortArmed hz = new ShortArmed();
		hz.name = "Huzhen";
		hz.level = 2;
		hz.exp = 0;

		hz.force = 58;
		hz.intelligence = 30;
		hz.defense = 37;

		hz.currentArmyHP = hz.initMaxArmyHP();
		hz.currentMorale = 100;
		hz.currentMana = hz.initMaxMana();

		// initPosition
		hz.x = x;
		hz.y = y;
		hz.aiType = aiType;
		return hz;
	}

	public static ShortArmed loadZhaocen(int y, int x, ActionAIType aiType) {
		ShortArmed zc = new ShortArmed();
		zc.name = "Zhaocen";
		zc.level = 2;
		zc.exp = 0;

		zc.force = 63;
		zc.intelligence = 25;
		zc.defense = 57;

		zc.currentArmyHP = zc.initMaxArmyHP();
		zc.currentMorale = 100;
		zc.currentMana = zc.initMaxMana();

		// initPosition
		zc.x = x;
		zc.y = y;
		zc.aiType = aiType;
		return zc;
	}

	public static ShortArmed loadFootmanArmy(int y, int x, int level, ActionAIType aiType) {
		ShortArmed bbd = new ShortArmed();
		bbd.name = "BuBingDui";
		bbd.level = level;
		bbd.exp = 0;

		bbd.force = 40;
		bbd.intelligence = 30;
		bbd.defense = 50;

		bbd.currentArmyHP = bbd.initMaxArmyHP();
		bbd.currentMorale = 100;
		bbd.currentMana = bbd.initMaxMana();

		// initPosition
		bbd.x = x;
		bbd.y = y;

		bbd.aiType = aiType;
		return bbd;
	}
}
