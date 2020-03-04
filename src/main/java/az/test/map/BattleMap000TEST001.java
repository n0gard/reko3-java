package az.test.map;

import az.test.battle.BattleInfo;
import az.test.model.PlayerUnitGenerator;
import az.test.model.army.BaseUnit;
import az.test.model.map.MapItem;
import az.test.reko3ibm.AI01Active;
import az.test.reko3ibm.AI02Standby;
import az.test.reko3ibm.AI03Passive;

import com.alibaba.fastjson.JSON;

public class BattleMap000TEST001 extends BattleMap {

	public BattleMap000TEST001() {
		super();
		// fill map
		fillingMap(mapIds);
		// name
		battleName = "Battle at TEST2x2";
		// round
		setRoundLimit(30);

	}

	public void loadEnemies(BattleInfo bi) {
		// load enemies
		// BaseUnit huaxiong = PlayerUnitGenerator.loadHuaxiong(9, 3, new
		// AI02Standby(true));
		BaseUnit bbd = PlayerUnitGenerator.loadFootmanArmy(0, 0, 6, new AI02Standby(true));
		bbd.isLord = true;
		bi.map.lord = bbd;
		bi.loadEnemyUnit(bi.map.lord);
		// bi.loadEnemyUnit(PlayerUnitGenerator.loadLisu(10, 5, new
		// AI03Passive(true)));
		// bi.loadEnemyUnit(PlayerUnitGenerator.loadHuzhen(9, 4, new
		// AI03Passive(true)));
		// bi.loadEnemyUnit(PlayerUnitGenerator.loadZhaocen(9, 6, new
		// AI03Passive(true)));
		// bi.loadEnemyUnit(PlayerUnitGenerator.loadFootmanArmy(8, 11, new
		// AI03Passive(true)));
		// bi.loadEnemyUnit(PlayerUnitGenerator.loadFootmanArmy(10, 11, new
		// AI03Passive(true)));
		// bi.loadEnemyUnit(PlayerUnitGenerator.loadFootmanArmy(12, 11, new
		// AI03Passive(true)));
	}

	public static int[][] mapIds = {
			// 0, 1, 2
			{ 0, 0 }, // 0
			{ 0, 7 }, // 1
//			{ 0, 0, 0 }, // 2
	};// end

	public static void main(String[] args) {
		MapItem[][] mapItemsTable = new MapItem[mapIds.length][mapIds[0].length];
		for (int y = 0; y < mapIds.length; y++) {
			for (int x = 0; x < mapIds[0].length; x++) {
				mapItemsTable[y][x] = MapItem.generateById(mapIds[y][x], y, x);
			}
		}

		System.out.println(JSON.toJSONString(mapItemsTable, true));

		System.out.println(String.format("%02d", 1));
	}
}
