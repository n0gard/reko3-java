package az.test.map;

import az.test.battle.BattleInfo;
import az.test.model.PlayerUnitGenerator;
import az.test.model.army.BotUnit;
import az.test.model.item.consumption.food.Bean;
import az.test.reko3ibm.AI02Standby;
import az.test.reko3ibm.AI03Passive;

public class BattleMap01 extends BattleMap {

	public BattleMap01() {
		super();
		// fill map
		fillingMap(mapIds);
		// name
		battleName = "汜水关";
		// round
		setRoundLimit(30);
		// treasures
		fillingGold(6, 7, 100);
		fillingItem(12,7, new Bean());
	}

	public void loadEnemies(BattleInfo bi) {
		// load enemies
		BotUnit huaxiong = PlayerUnitGenerator.getInstance(bi).loadHuaxiong(9, 3, new AI02Standby(true));
		huaxiong.isLord = true;
		bi.map.lord = huaxiong;
		bi.loadEnemyUnit(bi.map.lord);
		bi.loadEnemyUnit(PlayerUnitGenerator.getInstance(bi).loadLisu(10, 5, new AI03Passive(true)));
		bi.loadEnemyUnit(PlayerUnitGenerator.getInstance(bi).loadHuzhen(9, 4, new AI03Passive(true)));
		bi.loadEnemyUnit(PlayerUnitGenerator.getInstance(bi).loadZhaocen(9, 6, new AI03Passive(true)));
		bi.loadEnemyUnit(PlayerUnitGenerator.getInstance(bi).loadFootmanArmy(8, 11, 1, new AI03Passive(true)));
		bi.loadEnemyUnit(PlayerUnitGenerator.getInstance(bi).loadFootmanArmy(10, 11, 1,new AI03Passive(true)));
		bi.loadEnemyUnit(PlayerUnitGenerator.getInstance(bi).loadFootmanArmy(12, 11, 1,new AI03Passive(true)));
	}








	public static int[][] mapIds = {
			// 0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27
			{ 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13,  0,  4,  4,  4,  4,  4,  4,  4,  0,  0 }, // 0
			{ 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13,  0,  2,  4,  4,  4,  4,  2,  0, 12, 12 }, // 1
			{ 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13,  0,  0,  4,  4,  4,  2,  0, 12, 12, 12 }, // 2
			{ 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13,  0,  0,  2,  2,  0,  0, 12, 12, 12,  0 }, // 3
			{ 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13,  0,  0,  0,  0,  0, 12, 12, 12,  0,  2 }, // 4
			{ 13, 13, 14, 14, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13,  0,  0,  0,  0, 12, 12, 12,  0,  0,  0,  2 }, // 5
			{  0,  0, 14, 14,  0,  0,  0, 13, 13,  5,  5,  5, 11,  5,  5,  5,  0,  0,  0,  0, 12, 12, 12,  0,  0,  0,  0,  0 }, // 6
			{  0,  0, 14, 14,  0,  5, 10,  5,  5,  0,  5,  5,  5,  5,  0,  0,  0,  0, 12, 12,  0,  0,  0,  0,  0,  0,  0,  0 }, // 7
			{  0,  0,  0, 14,  0,  0,  0,  0,  0,  0,  0,  8,  0,  0,  0,  0,  0, 12, 12,  0,  0,  0,  0,  0,  0,  0,  0,  2 }, // 8
			{  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 12, 12,  7,  0,  0,  0,  0,  0,  2,  2,  2 }, // 9
			{  0,  0,  0, 14,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  3,  0,  0,  0,  0,  0,  0,  0,  2,  4,  4 }, // 10
			{  0,  0, 14, 14,  0,  0,  0,  0,  2,  2,  2,  2,  0,  0,  0,  0, 12, 12,  0,  2,  2,  2,  2,  2,  2,  2,  4,  4 }, // 11
			{ 13,  0, 14, 14,  0,  0,  0,  0,  2,  2,  2,  2,  0,  7,  0, 12, 12, 12,  0,  2,  4,  4,  2,  2,  2,  4,  4,  4 }, // 11
			{ 13, 13, 14, 14,  0,  0,  0,  0,  0,  0,  2,  0,  0,  0,  0, 12,  0,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4 }, // 13
			{ 13, 13, 13, 13, 13, 13, 13, 13,  0,  0,  0,  0,  0,  0, 12, 12,  0,  2,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4 }, // 14
			{ 13, 13, 13, 13, 13, 13, 13, 13, 13,  0,  0,  0,  2, 12, 12,  0,  2,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4 }, // 15
	};

}
