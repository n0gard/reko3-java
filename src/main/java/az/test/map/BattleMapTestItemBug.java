package az.test.map;

import az.test.battle.BattleInfo;
import az.test.model.PlayerUnitGenerator;
import az.test.model.army.BotUnit;
import az.test.model.map.MapItem;
import az.test.reko3ibm.AI02Standby;
import az.test.util.LogUtil;
import com.alibaba.fastjson.JSON;

import java.util.List;

public class BattleMapTestItemBug extends BattleMap {

	public BattleMapTestItemBug() {
		super();
		// fill map
		fillingMap(mapIds);
		// name
		battleName = "Battle at TEST_2x3";
		// round
		setRoundLimit(30);
	}

	public void loadEnemies(BattleInfo bi) {
		// load enemies
		BotUnit cc = PlayerUnitGenerator.getInstance(bi).loadCaocao(0, 0,  new AI02Standby(true));
		cc.isLord = true;
		bi.map.lord = cc;
		List<BotUnit> loaded = bi.loadEnemyUnit(bi.map.lord);
		LogUtil.printInfo(bi.map.getCurrentRoundNo(),"loaded enemy " + loaded);
	}

	public static int[][] mapIds = {
			//0, 1, 2
			{ 0, 0, 0 }, // 0
			{ 0, 0, 7 }, // 1
//			{ 0, 0, 7 }, // 2
	};// end

	public static void main(String[] args) {
		MapItem[][] mapItemsTable = new MapItem[mapIds.length][mapIds[0].length];
		for (int y = 0; y < mapIds.length; y++) {
			for (int x = 0; x < mapIds[0].length; x++) {
				mapItemsTable[y][x] = MapItem.generateById(mapIds[y][x], y, x);
			}
		}

		LogUtil.printInfo(-1, JSON.toJSONString(mapItemsTable, true));

	}
}
