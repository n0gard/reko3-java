package az.test.map;

import java.util.ArrayList;
import java.util.List;

import az.test.battle.BattleInfo;
import az.test.model.army.BaseUnit;
import az.test.model.map.Abatis;
import az.test.model.map.Barrack;
import az.test.model.map.MapItem;
import az.test.model.map.Village;

public abstract class BattleMap {
	public String battleName;
	public MapItem[][] map;
	public List<MapItem> restores = new ArrayList<MapItem>();
	public List<BaseUnit> enemies = new ArrayList<BaseUnit>();
	public List<BaseUnit> friends = new ArrayList<BaseUnit>();
	private int roundLimit;
	private int currentRoundNo = 1;
	public BaseUnit lord;
	public BaseUnit someone;
	public MapItem escapePlace;

	@Override
	public int hashCode() {
		return currentRoundNo >> 3 | lord.force | lord.intelligence | lord.defense;
	}

	public void fillingMap(int[][] mapIds) {
		map = new MapItem[mapIds.length][mapIds[0].length];
		for (int y = 0; y < mapIds.length; y++) {
			for (int x = 0; x < mapIds[y].length; x++) {
				map[y][x] = MapItem.generateById(mapIds[y][x], y, x);
			}
		}
	}

	public boolean isPlayerSuccess() {
		if (null != lord && lord.isEvacuated) {
			return true;
		}
		if (null != someone && null != escapePlace && someone.currentPositionMap == escapePlace) {
			return true;
		}
		return false;
	}

	public boolean isRunningOutOfRounds() {
		return currentRoundNo >= roundLimit;
	}

	public boolean isRestorePlace(MapItem item) {
		if (item instanceof Village || item instanceof Abatis || item instanceof Barrack) {
			return true;
		}
		return false;
	}

	public void loadEnemies(BattleInfo bi) {

	}

	public void loadSomeone(BaseUnit someone) {
		this.someone = someone;
	}

	public int getCurrentRoundNo() {
		return currentRoundNo;
	}

	public void setCurrentRoundNo(int currentRoundNo) {
		this.currentRoundNo = currentRoundNo;
	}

	public int getRoundLimit() {
		return roundLimit;
	}

	public void setRoundLimit(int roundLimit) {
		this.roundLimit = roundLimit;
	}

}
