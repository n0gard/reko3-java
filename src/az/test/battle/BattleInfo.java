package az.test.battle;

import java.util.ArrayList;
import java.util.List;

import az.test.battle.enums.Weather;
import az.test.exception.MaxPlayerUnitsLimitedException;
import az.test.map.BattleMap;
import az.test.model.army.BaseUnit;
import az.test.model.army.other.MilitaryBand;
import az.test.model.army.ride.Rider;
import az.test.model.map.MapItem;
import az.test.util.LogUtil;
import az.test.util.RandomHelper;

public class BattleInfo {
    public Weather weather;
    public int lastRoundWeatherCode = 2;
    public List<BaseUnit> playerUnits = new ArrayList<BaseUnit>();
    public List<BaseUnit> outOfBattlePlayerUnits = new ArrayList<BaseUnit>();
    public static final int MAX_PLAYER_UNITS = 20;
    public List<BaseUnit> friendUnits = new ArrayList<BaseUnit>();
    public List<BaseUnit> outOfBattleFriendUnits = new ArrayList<BaseUnit>();
    public List<BaseUnit> enemyUnits = new ArrayList<BaseUnit>();
    public List<BaseUnit> outOfBattleEnemyUnits = new ArrayList<BaseUnit>();
    public BattleMap map;
    private long timestamp = 0L;

    public BattleInfo() {
        super();
        setTimestamp(System.currentTimeMillis());
    }

    @Override
    public int hashCode() {
        return weather.hashCode() >> 3 | map.hashCode();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void addPlayerUnit(BaseUnit bu) throws MaxPlayerUnitsLimitedException {
        if (null == playerUnits) {
            playerUnits = new ArrayList<BaseUnit>();
        }
        if (playerUnits.size() >= MAX_PLAYER_UNITS) {
            throw new MaxPlayerUnitsLimitedException();
        }
        if (playerUnits.contains(bu)) {
            return;
        }
        playerUnits.add(bu);
        map.map[bu.y][bu.x].army = bu;
        bu.currentPositionMap = map.map[bu.y][bu.x];
    }

    public List<BaseUnit> loadEnemyUnit(BaseUnit bu) {
        if (null == enemyUnits) {
            enemyUnits = new ArrayList<BaseUnit>();
        }
        if (enemyUnits.contains(bu)) {
            return enemyUnits;
        }
        enemyUnits.add(bu);
        map.map[bu.y][bu.x].army = bu;
        bu.currentPositionMap = map.map[bu.y][bu.x];
        return enemyUnits;
    }

    public List<BaseUnit> loadFriendUnit(BaseUnit bu) {
        if (null == friendUnits) {
            friendUnits = new ArrayList<BaseUnit>();
        }
        friendUnits.add(bu);
        map.map[bu.y][bu.x].army = bu;
        bu.currentPositionMap = map.map[bu.y][bu.x];
        return friendUnits;
    }

    public void loadMap(BattleMap map) {
        this.map = map;
        this.map.loadEnemies(this);
    }

    public void initRound() {
        System.out.println("[BattleInfo]initRound");
        // round counting
        // map.setCurrentRoundNo(map.getCurrentRoundNo() + 1);
        // init weather
        int currentWeatherCode = Weather.generateNextWeather(lastRoundWeatherCode);
        weather = Weather.parseInt2Weather(currentWeatherCode);
        lastRoundWeatherCode = currentWeatherCode;
        LogUtil.printInfo(map.getCurrentRoundNo(), "Weather: " + weather + "(last:" + lastRoundWeatherCode + ")");
        //
        // caculate restores & chaos restore
        for (BaseUnit player : playerUnits) {
            int hpRestore = 0;
            int moraleRestore = 0;
            if (player.isEvacuated) {
                continue;
            }
            // case 1 - army at restore places
            if (player.currentPositionMap.isRestoreHPPlace() || player.haveRestoreHPItem()) {
                hpRestore = triggerHPRestore(player);
            }
            if (player.currentPositionMap.isRestoreMoralePlace() || player.haveRestoreMoraleItem()) {
                moraleRestore = triggerMoraleRestore(player);
            }
            // case 2 - army around by band(s)
            int beforeMana = player.currentMana;
            List<BaseUnit> bands = isAroundByBands(player.y, player.x);
            for (BaseUnit band : bands) {
                player.currentMana += band.level / 10 + 1;
            }
            if (player.currentMana > player.initMaxMana()) {
                player.currentMana = player.initMaxMana();
            }
            int manaRestore = player.currentMana - beforeMana;
            // case 3 - auto wake up
            boolean isRecoveryFromChaos = false;
            boolean goIn2Determination = false;
            if (player.isInChaos) {
                goIn2Determination = true;
                int recoveryDice = RandomHelper.generateInt(0, 99);
                if (recoveryDice < (player.defense + player.currentMorale) / 3) {
                    player.isInChaos = false;
                    isRecoveryFromChaos = true;
                }
            }
            // case 4 - TODO init place by hand
            if (hpRestore > 0 || moraleRestore > 0 || manaRestore > 0 || goIn2Determination) {
                LogUtil.printInfo(map.getCurrentRoundNo(), "Restore", "Player: " + player.name + " Restored HP: "
                        + hpRestore + " now: " + player.currentArmyHP + " Morale: " + moraleRestore + " now: "
                        + player.currentMorale + " Mana: " + manaRestore + " now: " + player.currentMana
                        + " Recovery? " + isRecoveryFromChaos);
            }
        }
        for (BaseUnit friend : friendUnits) {
            int hpRestore = 0;
            int moraleRestore = 0;
            if (friend.isEvacuated) {
                continue;
            }
            // case 1 - army at restore places
            if (friend.currentPositionMap.isRestoreHPPlace() || friend.haveRestoreHPItem()) {
                hpRestore = triggerHPRestore(friend);
            }
            if (friend.currentPositionMap.isRestoreMoralePlace() || friend.haveRestoreMoraleItem()) {
                moraleRestore = triggerMoraleRestore(friend);
            }
            // case 2 - army around by band(s)
            int beforeMana = friend.currentMana;
            List<BaseUnit> bands = isAroundByBands(friend.y, friend.x);
            for (BaseUnit band : bands) {
                friend.currentMana += band.level / 10 + 1;
            }
            if (friend.currentMana > friend.initMaxMana()) {
                friend.currentMana = friend.initMaxMana();
            }
            int manaRestore = friend.currentMana - beforeMana;
            // case 3 - auto wake up
            boolean isRecoveryFromChaos = false;
            boolean goIn2Determination = false;
            if (friend.isInChaos) {
                goIn2Determination = true;
                int recoveryDice = RandomHelper.generateInt(0, 99);
                if (recoveryDice < (friend.defense + friend.currentMorale) / 3) {
                    friend.isInChaos = false;
                    isRecoveryFromChaos = true;
                }
            }
            // case 4 - TODO init place by hand
            if (hpRestore > 0 || moraleRestore > 0 || manaRestore > 0 || goIn2Determination) {
                LogUtil.printInfo(map.getCurrentRoundNo(), "Restore", "Player: " + friend.name + " Restored HP: "
                        + hpRestore + " Morale: " + moraleRestore + " Mana: " + manaRestore + " Recovery? "
                        + isRecoveryFromChaos);
            }
        }
        for (BaseUnit enemy : enemyUnits) {
            int hpRestore = 0;
            int moraleRestore = 0;
            if (enemy.isEvacuated) {
                continue;
            }
            // case 1 - army at restore places
            if (enemy.currentPositionMap.isRestoreHPPlace() || enemy.haveRestoreHPItem()) {
                hpRestore = triggerHPRestore(enemy);
            }
            if (enemy.currentPositionMap.isRestoreMoralePlace() || enemy.haveRestoreMoraleItem()) {
                moraleRestore = triggerMoraleRestore(enemy);
            }
            // case 2 - army around by band(s)
            int beforeMana = enemy.currentMana;
            List<BaseUnit> bands = isAroundByBands(enemy.y, enemy.x);
            for (BaseUnit band : bands) {
                enemy.currentMana += band.level / 10 + 1;
            }
            if (enemy.currentMana > enemy.initMaxMana()) {
                enemy.currentMana = enemy.initMaxMana();
            }
            int manaRestore = enemy.currentMana - beforeMana;
            // case 3 - auto wake up
            boolean isRecoveryFromChaos = false;
            boolean goIn2Determination = false;
            if (enemy.isInChaos) {
                goIn2Determination = true;
                int recoveryDice = RandomHelper.generateInt(0, 99);
                if (recoveryDice < (enemy.defense + enemy.currentMorale) / 3) {
                    enemy.isInChaos = false;
                    isRecoveryFromChaos = true;
                }
            }
            // case 4 - TODO init place by hand
            if (hpRestore > 0 || moraleRestore > 0 || manaRestore > 0 || goIn2Determination) {
                LogUtil.printInfo(map.getCurrentRoundNo(), "Restore", "Player: " + enemy.name + " Restored HP: "
                        + hpRestore + " Morale: " + moraleRestore + " Mana: " + manaRestore + " Recovery? "
                        + isRecoveryFromChaos);
            }
        }
        // clean round activities
        for (BaseUnit playerUnit : playerUnits) {
            playerUnit.roundFinished = false;
        }
        for (BaseUnit friendUnit : friendUnits) {
            friendUnit.roundFinished = false;
        }
        for (BaseUnit enemyUnit : enemyUnits) {
            enemyUnit.roundFinished = false;
        }
    }

    public void initRound(boolean isPlayer) {
    	if (isPlayer) {
    		
    	} else {
    		for (BaseUnit enemy : enemyUnits) {
                int hpRestore = 0;
                int moraleRestore = 0;
                if (enemy.isEvacuated) {
                    continue;
                }
                // case 1 - army at restore places
                if (enemy.currentPositionMap.isRestoreHPPlace() || enemy.haveRestoreHPItem()) {
                    hpRestore = triggerHPRestore(enemy);
                }
                if (enemy.currentPositionMap.isRestoreMoralePlace() || enemy.haveRestoreMoraleItem()) {
                    moraleRestore = triggerMoraleRestore(enemy);
                }
                // case 2 - army around by band(s)
                int beforeMana = enemy.currentMana;
                List<BaseUnit> bands = isAroundByBands(enemy.y, enemy.x);
                for (BaseUnit band : bands) {
                    enemy.currentMana += band.level / 10 + 1;
                }
                if (enemy.currentMana > enemy.initMaxMana()) {
                    enemy.currentMana = enemy.initMaxMana();
                }
                int manaRestore = enemy.currentMana - beforeMana;
                // case 3 - auto wake up
                boolean isRecoveryFromChaos = false;
                boolean goIn2Determination = false;
                if (enemy.isInChaos) {
                    goIn2Determination = true;
                    int recoveryDice = RandomHelper.generateInt(0, 99);
                    if (recoveryDice < (enemy.defense + enemy.currentMorale) / 3) {
                        enemy.isInChaos = false;
                        isRecoveryFromChaos = true;
                    }
                }
                // case 4 - TODO init place by hand
                if (hpRestore > 0 || moraleRestore > 0 || manaRestore > 0 || goIn2Determination) {
                    LogUtil.printInfo(map.getCurrentRoundNo(), "Restore", "Player: " + enemy.name + " Restored HP: "
                            + hpRestore + " Morale: " + moraleRestore + " Mana: " + manaRestore + " Recovery? "
                            + isRecoveryFromChaos);
                }
            }
            // clean round activities
            for (BaseUnit enemyUnit : enemyUnits) {
                enemyUnit.roundFinished = false;
            }
    	}
    }

    public int triggerHPRestore(BaseUnit army) {
        int restoreHP = 150 + RandomHelper.generateInt(0, 10) * 10;
        int beforeHP = army.currentArmyHP;
        army.currentArmyHP += restoreHP;
        if (army.currentArmyHP > army.initMaxArmyHP()) {
            army.currentArmyHP = army.initMaxArmyHP();
        }
        if (army.initMaxArmyHP() - army.currentArmyHP <= 9) {
            army.currentArmyHP = army.initMaxArmyHP();
            restoreHP = army.initMaxArmyHP() - beforeHP;
        }
        return restoreHP;
    }

    public int triggerMoraleRestore(BaseUnit army) {
        int restoreMorale = army.defense / 10 + RandomHelper.generateInt(1, 5);
        int beforeMorale = army.currentMorale;
        army.currentMorale += restoreMorale;
        if (army.currentMorale > 100) {
            army.currentMorale = 100;
        }
        if (100 - army.currentArmyHP <= 9) {
            army.currentMorale = 100;
            restoreMorale = 100 - beforeMorale;
        }
        return restoreMorale;
    }

    public List<BaseUnit> isAroundByBands(int y, int x) {
        List<BaseUnit> bands = new ArrayList<BaseUnit>();
        BaseUnit bandNorth = queryUnitByCoordinate(y - 1, x);
        if (null != bandNorth && bandNorth instanceof MilitaryBand) {
            bands.add(bandNorth);
        }
        BaseUnit bandWest = queryUnitByCoordinate(y, x - 1);
        if (null != bandWest && bandWest instanceof MilitaryBand) {
            bands.add(bandWest);
        }
        BaseUnit bandSouth = queryUnitByCoordinate(y + 1, x);
        if (null != bandSouth && bandSouth instanceof MilitaryBand) {
            bands.add(bandSouth);
        }
        BaseUnit bandEast = queryUnitByCoordinate(y, x + 1);
        if (null != bandEast && bandEast instanceof MilitaryBand) {
            bands.add(bandEast);
        }
        return bands;
    }

    public BaseUnit queryEnemyUnitByCoordinate(int y, int x) {
        for (BaseUnit u : enemyUnits) {
            if (u.y == y && u.x == x) {
                return u;
            }
        }
        return null;
    }

    public BaseUnit queryUnitByCoordinate(int y, int x) {
        if (y < 0 || x < 0 || y >= map.map.length || x >= map.map[0].length) {
            return null;
        }
        return map.map[y][x].army;
        // List<BaseUnit> allUnits = new ArrayList<BaseUnit>();
        // allUnits.addAll(enemyUnits);
        // allUnits.addAll(friendUnits);
        // allUnits.addAll(playerUnits);
        // for (BaseUnit u : allUnits) {
        // if (u.y == y && u.x == x) {
        // return u;
        // }
        // }
        // return null;
    }

    public boolean canIStandHere(BaseUnit army, int y, int x) {
        if (y >= map.map.length || x >= map.map[0].length) {
            return false;
        }
        MapItem mapItem = map.map[y][x];
        if (army.moveAbility - mapItem.cost < 0) {
            return false;
        }
        switch (mapItem.id) {
        case 0:
        case 1:
        case 2:
        case 3:
        case 5:
        case 7:
        case 8:
        case 9:
        case 10:
        case 11:
            return true;
        case 4:
            if (army instanceof Rider) {
                return false;
            }
            return true;
        case 12:
        case 13:
        case 14:
        case 15:
        case 16:
        case 17:
            return false;
        default:
            return false;
        }
    }

    public MapItem getNorth(int y, int x) {
        if (y - 1 >= 0) {
            return map.map[y - 1][x];
        }
        return null;
    }

    public MapItem getEast(int y, int x) {
        if (x + 1 < map.map[0].length) {
            return map.map[y][x + 1];
        }
        return null;
    }

    public MapItem getSouth(int y, int x) {
        if (y + 1 < map.map.length) {
            return map.map[y + 1][x];
        }
        return null;
    }

    public MapItem getWest(int y, int x) {
        if (x - 1 >= 0) {
            return map.map[y][x - 1];
        }
        return null;
    }

    public boolean areAllPlayersEvacuatedOrSomeoneEvacuated() {
        if (null != map.someone) {
            return map.someone.isEvacuated;
        } else {
            boolean allEvacuated = true;
            for (BaseUnit playerUnit : playerUnits) {
                if (!playerUnit.isEvacuated) {
                    allEvacuated = false;
                }
            }
            System.out.println("[BattleMap]areAllPlayersEvacuatedOrSomeoneEvacuated   someone not set yet.");
            return allEvacuated;
        }
    }

    public static void main(String[] args) {
        // int last = (int) (Math.random() * 6.0);
        int last = 2;
        System.out.println("INIT LAST: " + last);
        int current = last;
        int sun = 0;
        int cloud = 0;
        int rain = 0;
        int imp = 0;
        int c0 = 0;
        int c1 = 0;
        int c2 = 0;
        int c3 = 0;
        int c4 = 0;
        int c5 = 0;
        for (int i = 0; i < 2560; i++) {
            current = Weather.generateNextWeather(last);
            switch (current) {
            case 0:
                c0++;
                break;
            case 1:
                c1++;
                break;
            case 2:
                c2++;
                break;
            case 3:
                c3++;
                break;
            case 4:
                c4++;
                break;
            case 5:
                c5++;
                break;
            }
            Weather w = Weather.parseInt2Weather(current);
            switch (w) {
            case SUN:
                sun++;
                break;
            case CLOUD:
                cloud++;
                break;
            case RAIN:
                rain++;
                break;
            default:
                System.out.println("IMPOSSIBLE");
                imp++;
                break;
            }
            last = current;
        }
        System.out.println("--------- SUN: " + sun + " --- CLOUD: " + cloud + " --- RAIN: " + rain + " ------ IMP: "
                + imp + " ----------");
        System.out.println("c0: " + c0 + " c1: " + c1 + " c2:" + c2 + " c3: " + c3 + " c4: " + c4 + " c5: " + c5);
    }
}
