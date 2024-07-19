package az.test.map;

import az.test.battle.BattleInfo;
import az.test.model.PlayerUnitGenerator;
import az.test.model.army.BaseUnit;
import az.test.model.map.MapItem;
import az.test.reko3ibm.AI02Standby;

import az.test.util.LogUtil;
import com.alibaba.fastjson.JSON;

import java.util.List;

public class BattleMap000TEST001 extends BattleMap {

	public BattleMap000TEST001() {
		super();
		// fill map
		fillingMap(mapIds);
		// name
		battleName = "Battle at TEST_3x3";
		// round
		setRoundLimit(30);
	}

	public void loadEnemies(BattleInfo bi) {
		// load enemies
		BaseUnit bbd = PlayerUnitGenerator.getInstance(bi).loadFootmanArmy(0, 0, 3, new AI02Standby(true));
		bbd.isLord = true;
		bi.map.lord = bbd;
		List<BaseUnit> loaded = bi.loadEnemyUnit(bi.map.lord);
		LogUtil.printlnInfo(bi.map.getCurrentRoundNo(),"loaded enemy " + loaded);
	}

	public static int[][] mapIds = {
			//0, 1, 2
			{ 0, 0, 0 }, // 0
			{ 0, 0, 8 }, // 1
//			{ 0, 0, 7 }, // 2
	};// end

	public static void main(String[] args) {
		MapItem[][] mapItemsTable = new MapItem[mapIds.length][mapIds[0].length];
		for (int y = 0; y < mapIds.length; y++) {
			for (int x = 0; x < mapIds[0].length; x++) {
				mapItemsTable[y][x] = MapItem.generateById(mapIds[y][x], y, x);
			}
		}

		LogUtil.printlnInfo(-1, JSON.toJSONString(mapItemsTable, true));

	}
}
