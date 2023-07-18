package az.test.model.army;

import java.util.ArrayList;
import java.util.List;

import az.test.battle.BattleInfo;
import az.test.exception.CounterattackHappenedException;
import az.test.exception.MaxItemsLimitedException;
import az.test.exception.OutOfAttackRangeException;
import az.test.exception.OutOfMoveRangeException;
import az.test.model.army.bow.Bow;
import az.test.model.army.bow.BowSoilder;
import az.test.model.army.bow.Liannu;
import az.test.model.army.bow.StoneCar;
import az.test.model.army.foot.Chariot;
import az.test.model.army.foot.Footman;
import az.test.model.army.foot.LongArmed;
import az.test.model.army.foot.ShortArmed;
import az.test.model.army.other.Barbarian;
import az.test.model.army.other.BeastArmy;
import az.test.model.army.other.MartialArtist;
import az.test.model.army.ride.LightRide;
import az.test.model.army.ride.Rider;
import az.test.model.army.theif.Theif;
import az.test.model.item.Book;
import az.test.model.item.Commandment;
import az.test.model.item.ImperialJadeSeal;
import az.test.model.item.Item;
import az.test.model.item.SupportReport;
import az.test.model.item.Weapon;
import az.test.model.map.Abatis;
import az.test.model.map.Barrack;
import az.test.model.map.Forest;
import az.test.model.map.Grassland;
import az.test.model.map.MapItem;
import az.test.model.map.Mountain;
import az.test.model.map.Village;
import az.test.reko3ibm.Action;
import az.test.reko3ibm.ActionAIType;
import az.test.util.LogUtil;
import az.test.util.RandomHelper;

public class BaseUnit {
    // personal attributes
    public String name;
    public int level;
    public int exp;
    public int intelligence;
    public int force;
    public int defense;
    // bag
    public List<Item> items = new ArrayList<Item>();
    public static final int MAX_ITEM_LIMIT = 8;
    // army attributes
    public int armyHPBase;
    public int armyHPInc;
    public int currentArmyHP;
    public int currentMorale;
    public int currentMana;
    public int apBase;
    public int dpBase;

    public int moveAbility;
    public List<MapItem> canMoveToCoordinateRange = new ArrayList<MapItem>();

    // current position related to battle map
    public int x;
    public int y;
    public MapItem currentPositionMap;

    public boolean isInChaos;
    public boolean roundFinished;
    public boolean isEvacuated;
    public boolean isLord;

    // auto action stuff
    public ActionAIType aiType;
    // special target
    public BaseUnit specialEnemy;

    public List<Item> addItem(Item item) throws MaxItemsLimitedException {
        if (null == items) {
            items = new ArrayList<Item>();
        }
        if (8 <= items.size()) {
            throw new MaxItemsLimitedException();
        }
        items.add(item);
        return items;
    }

    public boolean haveRestoreHPItem() {
        for (Item item : items) {
            if (item instanceof ImperialJadeSeal || item instanceof SupportReport) {
                return true;
            }
        }
        return false;
    }

    public boolean haveRestoreMoraleItem() {
        for (Item item : items) {
            if (item instanceof ImperialJadeSeal || item instanceof Commandment) {
                return true;
            }
        }
        return false;
    }

    public int initMaxArmyHP() {
        return armyHPBase + armyHPInc * (level - 1);
    }

    public int initMaxMana() {
        return (level + 10) * intelligence * 5 / 200;
    }

    public int caculateAP() {
        return (int) (((4000 / (140 - force) + apBase * 2 + currentMorale) * (level + 10) / 10)
                * (100 + queryItemAPInc()) / 100);
    }

    public int caculateDP() {
        return (int) (((4000 / (140 - defense) + dpBase * 2 + currentMorale) * (level + 10) / 10)
                * (100 + queryItemDPInc()) / 100);
    }

    public int queryItemAPInc() {
        int inc = 0;
        for (Item i : items) {
            if (i instanceof Weapon) {
                Weapon w = (Weapon) i;
                inc = w.apIncreasementPercentage;
            }
        }
        return inc;
    }

    public int queryItemDPInc() {
        int inc = 0;
        for (Item i : items) {
            if (i instanceof Book) {
                Book b = (Book) i;
                inc = b.dpIncreasementPercentage;
            }
        }
        return inc;
    }

    public void gainExp(int value) {
        this.exp += value;
        if (this.exp >= 100) {
            levelUp();
        }
    }

    public void levelUp() {
        this.level++;
        this.exp -= 100;
    }

    /** ===============================ACTIONS================================ */

    public void moveTo(BattleInfo battle, int targetY, int targetX, boolean isSim) {
        // canMoveToCoordinateRange = new ArrayList<MapItem>();
        LogUtil.printLog(battle.map.getCurrentRoundNo(), "move", this.name, " move to " + targetY + "," + targetX,
                " from coordinate: " + this.y + "," + this.x + " to " + targetY + "," + targetX + " moveAb: "
                        + this.moveAbility);
        // calculateMoveRange(battle, this.moveAbility, this.y, this.x);
        // System.out.println(canMoveToCoordinateRange);
        if (!isSim)
        drawMap(battle, targetY, targetX);
        // if (!containsCoordinate(canMoveToCoordinateRange, targetY, targetX))
        // {
        // throw new OutOfMoveRangeException(this,
        // battle.map.map[targetY][targetX]);
        // }
        battle.map.map[this.y][this.x].army = null;
        this.y = targetY;
        this.x = targetX;
        battle.map.map[this.y][this.x].army = this;
        this.currentPositionMap = battle.map.map[this.y][this.x];
    }

    public void drawMap(BattleInfo battle, int targetY, int targetX) {
        System.out.print("[Round " + battle.map.getCurrentRoundNo() + "][move]   ");
        for (int len = 0; len < battle.map.map[0].length; len++) {
            System.out.print(String.format("%02d", len) + " ");
        }
        System.out.println();
        System.out.print("[Round " + battle.map.getCurrentRoundNo() + "][move]   ");
        for (int len = 0; len < battle.map.map[0].length; len++) {
            System.out.print("___");
        }
        System.out.println();

        for (int yyy = 0; yyy < battle.map.map.length; yyy++) {
            LogUtil.printInfoWithNoReturn(battle.map.getCurrentRoundNo(), "move", String.format("%02d", yyy) + "|");
            for (int xxx = 0; xxx < battle.map.map[yyy].length; xxx++) {
                MapItem mi = battle.map.map[yyy][xxx];
                BaseUnit army = mi.army;
                int mapItemId = mi.id;
                // draw whole map range
                if (containsMapItem(canMoveToCoordinateRange, mi) && -1 != targetY) {
                    if (null != army) {
                        switch (mapItemId) {
                        case 0:
                            System.out.print(".");
                            break;
                        case 2:
                            System.out.print(",");
                            break;
                        case 3:
                            System.out.print("=");
                            break;
                        case 4:
                            System.out.print("F");
                            break;
                        case 5:
                            System.out.print("*");
                            break;
                        case 7:
                            System.out.print("v");
                            break;
                        case 8:
                            System.out.print("a");
                            break;
                        case 12:
                            System.out.print("~~ ");
                            break;
                        case 13:
                            System.out.print("## ");
                            break;
                        case 14:
                            System.out.print("WW ");
                            break;
                        default:
                            if (mapItemId > 9) {
                                System.out.print(mapItemId + " ");
                            } else {
                                System.out.print(mapItemId);
                            }
                            break;
                        }

                        if (mi.y == this.y && mi.x == this.x) {
                            System.out.print("@ ");
                        } else if (battle.enemyUnits.contains(army)) {
                            System.out.print("e ");
                        } else if (battle.friendUnits.contains(army)) {
                            System.out.print("f ");
                        } else if (battle.playerUnits.contains(army)) {
                            System.out.print("p ");
                        } else {
                            System.out.print("? ");
                        }
                    } else {
                        System.out.print("m");
                        if (mi.y == targetY && mi.x == targetX) {
                            System.out.print("T ");
                        } else {
                            System.out.print("  ");
                        }
                    }
                }
                // out of range
                else {
                    switch (mapItemId) {
                    case 0:
                        System.out.print(".");
                        break;
                    case 2:
                        System.out.print(",");
                        break;
                    case 3:
                        System.out.print("=");
                        break;
                    case 4:
                        System.out.print("F");
                        break;
                    case 5:
                        System.out.print("*");
                        break;
                    case 7:
                        System.out.print("v");
                        break;
                    case 8:
                        System.out.print("a");
                        break;
                    case 12:
                        System.out.print("~~ ");
                        break;
                    case 13:
                        System.out.print("## ");
                        break;
                    case 14:
                        System.out.print("WW ");
                        break;
                    default:
                        if (mapItemId > 9) {
                            System.out.print(mapItemId + " ");
                        } else {
                            System.out.print(mapItemId);
                        }
                        break;
                    }

                    if (null != army) {
                        if (battle.enemyUnits.contains(army)) {
                            System.out.print("e ");
                        } else if (battle.map.friends.contains(army)) {
                            System.out.print("f ");
                        } else if (battle.playerUnits.contains(army)) {
                            System.out.print("p ");
                        } else {
                            System.out.print("? ");
                        }
                    } else {
                        switch (mapItemId) {
                        case 0:
                            System.out.print(". ");
                            continue;
                        case 2:
                            System.out.print(", ");
                            continue;
                        case 3:
                            System.out.print("= ");
                            continue;
                        case 4:
                            System.out.print("F ");
                            continue;
                        case 5:
                            System.out.print("* ");
                            continue;
                        case 7:
                            System.out.print("v ");
                            continue;
                        case 8:
                            System.out.print("a ");
                            break;
                        }
                    }
                }
            }
            System.out.println();
        }
        System.out.print("[Round " + battle.map.getCurrentRoundNo() + "][move]   ");
        for (

        int len = 0; len < battle.map.map[0].length; len++) {
            System.out.print("---");
        }
        System.out.println();
    }

    public void drawMap(BattleInfo battle, String action, int targetY, int targetX) {
        System.out.print("[Round " + battle.map.getCurrentRoundNo() + "][" + action + "]   ");
        for (int len = 0; len < battle.map.map[0].length; len++) {
            System.out.print(String.format("%02d", len) + " ");
        }
        System.out.println();
        System.out.print("[Round " + battle.map.getCurrentRoundNo() + "][" + action + "]   ");
        for (int len = 0; len < battle.map.map[0].length; len++) {
            System.out.print("___");
        }
        System.out.println();

        for (int yyy = 0; yyy < battle.map.map.length; yyy++) {
            LogUtil.printInfoWithNoReturn(battle.map.getCurrentRoundNo(), action, String.format("%02d", yyy) + "|");
            for (int xxx = 0; xxx < battle.map.map[yyy].length; xxx++) {
                MapItem mi = battle.map.map[yyy][xxx];
                BaseUnit army = mi.army;
                // draw whole map
                //
                int mapItemId = mi.id;
                switch (mapItemId) {
                case 0:
                    System.out.print(".");
                    break;
                case 2:
                    System.out.print(",");
                    break;
                case 3:
                    System.out.print("=");
                    break;
                case 4:
                    System.out.print("F");
                    break;
                case 5:
                    System.out.print("*");
                    break;
                case 7:
                    System.out.print("v");
                    break;
                case 8:
                    System.out.print("a");
                    break;
                case 12:
                    System.out.print("~~ ");
                    break;
                case 13:
                    System.out.print("## ");
                    break;
                case 14:
                    System.out.print("WW ");
                    break;
                default:
                    if (mapItemId > 9) {
                        System.out.print(mapItemId + " ");
                    } else {
                        System.out.print(mapItemId);
                    }
                    break;
                }

                if (null != army) {
                    if (battle.enemyUnits.contains(army)) {
                        System.out.print("e");
                    } else if (battle.map.friends.contains(army)) {
                        System.out.print("f");
                    } else if (battle.playerUnits.contains(army)) {
                        System.out.print("p");
                    } else {
                        System.out.print("?");
                    }

                    if (targetY == yyy && targetX == xxx) {
                        System.out.print("<");
                    } else if (this.y == yyy && this.x == xxx) {
                        System.out.print(">");
                    } else {
                        System.out.print(" ");
                    }
                } else {
                    switch (mapItemId) {
                    case 0:
                        System.out.print(". ");
                        continue;
                    case 2:
                        System.out.print(", ");
                        continue;
                    case 3:
                        System.out.print("= ");
                        continue;
                    case 4:
                        System.out.print("F ");
                        continue;
                    case 5:
                        System.out.print("* ");
                        continue;
                    case 7:
                        System.out.print("v ");
                        continue;
                    case 8:
                        System.out.print("a ");
                        break;
                    }
                }
            }
            System.out.println();
        }
        System.out.print("[Round " + battle.map.getCurrentRoundNo() + "][" + action + "]   ");
        for (

        int len = 0; len < battle.map.map[0].length; len++) {
            System.out.print("---");
        }
        System.out.println();
    }

    private boolean containsMapItem(List<MapItem> list, MapItem mi) {
        for (MapItem item : list) {
            if (item.y == mi.y && item.x == mi.x) {
                return true;
            }
        }
        return false;
    }

    private boolean existOtherArmy(BattleInfo battle, MapItem mi) {
        if (null == mi.army) {
            return false;
        } else {
            if (mi.army.y == y && mi.army.x == x) {
                return false;
            } else {
                return true;
            }
        }
    }

    private boolean isSurroundByOppositeArmy(BattleInfo battle, int y, int x) {
        if (y - 1 >= 0) {
            MapItem northItem = battle.map.map[y - 1][x];
            if (existOtherArmy(battle, northItem)) {
                return true;
            }
        }
        if (x + 1 < battle.map.map[0].length) {
            MapItem eastItem = battle.map.map[y][x + 1];
            if (existOtherArmy(battle, eastItem)) {
                return true;
            }
        }
        if (y + 1 < battle.map.map.length) {
            MapItem southItem = battle.map.map[y + 1][x];
            if (existOtherArmy(battle, southItem)) {
                return true;
            }
        }
        if (x - 1 >= 0) {
            MapItem westItem = battle.map.map[y][x - 1];
            if (existOtherArmy(battle, westItem)) {
                return true;
            }
        }
        return false;
    }

    public void calculateMoveRange(BattleInfo battle, int moveAbility, int nowY, int nowX) {
        if (this.isInChaos || this.roundFinished) {
            return;
        }
        MapItem c = battle.map.map[nowY][nowX];
        // add current position
        if (!containsMapItem(canMoveToCoordinateRange, c)) {
            canMoveToCoordinateRange.add(c);
        }
        if (isSurroundByOppositeArmy(battle, nowY, nowX) && this.y != nowY && this.x != nowX) {
            return;
        }
        if (moveAbility > 0) {
            int targetY = nowY - 1;
            int targetX = nowX;
            if (targetY >= 0) {
                if (battle.canIStandHere(this, targetY, targetX)) {
                    MapItem mapItemNorth = battle.map.map[targetY][targetX];
                    // no army or no opposite army
                    if (null == mapItemNorth.army || !existOtherArmy(battle, mapItemNorth)) {
                        if (!containsMapItem(canMoveToCoordinateRange, mapItemNorth)) {
                            canMoveToCoordinateRange.add(mapItemNorth);
                        }
                        calculateMoveRange(battle, moveAbility - mapItemNorth.queryCost(this), mapItemNorth.y,
                                mapItemNorth.x);
                    }
                    // army and exist opposite army
                    else {
                    }
                }
            }
            targetY = nowY;
            targetX = nowX + 1;
            if (targetX <= battle.map.map[0].length) {
                if (battle.canIStandHere(this, targetY, targetX)) {
                    MapItem mapItemEast = battle.map.map[targetY][targetX];
                    if (null == mapItemEast.army || !existOtherArmy(battle, mapItemEast)) {
                        if (!containsMapItem(canMoveToCoordinateRange, mapItemEast)) {
                            canMoveToCoordinateRange.add(mapItemEast);
                        }
                        calculateMoveRange(battle, moveAbility - mapItemEast.queryCost(this), mapItemEast.y,
                                mapItemEast.x);
                    } else {
                    }
                }
            }
            targetY = nowY + 1;
            targetX = nowX;
            if (targetY <= battle.map.map.length) {
                if (battle.canIStandHere(this, targetY, targetX)) {
                    MapItem mapItemSouth = battle.map.map[targetY][targetX];
                    if (null == mapItemSouth.army || !existOtherArmy(battle, mapItemSouth)) {
                        if (!containsMapItem(canMoveToCoordinateRange, mapItemSouth)) {
                            canMoveToCoordinateRange.add(mapItemSouth);
                        }
                        calculateMoveRange(battle, moveAbility - mapItemSouth.queryCost(this), mapItemSouth.y,
                                mapItemSouth.x);
                    } else {
                    }
                }
            }
            targetY = nowY;
            targetX = nowX - 1;
            if (targetX >= 0) {
                if (battle.canIStandHere(this, targetY, targetX)) {
                    MapItem mapItemWest = battle.map.map[targetY][targetX];
                    if (null == mapItemWest.army || !existOtherArmy(battle, mapItemWest)) {
                        if (!containsMapItem(canMoveToCoordinateRange, mapItemWest)) {
                            canMoveToCoordinateRange.add(mapItemWest);
                        }
                        calculateMoveRange(battle, moveAbility - mapItemWest.queryCost(this), mapItemWest.y,
                                mapItemWest.x);
                    } else {
                    }
                }
            }
        }
    }

    public boolean containsCoordinate(List<MapItem> coordinates, int y, int x) {
        for (MapItem c : coordinates) {
            if (c.y == y && c.x == x) {
                return true;
            }
        }
        return false;
    }

    public BaseUnit calculateCurrentPositionAttackTarget(BattleInfo battle) {
        if (this instanceof LightRide || this instanceof ShortArmed) {
            // north
            int northY = this.y - 1;
            int northX = this.x;
            if (northY >= 0) {
                if (null != battle.map.map[northY][northX].army
                        && battle.playerUnits.contains(battle.map.map[northY][northX].army)) {
                    return battle.map.map[northY][northX].army;
                }
            }
            // east
            int eastY = this.y;
            int eastX = this.x + 1;
            if (eastX < battle.map.map[0].length) {
                if (null != battle.map.map[eastY][eastX].army
                        && battle.playerUnits.contains(battle.map.map[eastY][eastX].army)) {
                    return battle.map.map[eastY][eastX].army;
                }
            }
            // south
            int southY = this.y + 1;
            int southX = this.x;
            if (southY < battle.map.map.length) {
                if (null != battle.map.map[southY][southX].army
                        && battle.playerUnits.contains(battle.map.map[southY][southX].army)) {
                    return battle.map.map[southY][southX].army;
                }
            }
            // west
            int westY = this.y;
            int westX = this.x - 1;
            if (westX >= 0) {
                if (null != battle.map.map[westY][westX].army
                        && battle.playerUnits.contains(battle.map.map[westY][westX].army)) {
                    return battle.map.map[westY][westX].army;
                }
            }
        }
        return null;
    }

    public BaseUnit calculateAssignedPositionAttackTarget(BattleInfo battle, int y, int x, int currentY, int currentX) {
        // System.out.println("[BaseUnit] calculateAssignedPositionAttackTarget
        // y: " + y + ", x: " + x);
        boolean isPlayer = false;
        if (battle.playerUnits.contains(this)) {
            isPlayer = true;
        }
        BaseUnit target = null;
        if (this instanceof LightRide || this instanceof ShortArmed) {
            // north
            int northY = y - 1;
            int northX = x;
            if (northY >= 0) {
                if (northY == currentY && northX == currentX) {
                    // System.out.println("[BaseUnit]
                    // calculateAssignedPositionAttackTarget NORTH IS SELF ARMY
                    // !!!");
                } else {
                    if (null != battle.map.map[northY][northX].army) {
                        if (isPlayer) {
                            if (battle.enemyUnits.contains(battle.map.map[northY][northX].army)) {
                                target = battle.map.map[northY][northX].army;
                            }
                        } else {
                            if (battle.playerUnits.contains(battle.map.map[northY][northX].army)
                                    || battle.friendUnits.contains(battle.map.map[northY][northX].army)) {
                                target = battle.map.map[northY][northX].army;
                            }
                        }
                    }
                }
            }
            // east
            int eastY = y;
            int eastX = x + 1;
            if (eastX < battle.map.map[0].length) {
                if (eastY == currentY && eastX == currentX) {
                    // System.out
                    // .println("[BaseUnit]
                    // calculateAssignedPositionAttackTarget EAST POSITION IS
                    // SELF ARMY !!!");
                } else {
                    if (null != battle.map.map[eastY][eastX].army)
                        if (isPlayer) {
                            if (battle.enemyUnits.contains(battle.map.map[eastY][eastX].army)) {
                                target = battle.map.map[eastY][eastX].army;
                            }
                        } else {
                            if (battle.playerUnits.contains(battle.map.map[eastY][eastX].army)
                                    || battle.friendUnits.contains(battle.map.map[eastY][eastX].army)) {
                                target = battle.map.map[eastY][eastX].army;
                            }
                        }
                }
            }
        }
        // south
        int southY = y + 1;
        int southX = x;
        if (southY < battle.map.map.length) {
            if (southY == currentY && southX == currentX) {
                // System.out.println("[BaseUnit]
                // calculateAssignedPositionAttackTarget SOUTH POSITION IS SELF
                // ARMY !!!");
            } else {
                if (null != battle.map.map[southY][southX].army) {
                    if (isPlayer) {
                        if (battle.enemyUnits.contains(battle.map.map[southY][southX].army)) {
                            target = battle.map.map[southY][southX].army;
                        }
                    } else {
                        if (battle.playerUnits.contains(battle.map.map[southY][southX].army)
                                || battle.friendUnits.contains(battle.map.map[southY][southX].army)) {
                            target = battle.map.map[southY][southX].army;
                        }
                    }
                }
            }
        }
        // west
        int westY = y;
        int westX = x - 1;
        if (westX >= 0) {
            if (westY == currentY && westX == currentX) {
                // System.out.println("[BaseUnit]
                // calculateAssignedPositionAttackTarget WEST POSITION IS SELF
                // ARMY !!!");
            } else {
                if (null != battle.map.map[westY][westX].army) {
                    if (isPlayer) {
                        if (battle.enemyUnits.contains(battle.map.map[westY][westX].army)) {
                            target = battle.map.map[westY][westX].army;
                        }
                    } else {
                        if (battle.playerUnits.contains(battle.map.map[westY][westX].army)
                                || battle.friendUnits.contains(battle.map.map[westY][westX].army)) {
                            target = battle.map.map[westY][westX].army;
                        }
                    }
                }
            }
        }
        return target;
    }

    /**
     * 
     * @param target
     * @param isCounterAttack
     * @param enemyAttack
     * @throws OutOfAttackRangeException
     * @throws CounterattackHappenedException
     */
    public void attack(BattleInfo battle, BaseUnit target, boolean isCounterAttack, boolean enemyAttack, boolean isSim)
            throws OutOfAttackRangeException, CounterattackHappenedException {
        if (null == target) {
            LogUtil.printInfo(battle.map.getCurrentRoundNo(), "WARNING", "Warning! Attack target is null. ");
            return;
        }
        if (!isSim)
        drawMap(battle, "attack", target.y, target.x);

        // caculate hp damage
        // step 1
        int dc = generateDefensiveCorrection(target);
        // step 2
        int hpDamage = (caculateAP() - dc / 2) * (100 - queryMapItemCorrection(target.currentPositionMap)) / 100;
        // step 3
        if (isCounterAttack) {
            hpDamage = hpDamage / 2;
        }
        // step 4
        if (hpDamage <= 0) {
            hpDamage = 1;
        }
        // step 5
        if (hpDamage > target.currentArmyHP) {
            hpDamage = target.currentArmyHP;
        }
        // caculate moral damage
        int moraleDamage = hpDamage / (target.level + 5) / 3;
        if (0 == moraleDamage && hpDamage > 0) {
            moraleDamage = 1;
        }
        if (0 == moraleDamage && 0 == hpDamage) {
            moraleDamage = 0;
        }
        if (moraleDamage > target.currentMorale) {
            moraleDamage = target.currentMorale;
        }

        target.currentArmyHP -= hpDamage;
        target.currentMorale -= moraleDamage;

        int baseGainedExp = 0;
        int extraGainedExp = 0;
        // ko
        if (0 == target.currentArmyHP) {
            if (enemyAttack) {
                battle.outOfBattlePlayerUnits.add(target);
            } else {
                battle.outOfBattleEnemyUnits.add(target);
            }
            if (!isSim)
            LogUtil.printInfo(battle.map.getCurrentRoundNo(), "INFO", target + " has been kicked out.");
            target.evacuate();
        }

        if (!enemyAttack) {
            if (this.level > target.level) {
                baseGainedExp = 4;
            } else {
                baseGainedExp = (target.level - this.level + 3) * 2;
            }
            if (baseGainedExp > 16) {
                baseGainedExp = 16;
            }
            // ko
            if (target.isEvacuated) {
                if (target.isLord) {
                    extraGainedExp = 48;
                } else {
                    if (target.level > this.level) {
                        extraGainedExp = 32;
                    } else {
                        extraGainedExp = 64 / (this.level - target.level + 2);
                    }
                }
            }
            gainExp(baseGainedExp + extraGainedExp);
        }

        LogUtil.printLog(battle.map.getCurrentRoundNo(), isCounterAttack ? "Counter-ATTACK" : "ATTACK", this.name,
                target.name, "hpDamage: " + hpDamage + " moraleDamage: " + moraleDamage + " unit: " + this.name
                        + " gain exp: " + baseGainedExp + " " + extraGainedExp + " target: " + target.name + " HP: "
                        + target.currentArmyHP + " Morale: " + target.currentMorale);

        // Counter attack determination
        if (target instanceof Theif || target instanceof MartialArtist) {
            if (this instanceof Theif || this instanceof MartialArtist || this instanceof Rider
                    || this instanceof Barbarian || this instanceof BeastArmy) {
                if (!target.isEvacuated) {
                    if (!target.isInChaos) {
                        if (!isCounterAttack) {
                            int dice = RandomHelper.generateInt(0, 149);
                            if (dice < target.force) {
                                target.attack(battle, this, true, !enemyAttack, isSim);
                                throw new CounterattackHappenedException();
                            }
                        }
                    }
                }
            }
        }
    }

    public int generateDefensiveCorrection(BaseUnit target) {
        if (this instanceof Footman) {
            if (target instanceof Bow) {
                return target.caculateDP() - target.caculateDP() / 4;
            } else if (target instanceof Rider) {
                return target.caculateDP() + target.caculateDP() / 4;
            }
        } else if (this instanceof Bow) {
            if (target instanceof Rider) {
                return target.caculateDP() - target.caculateDP() / 4;
            } else if (target instanceof Footman) {
                return target.caculateDP() + target.caculateDP() / 4;
            }
        } else if (this instanceof Rider) {
            if (target instanceof Footman) {
                return target.caculateDP() - target.caculateDP() / 4;
            } else if (target instanceof Rider) {
                return target.caculateDP() + target.caculateDP() / 4;
            }
        }
        return target.caculateDP();
    }

    public int queryMapItemCorrection(MapItem map) {
        if (map instanceof Forest) {
            return 20;
        } else if (map instanceof Mountain) {
            return 30;
        } else if (map instanceof Village) {
            return 5;
        } else if (map instanceof Grassland) {
            return 5;
        } else if (map instanceof Abatis) {
            return 30;
        } else if (map instanceof Barrack) {
            return 10;
        }
        return 0;
    }

    public void strategy(BaseUnit target) {
        // TODO
    }

    public void useItem(int itemIdx, BaseUnit target) {
        // TODO
    }

    public void evacuate() {
        this.isEvacuated = true;
        if (null != this.currentPositionMap) {
            this.currentPositionMap.army = null;
        }
        this.currentPositionMap = null;
        // this.y = -99;
        // this.x = -99;
    }

    public boolean isWeak(BattleInfo battle) {
        if (this.currentArmyHP < initMaxArmyHP() * 0.4 || this.currentMorale < 40) {
            LogUtil.printInfo(battle.map.getCurrentRoundNo(), this.name + "[" + this.y + "," + this.x + "]"
                    + " is weak.");
            return true;
        }
        return false;
    }

    public Action[][] generateMyActionValues(BattleInfo battle) {
        // step 0 init values
        Action[][] actionValuesArray = new Action[battle.map.map.length][battle.map.map[0].length];
        fillValuesArray(actionValuesArray, 1);
        // step 1 deal with weak army
        if (this.isWeak(battle)) {
            increaseRestorePlacesValue(battle.map.map, actionValuesArray, 50);
        }
        // step 2 attack
        for (BaseUnit target : battle.playerUnits) {
            if (target.isEvacuated) {
                continue;
            }
            int attackBaseValue = (caculateAP() - generateDefensiveCorrection(target) / 2)
                    * (100 - queryMapItemCorrection(target.currentPositionMap)) / 100 + this.currentArmyHP / 6;
            if (null != this.specialEnemy) {
                if (target.name.equals(this.specialEnemy.name)) {
                    attackBaseValue += 30;
                }
            }
            int attackActionValue = attackBaseValue / 16;
            if (attackActionValue < 1) {
                attackActionValue = 1;
            }
            // calculate attack place
            /**
             * O T O<br>
             * T @ T<br>
             *   O T O<br>
             */
            if (!(this instanceof Bow)) {
                fillTargetAndActionValue(actionValuesArray, target.y - 1, target.x, attackActionValue, target);
                fillTargetAndActionValue(actionValuesArray, target.y, target.x + 1, attackActionValue, target);
                fillTargetAndActionValue(actionValuesArray, target.y + 1, target.x, attackActionValue, target);
                fillTargetAndActionValue(actionValuesArray, target.y, target.x - 1, attackActionValue, target);
            }
            /**
             * T O T<br>
             * O @ O<br>
             *   T O T<br>
             */
            if (this instanceof LongArmed || this instanceof Chariot || this instanceof MartialArtist) {
                fillTargetAndActionValue(actionValuesArray, target.y - 1, target.x + 1, attackActionValue, target);
                fillTargetAndActionValue(actionValuesArray, target.y + 1, target.x + 1, attackActionValue, target);
                fillTargetAndActionValue(actionValuesArray, target.y - 1, target.x - 1, attackActionValue, target);
                fillTargetAndActionValue(actionValuesArray, target.y + 1, target.x - 1, attackActionValue, target);
            }
            /**
             * T O T<br>
             * O @ O<br>
             *   T O T<br>
             */
            if (this instanceof Bow) {
                fillTargetAndActionValue(actionValuesArray, target.y - 1, target.x + 1, attackActionValue, target);
                fillTargetAndActionValue(actionValuesArray, target.y + 1, target.x + 1, attackActionValue, target);
                fillTargetAndActionValue(actionValuesArray, target.y - 1, target.x - 1, attackActionValue, target);
                fillTargetAndActionValue(actionValuesArray, target.y + 1, target.x - 1, attackActionValue, target);
            }
            /**
             * O O T O O<br>
             * O O O O O<br>
             * T O @ O T<br>
             * O O O O T<br>
             * O O T O O<br>
             */
            if (this instanceof BowSoilder) {
                fillTargetAndActionValue(actionValuesArray, target.y, target.x + 2, attackActionValue, target);
                fillTargetAndActionValue(actionValuesArray, target.y, target.x - 2, attackActionValue, target);
                fillTargetAndActionValue(actionValuesArray, target.y + 2, target.x, attackActionValue, target);
                fillTargetAndActionValue(actionValuesArray, target.y - 2, target.x, attackActionValue, target);
            }
            /**
             * O T T T O<br>
             * T O O O T<br>
             * T O @ O T<br>
             * T O O O T<br>
             * O T T T O<br>
             */
            if (this instanceof Liannu || this instanceof StoneCar) {
                fillTargetAndActionValue(actionValuesArray, target.y - 2, target.x - 1, attackActionValue, target);
                fillTargetAndActionValue(actionValuesArray, target.y + 2, target.x - 1, attackActionValue, target);
                fillTargetAndActionValue(actionValuesArray, target.y - 2, target.x, attackActionValue, target);
                fillTargetAndActionValue(actionValuesArray, target.y + 2, target.x, attackActionValue, target);
                fillTargetAndActionValue(actionValuesArray, target.y - 2, target.x + 1, attackActionValue, target);
                fillTargetAndActionValue(actionValuesArray, target.y + 2, target.x + 1, attackActionValue, target);
                fillTargetAndActionValue(actionValuesArray, target.y - 1, target.x + 2, attackActionValue, target);
                fillTargetAndActionValue(actionValuesArray, target.y - 1, target.x - 2, attackActionValue, target);
                fillTargetAndActionValue(actionValuesArray, target.y, target.x + 2, attackActionValue, target);
                fillTargetAndActionValue(actionValuesArray, target.y, target.x - 2, attackActionValue, target);
                fillTargetAndActionValue(actionValuesArray, target.y + 1, target.x + 2, attackActionValue, target);
                fillTargetAndActionValue(actionValuesArray, target.y + 1, target.x - 2, attackActionValue, target);
            }
            /**
             * O O O T O O O<br>
             * O T O O O T O<br>
             * O O O O O O O<br>
             * T O O @ O O T<br>
             * O O O O O O O<br>
             * O T O O O T O<br>
             * O O O T O O O<br>
             */
            if (this instanceof StoneCar) {
                fillTargetAndActionValue(actionValuesArray, target.y, target.x + 3, attackActionValue, target);
                fillTargetAndActionValue(actionValuesArray, target.y, target.x - 3, attackActionValue, target);
                fillTargetAndActionValue(actionValuesArray, target.y + 3, target.x, attackActionValue, target);
                fillTargetAndActionValue(actionValuesArray, target.y - 3, target.x, attackActionValue, target);
                fillTargetAndActionValue(actionValuesArray, target.y - 2, target.x + 2, attackActionValue, target);
                fillTargetAndActionValue(actionValuesArray, target.y + 2, target.x + 2, attackActionValue, target);
                fillTargetAndActionValue(actionValuesArray, target.y + 2, target.x - 2, attackActionValue, target);
                fillTargetAndActionValue(actionValuesArray, target.y - 2, target.x - 2, attackActionValue, target);
            }
        }
        return actionValuesArray;
    }

    private void fillTargetAndActionValue(Action[][] actionArray, int y, int x, int value, BaseUnit target) {
        if (y >= actionArray.length || x >= actionArray[0].length || y < 0 || x < 0) {
            return;
        }
        if (value > actionArray[y][x].actionValue) {
            actionArray[y][x].actionType = "Attack";
            actionArray[y][x].actionValue = value;
            actionArray[y][x].target = target;
        }
    }

    private void fillValuesArray(Action[][] values, int value) {
        for (int j = 0; j < values.length; j++) {
            for (int i = 0; i < values[j].length; i++) {
                values[j][i] = new Action();
            }
        }
    }

    private void increaseRestorePlacesValue(MapItem[][] map, Action[][] values, int value) {
        for (int j = 0; j < values.length; j++) {
            for (int i = 0; i < values[j].length; i++) {
                if (map[j][i] instanceof Village || map[j][i] instanceof Abatis || map[j][i] instanceof Barrack)
                    values[j][i].actionValue += value;
            }
        }
    }

    int lastY;
    int lastX;
    int lastExp;
    int lastHP;
    int lastMP;
    int lastSP;
    boolean lastEvacuated = false;
    public void recordLastState() {
        lastY = this.y;
        lastX = this.x;
        lastExp = this.exp;
        lastHP = this.currentArmyHP;
        lastMP = this.currentMorale;
        lastSP = this.currentMana;
        lastEvacuated = this.isEvacuated;
    }

    public void restoreLastState(BattleInfo battle) {
        battle.map.map[this.y][this.x].army = null;
        this.y = lastY;
        this.x = lastX;
        this.exp = lastExp;
        this.currentArmyHP = lastHP;
        this.currentMorale = lastMP;
        this.currentMana = lastSP;
        battle.map.map[this.y][this.x].army = this;
        this.isEvacuated = lastEvacuated;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + defense;
        result = prime * result + force;
        result = prime * result + intelligence;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BaseUnit other = (BaseUnit) obj;
        if (defense != other.defense)
            return false;
        if (force != other.force)
            return false;
        if (intelligence != other.intelligence)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return name + "[" + y + "," + x + "][" + force + "," + intelligence + "," + defense + "] HP:" + currentArmyHP
                + " Mo:" + currentMorale + " Exp: " + exp;
    }

}
