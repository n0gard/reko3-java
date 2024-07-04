package az.test.map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import az.test.battle.BattleInfo;
import az.test.model.army.BaseUnit;
import az.test.model.army.BotUnit;
import az.test.model.item.BaseItem;
import az.test.model.item.ItemGenerator;
import az.test.model.map.Abatis;
import az.test.model.map.Barrack;
import az.test.model.map.MapItem;
import az.test.model.map.Village;
import az.test.util.LogUtil;
import az.test.util.ObjectCopyUtil;
import lombok.Data;
import org.lwjgl.system.CallbackI;

@Data
public abstract class BattleMap implements Serializable {
    public String battleName;
    public MapItem[][] map;
    public List<MapItem> restores = new ArrayList<>();
    public List<BotUnit> enemies = new ArrayList<>();
    public List<BotUnit> friends = new ArrayList<>();
    public int roundLimit;
    public int currentRoundNo = 1;
    public BotUnit lord;
    public List<MapItem> escapePlaces = new ArrayList<>();
    public boolean isAllSurvivedUnitGainExtraExp = false;

    public void fillingMap(int[][] mapIds) {
        map = new MapItem[mapIds.length][mapIds[0].length];
        for (int y = 0; y < mapIds.length; y++) {
            for (int x = 0; x < mapIds[y].length; x++) {
                map[y][x] = MapItem.generateById(mapIds[y][x], y, x);
                switch (map[y][x].id) {
                    case 10:

                }
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
        LogUtil.printInfo(getCurrentRoundNo(), "[BattleMap]isPlayerSuccess lord:" + lord);
        if (null != lord && lord.isEvacuated) {
            LogUtil.printInfo(getCurrentRoundNo(), "[BattleMap]lord evacuated");
            return true;
        }
        if (enemies.isEmpty()) {
            LogUtil.printInfo(getCurrentRoundNo(), "[BattleMap]enemies evacuated");
            return true;
        }
        if (areAllEscape(someones)) {
            LogUtil.printInfo(getCurrentRoundNo(), "[BattleMap]someone escaped");
            isAllSurvivedUnitGainExtraExp = true;
            return true;
        }
        if (areAllEscape(anyones)) {
            LogUtil.printInfo(getCurrentRoundNo(), "[BattleMap]anyones all escaped");
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

    public void loadEnemy(BotUnit enemy) {
        enemies.add(enemy);
    }

    public void loadAnyone(BaseUnit anyone) {
        this.anyones.add(anyone);
    }

    public void loadSomeone(BaseUnit someone) {
        this.someones.add(someone);
    }

}
