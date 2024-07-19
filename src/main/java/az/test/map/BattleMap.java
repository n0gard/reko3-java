package az.test.map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import az.test.battle.BattleInfo;
import az.test.model.army.BaseUnit;
import az.test.model.item.BaseItem;
import az.test.model.item.ItemGenerator;
import az.test.model.map.Abatis;
import az.test.model.map.Barrack;
import az.test.model.map.MapItem;
import az.test.model.map.Village;
import az.test.util.LogUtil;
import lombok.Data;

@Data
public abstract class BattleMap implements Serializable {
    public String battleName;
    public MapItem[][] map;
    public List<MapItem> restores = new ArrayList<>();
    public List<BaseUnit> players = new ArrayList<>();
    public List<BaseUnit> enemies = new ArrayList<>();
    public List<BaseUnit> friends = new ArrayList<>();
    public int roundLimit;
    public int currentRoundNo = 1;
    public BaseUnit lord;
    public List<MapItem> escapePlaces = new ArrayList<>();
    public boolean isAllSurvivedUnitGainExtraExp = false;

    public void fillingMap(int[][] mapIds) {
        map = new MapItem[mapIds.length][mapIds[0].length];
        for (int y = 0; y < mapIds.length; y++) {
            for (int x = 0; x < mapIds[y].length; x++) {
                map[y][x] = MapItem.generateById(mapIds[y][x], y, x);
            }
        }
    }

    public void fillingItem(int y, int x, BaseItem item) {
        map[y][x].item = ItemGenerator.generateItemById(item.id);
    }

    public void fillingGold(int y, int x, int gold) {
        map[y][x].item = ItemGenerator.generateGold(gold);
    }

    public boolean isPlayerSuccess() {
        LogUtil.printlnInfo(getCurrentRoundNo(), "[BattleMap]isPlayerSuccess lord:" + lord);
        if (null != lord && lord.isEvacuated) {
            LogUtil.printlnInfo(getCurrentRoundNo(), "[BattleMap]lord evacuated");
            return true;
        }
        if (enemies.isEmpty()) {
            LogUtil.printlnInfo(getCurrentRoundNo(), "[BattleMap]enemies evacuated");
            return true;
        }
        boolean allEscaped = true;

        List<String> escapedPlayerNames = new ArrayList<>();
        for(BaseUnit player : players) {
            if (player.canEscape) {
                if (player.isEscaped) {
                    escapedPlayerNames.add(player.name);
                } else {
                    allEscaped = false;
                }
            }
        }
        if (allEscaped && !escapedPlayerNames.isEmpty()) {
            LogUtil.printlnInfo(getCurrentRoundNo(), "[BattleMap]" + escapedPlayerNames + " escaped");
            isAllSurvivedUnitGainExtraExp = true;
            return true;
        }
        return false;
    }

    public boolean isRunningOutOfRounds() {
        return currentRoundNo > roundLimit;
    }

    public boolean isRestorePlace(MapItem item) {
        return item instanceof Village || item instanceof Abatis || item instanceof Barrack;
    }

    public abstract void loadEnemies(BattleInfo bi);

    public void loadEnemy(BaseUnit enemy) {
        enemies.add(enemy);
    }

}
