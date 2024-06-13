package az.test.map;

import az.test.battle.BattleInfo;
import az.test.model.PlayerUnitGenerator;
import az.test.model.army.BaseUnit;
import az.test.model.army.BotUnit;
import az.test.model.map.MapItem;
import az.test.reko3ibm.AI02Standby;
import az.test.reko3ibm.AI03Passive;

import com.alibaba.fastjson.JSON;

public class BattleMap01 extends BattleMap {

	public BattleMap01() {
		super();
		// fill map
		fillingMap(mapIds);
		// name
		battleName = "汜水关";
		// round
		setRoundLimit(30);

	}

	public void loadEnemies(BattleInfo bi) {
		// load enemies
//		BaseUnit huaxiong = PlayerUnitGenerator.loadHuaxiong(9, 3, new AI02Standby(true));
//		BaseUnit huaxiong = PlayerUnitGenerator.loadHuaxiong(10, 15, new AI02Standby(true));
	    BotUnit bbd1 = PlayerUnitGenerator.getInstance(bi).loadFootmanArmy(8, 11, 1, new AI03Passive(true));
		bbd1.isLord = true;
		bi.map.lord = bbd1;
		bi.loadEnemyUnit(bi.map.lord);
//		bi.loadEnemyUnit(PlayerUnitGenerator.loadLisu(10, 5, new AI03Passive(true)));
//		bi.loadEnemyUnit(PlayerUnitGenerator.loadHuzhen(9, 4, new AI03Passive(true)));
//		bi.loadEnemyUnit(PlayerUnitGenerator.loadZhaocen(9, 6, new AI03Passive(true)));
//		bi.loadEnemyUnit(PlayerUnitGenerator.loadFootmanArmy(8, 11, new AI03Passive(true)));
//		bi.loadEnemyUnit(PlayerUnitGenerator.loadFootmanArmy(10, 11, new AI03Passive(true)));
//		bi.loadEnemyUnit(PlayerUnitGenerator.loadFootmanArmy(12, 11, new AI03Passive(true)));
	}

	public static int[][] mapIds = {
			// 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27
			{ 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 0, 4, 4, 4, 4, 4, 4, 4, 0, 0 }, // 0
			{ 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 0, 2, 4, 4, 4, 4, 2, 0, 12, 12 }, // 1
			{ 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 0, 0, 4, 4, 4, 2, 0, 12, 12, 12 }, // 2
			{ 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 0, 0, 2, 2, 0, 0, 12, 12, 12, 0 }, // 3
			{ 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 0, 0, 0, 0, 0, 12, 12, 12, 0, 2 }, // 4
			{ 13, 13, 14, 14, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 0, 0, 0, 0, 12, 12, 12, 0, 0, 0, 2 }, // 5
			{ 0, 0, 14, 14, 0, 0, 0, 13, 13, 5, 5, 5, 11, 5, 5, 5, 0, 0, 0, 0, 12, 12, 12, 0, 0, 0, 0, 0 }, // 6
			{ 0, 0, 14, 14, 0, 5, 10, 5, 5, 0, 5, 5, 5, 5, 0, 0, 0, 0, 12, 12, 0, 0, 0, 0, 0, 0, 0, 0 }, // 7
			{ 0, 0, 0, 14, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 12, 12, 0, 0, 0, 0, 0, 0, 0, 0, 2 }, // 8
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 12, 7, 0, 0, 0, 0, 0, 2, 2, 2 }, // 9
			{ 0, 0, 0, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 2, 4, 4 }, // 10
			{ 0, 0, 14, 14, 0, 0, 0, 0, 2, 2, 2, 2, 0, 0, 0, 0, 12, 12, 0, 2, 2, 2, 2, 2, 2, 2, 4, 4 }, // 11
			{ 13, 0, 14, 14, 0, 0, 0, 0, 2, 2, 2, 2, 0, 7, 0, 12, 12, 12, 0, 2, 4, 4, 2, 2, 2, 4, 4, 4 }, // 11
			{ 13, 13, 14, 14, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 12, 0, 0, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 }, // 13
			{ 13, 13, 13, 13, 13, 13, 13, 13, 0, 0, 0, 0, 0, 0, 12, 12, 0, 2, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 }, // 14
			{ 13, 13, 13, 13, 13, 13, 13, 13, 13, 0, 0, 0, 2, 12, 12, 0, 2, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 } };// 15

	public static void main(String[] args) {
		MapItem[][] mapItemsTable = new MapItem[16][28];
		for (int y = 0; y < 16; y++) {
			for (int x = 0; x < 28; x++) {
				mapItemsTable[y][x] = MapItem.generateById(mapIds[y][x], y, x);
			}
		}

		System.out.println(JSON.toJSONString(mapItemsTable, true));

		System.out.printf("%02d%n", 1);
	}
}
