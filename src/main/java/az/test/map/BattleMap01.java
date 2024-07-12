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
			{  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  0,  1,  1,  1,  1,  1,  1,  1,  0,  3 }, // 0
			{  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  0,  7,  1,  1,  1,  1,  7,  0,  3,  3 }, // 1
			{  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  0,  0,  1,  1,  1,  7,  0,  3,  3,  3 }, // 2
			{  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  0,  0,  7,  7,  0,  0,  3,  3,  3,  0 }, // 3
			{  9,  9,  5,  5,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9, 11,  0,  0,  0,  0,  0,  3,  3,  3,  0,  7 }, // 4
			{ 11, 11,  5,  5,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9,  9, 11,  0,  0,  0,  3,  3,  3,  3,  0,  0,  0,  0 }, // 5
			{  0,  0,  5,  5,  0, 11, 11,  9,  9, 11, 11, 11, 15, 11, 11, 11,  0,  0,  0,  3,  3,  3,  3,  0,  0,  0,  0,  0 }, // 6
			{  0,  0,  5,  5,  0,  0, 16, 11, 11,  0, 11, 11, 11,  0,  0,  0,  0,  0,  3,  3,  0,  0,  0,  0,  0,  0,  0,  0 }, // 7
			{  0,  0,  0,  5,  0,  0,  0,  0,  0,  0,  0, 13,  0,  0,  0,  0,  0,  3,  3,  0,  0,  0,  0,  0,  0,  0,  0,  7 }, // 8
			{  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  3,  3,  8,  0,  0,  0,  0,  0,  7,  7,  7 }, // 9
			{  0,  0,  0,  5,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  4,  0,  0,  0,  0,  0,  0,  7,  7,  1,  1 }, // 10
			{  0,  0,  5,  5,  0,  0,  0,  0,  0,  7,  7,  7,  0,  0,  0,  0,  3,  3,  0,  0,  0,  7,  7,  7,  7,  7,  1,  1 }, // 11
			{  9,  9,  5,  5,  0,  0,  0,  0,  7,  7,  7,  0,  0,  8,  0,  3,  3,  3,  0,  7,  1,  1,  7,  7,  7,  1,  1,  1 }, // 12
			{  9,  9,  9,  9,  9,  0,  0,  0,  0,  0,  7,  0,  0,  0,  3,  3,  0,  0,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1 }, // 13
			{  9,  9,  9,  9,  9,  9,  9,  9,  0,  0,  0,  0,  0,  3,  3,  3,  0,  7,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1 }, // 14
			{  9,  9,  9,  9,  9,  9,  9,  9,  9,  0,  0,  0,  7,  3,  3,  0,  7,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1 }, // 15
	};

}
