package az.test.model.army;

import az.test.battle.BattleInfo;
import az.test.battle.enums.PlayerAction;
import az.test.exception.*;
import az.test.model.army.bow.Bow;
import az.test.model.army.foot.Infantry;
import az.test.model.army.foot.ShortArmed;
import az.test.model.army.other.Barbarian;
import az.test.model.army.other.BeastArmy;
import az.test.model.army.other.MartialArtist;
import az.test.model.army.ride.LightRide;
import az.test.model.army.ride.Rider;
import az.test.model.army.theif.Thief;
import az.test.model.enums.ArmyType;
import az.test.model.item.*;
import az.test.model.item.consumption.ConsumableItem;
import az.test.model.item.restore.Commandment;
import az.test.model.item.restore.ImperialJadeSeal;
import az.test.model.item.restore.SupportReport;
import az.test.model.item.weapon.Weapon;
import az.test.model.map.*;
import az.test.reko3ibm.Action;
import az.test.reko3ibm.ActionAIType;
import az.test.util.LogUtil;
import az.test.util.ObjectCopyUtil;
import az.test.util.RandomHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class BaseUnit implements Serializable {
    // personal attributes
    public String name;
    public int level;
    public int exp;
    /**
     * 智力
     */
    public int intelligence;
    /**
     * 武力
     */
    public int force;
    /**
     * 统御
     */
    public int defense;
    // bag
    public List<BaseItem> items = new ArrayList<>();
    public static final int MAX_ITEM_LIMIT = 8;
    // army name
    public String armyName;
    // army attributes
    public int armyHPBase;
    public int armyHPInc;
    /**
     * 兵力
     */
    public int currentArmyHP;
    /**
     * 士气
     */
    public int currentMorale;
    /**
     * 策略值
     */
    public int currentMana;
    public int apBase;
    public int dpBase;

    public int moveAbility;
    public Set<MapItem> canMoveToCoordinateRange = new HashSet<>();
    public List<AttackRange> attackRangeList = new ArrayList<>();

    // current position related to battle map
    public int x;
    public int y;
    public MapItem currentPositionMap;

    public boolean isInChaos;
    public boolean roundFinished;
    public boolean isEvacuated;
    public boolean isLord;
    public boolean canEscape;
    public boolean isEscaped;

    // auto action stuff
    public ActionAIType aiType;
    // special target
    public BaseUnit specialEnemy;
    public ArmyType armyType;

    public BattleInfo battle;

    public BaseUnit(BattleInfo battle, String armyName) {
        this.battle = battle;
        this.armyName = armyName;
    }

    public void copyProperties(BaseUnit transformFrom) {
        this.name = (String) ObjectCopyUtil.deepCopy(transformFrom.name);
        this.intelligence = transformFrom.intelligence;
        this.force = transformFrom.force;
        this.defense = transformFrom.defense;
        this.items = (List<BaseItem>) ObjectCopyUtil.deepCopy(transformFrom.items);
        this.exp = transformFrom.exp;
        this.currentArmyHP = transformFrom.currentArmyHP;
        this.currentMorale = transformFrom.currentMorale;
        this.currentMana = transformFrom.currentMana;
        this.level = transformFrom.level;
    }

    public List<BaseItem> addItem(BaseItem item) throws MaxItemsLimitedException {
        if (null == items) {
            items = new ArrayList<BaseItem>();
        }
        if (item instanceof Gold) {
            Gold g = (Gold) item;
            battle.obtainGold += g.liang;
            return items;
        }
        if (items.size() == MAX_ITEM_LIMIT) {
            throw new MaxItemsLimitedException();
        }
        items.add(item);
        return items;
    }

    public void obtainItem() {
        if (currentPositionMap.isItemPlace()) {
            try {
                addItem((BaseItem) ObjectCopyUtil.deepCopy(currentPositionMap.item));
                currentPositionMap.item = null;
            } catch (MaxItemsLimitedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean haveRestoreHPItem() {
        for (BaseItem item : items) {
            if (item instanceof ImperialJadeSeal || item instanceof SupportReport) {
                return true;
            }
        }
        return false;
    }

    public boolean haveRestoreMoraleItem() {
        for (BaseItem item : items) {
            if (item instanceof ImperialJadeSeal || item instanceof Commandment) {
                return true;
            }
        }
        return false;
    }

    public void restoreHP(int hpRestored) {
        currentArmyHP += hpRestored;
        // TODO
    }

    public void restoreMorale(int moraleRestored) {
        currentMorale += moraleRestored;
        // TODO
    }

    public int calculateMaxArmyHP() {
        return armyHPBase + armyHPInc * (level - 1);
    }

    public int calculateMaxMana() {
        return (level + 10) * intelligence * 5 / 200;
    }

    public int calculateAP() {
        return (int) (((4000.0 / (140.0 - (double) force) + apBase * 2 + currentMorale) * (double) (level + 10) / 10.0)
                * (double) (100 + queryItemAPInc()) / 100.0);
    }

    public int calculateDP() {
        return (int) (((4000.0 / (140.0 - (double) defense) + dpBase * 2 + currentMorale) * (double) (level + 10) / 10.0)
                * (double) (100 + queryItemDPInc()) / 100.0);
    }

    public int queryItemAPInc() {
        int inc = 0;
        for (BaseItem i : items) {
            if (i instanceof Weapon) {
                Weapon w = (Weapon) i;
                inc = w.apIncrementPercentage;
            }
        }
        return inc;
    }

    public int queryItemDPInc() {
        int inc = 0;
        for (BaseItem i : items) {
            if (i instanceof Book) {
                Book b = (Book) i;
                inc = b.dpIncrementalPercentage;
            }
        }
        return inc;
    }

    public int calculateBaseExp(BaseUnit target) {
        int baseGainedExp = 0;
        if (this.level > target.level) {
            baseGainedExp = 4;
        } else {
            baseGainedExp = (target.level - this.level + 3) * 2;
        }
        if (baseGainedExp > 16) {
            baseGainedExp = 16;
        }
        return baseGainedExp;
    }

    public int calculateExtraExp(BaseUnit target) {
        int extraGainedExp = 0;
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
        return extraGainedExp;
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

    /**
     * ===============================ACTIONS================================
     */

    /**
     * 部队在特殊地形上的行动受限问题：
     * <p>
     * 这里的特殊地形，指的是行动消耗超过1的地形，并且是相对的。例如对于骑兵，荒地相对草地是行动艰难的地形，相对鹿砦则是行动容易的地形。
     * <p>
     * 名词解释：
     * 1.暗格：包括障碍地形、该部队不能进入的地形、被敌军占领的地方。
     * 2.亮格：包括该部队可以移动到的空地、被己军或友军占领的地方。
     * (暗格或者亮格能够在点击部队时直接看出来)
     * <p>
     * 受限为一格的条件如下：
     * 1.紧挨部队的四格至少有两个亮格。
     * 2.紧挨部队的亮格地形不同，有行动容易的地形，也有行动艰难的地形。
     * 3.由于敌军阻碍，部队向行动最容易的各个亮格方向只能行动一步。
     * 当上述三个满足时，该部队只能向行动艰难的各个亮格方向走一格，即只能向各个亮格方向走一格。
     * 一旦条件改变，部队的受限情况也发生改变。
     *
     * @param battle
     * @param targetY
     * @param targetX
     * @param isSim
     */
    public void moveTo(BattleInfo battle, int targetY, int targetX, boolean isSim) {
        // canMoveToCoordinateRange = new ArrayList<MapItem>();
        LogUtil.printLog(battle.map.getCurrentRoundNo(), "move", this.name, " move to " + targetY + "," + targetX,
                " from coordinate: [" + this.y + "," + this.x + "] to [" + targetY + "," + targetX + "] moveAbility: "
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
        LogUtil.printInfo(battle.getMap().getCurrentRoundNo(), "[move]start");
        for (int len = 0; len < battle.map.map[0].length; len++) {
            LogUtil.printInfoWithNoReturn(String.format("%02d", len) + " ");
        }
        LogUtil.printlnInfo("");
        LogUtil.printInfo(battle.getMap().getCurrentRoundNo(), "[move]end  ");
        for (int len = 0; len < battle.map.map[0].length; len++) {
            LogUtil.printlnInfo("___");
        }
        LogUtil.printInfo(battle.getMap().getCurrentRoundNo(), "");

        for (int yyy = 0; yyy < battle.map.map.length; yyy++) {
            LogUtil.printInfoWithNoReturn(battle.map.getCurrentRoundNo(), "move", String.format("%02d", yyy) + "|");
            for (int xxx = 0; xxx < battle.map.map[yyy].length; xxx++) {
                MapItem mi = battle.map.map[yyy][xxx];
                BaseUnit army = mi.army;
                int mapItemId = mi.id;
                // draw whole map range
                if (-1 != targetY) {
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
                        } else {
                            if (ArmyType.ENEMY == army.armyType) {
                                System.out.print("e ");
                            } else if (ArmyType.FRIEND == army.armyType) {
                                System.out.print("f ");
                            } else if (ArmyType.PLAYER == army.armyType) {
                                System.out.print("p ");
                            } else {
                                System.out.print("? ");
                            }
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
                        if (ArmyType.ENEMY == army.armyType) {
                            System.out.print("e ");
                        } else if (ArmyType.FRIEND == army.armyType) {
                            System.out.print("f ");
                        } else if (ArmyType.PLAYER == army.armyType) {
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
        LogUtil.printInfo(battle.getMap().getCurrentRoundNo(), "[move]   ");
        for (int len = 0; len < battle.map.map[0].length; len++) {
            System.out.print("---");
        }
        System.out.println();
    }

    public void drawMap(BattleInfo battle, String action, int targetY, int targetX) {
        LogUtil.printInfo(battle.getMap().getCurrentRoundNo(), "[" + action + "]   ");
        for (int len = 0; len < battle.map.map[0].length; len++) {
            System.out.print(String.format("%02d", len) + " ");
        }
        System.out.println();
        LogUtil.printInfo(battle.getMap().getCurrentRoundNo(), "[" + action + "]   ");
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
                    if (ArmyType.ENEMY == army.armyType) {
                        System.out.print("e");
                    } else if (ArmyType.FRIEND == army.armyType) {
                        System.out.print("f");
                    } else if (ArmyType.PLAYER == army.armyType) {
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
        LogUtil.printInfo(battle.getMap().getCurrentRoundNo(), "[" + action + "]   ");
        for (

                int len = 0; len < battle.map.map[0].length; len++) {
            System.out.print("---");
        }
        System.out.println();
    }

    private boolean existOtherArmy(BattleInfo battle, MapItem mi) {
        if (null == mi.army) {
            return false;
        } else {
            return mi.army.y != y || mi.army.x != x;
        }
    }

    private boolean isSurroundByOppositeArmy(BattleInfo battle, int y, int x) {
        if (y - 1 >= 0) {
            return existOtherArmy(battle, battle.map.map[y - 1][x]);
        }
        if (x + 1 < battle.map.map[0].length) {
            return existOtherArmy(battle, battle.map.map[y][x + 1]);
        }
        if (y + 1 < battle.map.map.length) {
            return existOtherArmy(battle, battle.map.map[y + 1][x]);
        }
        if (x - 1 >= 0) {
            return existOtherArmy(battle, battle.map.map[y][x - 1]);
        }
        return false;
    }

    /**
     * 英杰传移动型AI算法<br>
     * <br>
     * 定义：   (A)部队当前的横纵坐标；<br>
     * (B)部队的目标坐标；<br>
     * (C)部队本回合的目标坐标。<br>
     * <br>
     * 若AI=0（移动）或4（无攻击移动）：<br>
     * <br>
     * 1、如果存在仇人，则取仇人的坐标为目标坐标(B)。<br>
     * 2、在移动范围内，查找最近敌的战场代码。查找方法为：<br>
     * 2.1、以(A)为中心，将所有的坐标都标上其消耗的总移动力，移动范围之外的标记-1<br>
     * 2.2、令x=0~移动力的循环，每次循环，都按逐行扫描的方式检查该坐标的移动力总消耗是否等于x，如果是，就按上右下左的顺序检查该格的周围四格是否有敌人，若有，则结束查找，返回该敌人的战场代码。<br>
     * 2.3、如果查找不到敌人，则返回-1。<br>
     * 3、如果最近敌恰好为仇人，那么<br>
     * 3.1、若(B)没人则(B)的行动价值+30；<br>
     * 3.2、以(B)为中心，周围4格中属于(A)为中心的移动范围内的格子，行动价值+30（若(B)没人则只+10）；<br>
     * 3.3、以(B)为中心，上2右2下2左2以及斜四格中属于(A)为中心的移动范围的格子，行动价值+10（若(B)没人则不加）；<br>
     * 4、如果最近敌不是仇人或没有仇人或之前返回-1，则<br>
     * 4.1、以(B)作为中心坐标，移动力无限，计算战场上所有坐标的总消耗移动力；<br>
     * 4.2、如果发现(B)到(A)的路线被封堵（注意是(B)到(A)不是(A)到(B)，即以无限的移动力经过一个回合的移动无法到达(A)，或者(B)是不可移动地形），则本回合采用AI=1（攻击最近敌）的方式操作，否则转下一步。<br>
     * 4.3、取(C)=(A)；<br>
     * 4.4、以(C)作为中心坐标，按上右下左的顺序查找周围四格中总消耗移动力最小的格子，并用那个格子取代(C)作为新的(C)；<br>
     * 4.5、重复4.4，直到移动力消耗完毕或者(C)=(B)为止。<br>
     * 4.6、若(C)没人则(C)的行动价值+30；<br>
     * 4.7、以(C)为中心，周围4格中属于(A)为中心的移动范围内的格子，行动价值+30（若(C)没人则只+10）；<br>
     * 4.8、以(C)为中心，上2右2下2左2以及斜四格中属于(A)为中心的移动范围的格子，行动价值+10（若(C)没人则不加）；<br>
     * <br>
     * <br>
     * 若AI=1（攻击最近敌）<br>
     * <br>
     * 1、以(A)为中心，如果移动范围+攻击范围内有敌人，则按照AI=3（休息）的方式操作；<br>
     * 2、以(A)为中心，无限移动力，如果移动范围+攻击范围内没有敌人，则本回合不动，也不用策略，否则转第3步；<br>
     * 3、查找最近敌坐标<br>
     * 3.1、以(A)为中心，将所有的坐标都标上其消耗的总移动力，移动范围之外的标记-1<br>
     * 3.2、令x=0~移动力的循环，每次循环，都按逐行扫描的方式检查该坐标的移动力总消耗是否等于x，如果是，就按上右下左的顺序检查该格的周围四格是否有敌人，若有，则结束查找，返回该敌人的战场代码。<br>
     * 3.3、(C)取为该敌人的坐标<br>
     * 3.4、以(B)作为中心坐标，移动力无限，计算战场上所有坐标的总消耗移动力；<br>
     * 3.5、如果发现(B)到(A)的路线被封堵（即以无限的移动力经过一个回合的移动无法到达(A)，或者(B)是不可移动地形），则本回合不动也不使用策略，否则转下一步。<br>
     * 3.6、取(C)=(A)；<br>
     * 3.7、以(C)作为中心坐标，按上右下左的顺序查找周围四格中总消耗移动力最小的格子，并用那个格子取代(C)作为新的(C)；<br>
     * 3.8、重复3.7，直到移动力消耗完毕或者(C)=(B)为止。<br>
     * 3.9、若(C)没人则(C)的行动价值+30；<br>
     * 3.10、以(C)为中心，周围4格中属于(A)为中心的移动范围内的格子，行动价值+30（若(C)没人则只+10）；<br>
     * 3.11、以(C)为中心，上2右2下2左2以及斜四格中属于(A)为中心的移动范围的格子，行动价值+10（若(C)没人则不加）；<br>
     * <br>
     * <br>
     * 最后，计算移动范围内各个坐标的行动价值。（AI=4时不计算攻击或策略的行动价值）<br>
     *
     * @param battle
     * @param moveAbility
     * @param nowY
     * @param nowX
     */
    public void calculateMoveRange(BattleInfo battle, int moveAbility, int nowY, int nowX) {
        if (this.isInChaos || this.roundFinished) {
            return;
        }
        MapItem c = battle.map.map[nowY][nowX];
        // add current position
        canMoveToCoordinateRange.add(c);
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
                        canMoveToCoordinateRange.add(mapItemNorth);
                        calculateMoveRange(battle, moveAbility - mapItemNorth.queryCost(this), mapItemNorth.y,
                                mapItemNorth.x);
                    }
                }
            }
            targetY = nowY;
            targetX = nowX + 1;
            if (targetX <= battle.map.map[0].length) {
                if (battle.canIStandHere(this, targetY, targetX)) {
                    MapItem mapItemEast = battle.map.map[targetY][targetX];
                    if (null == mapItemEast.army || !existOtherArmy(battle, mapItemEast)) {
                        canMoveToCoordinateRange.add(mapItemEast);
                        calculateMoveRange(battle, moveAbility - mapItemEast.queryCost(this), mapItemEast.y,
                                mapItemEast.x);
                    }
                }
            }
            targetY = nowY + 1;
            targetX = nowX;
            if (targetY <= battle.map.map.length) {
                if (battle.canIStandHere(this, targetY, targetX)) {
                    MapItem mapItemSouth = battle.map.map[targetY][targetX];
                    if (null == mapItemSouth.army || !existOtherArmy(battle, mapItemSouth)) {
                        canMoveToCoordinateRange.add(mapItemSouth);
                        calculateMoveRange(battle, moveAbility - mapItemSouth.queryCost(this), mapItemSouth.y,
                                mapItemSouth.x);
                    }
                }
            }
            targetY = nowY;
            targetX = nowX - 1;
            if (targetX >= 0) {
                if (battle.canIStandHere(this, targetY, targetX)) {
                    MapItem mapItemWest = battle.map.map[targetY][targetX];
                    if (null == mapItemWest.army || !existOtherArmy(battle, mapItemWest)) {
                        canMoveToCoordinateRange.add(mapItemWest);
                        calculateMoveRange(battle, moveAbility - mapItemWest.queryCost(this), mapItemWest.y,
                                mapItemWest.x);
                    }
                }
            }
        }
    }

    public BaseUnit calculateCurrentPositionAttackTarget(BattleInfo battle) {
        if (this instanceof Rider || this instanceof ShortArmed) {
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
        boolean isPlayer = battle.playerUnits.contains(this);
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

    public void attack(BattleInfo battle, BaseUnit target, boolean isCounterAttack, boolean enemyAttack, boolean isSim)
            throws OutOfAttackRangeException, CounterattackHappenedException {
        if (null == target) {
            LogUtil.printInfo(battle.map.getCurrentRoundNo(), "WARNING", "Warning! Attack target is null. ");
            return;
        }
        if (!isSim)
            drawMap(battle, "attack", target.y, target.x);

        // calculate hp damage
        // step 1
        int dc = generateDefensiveCorrection(target);
        // step 2
        int hpDamage = (calculateAP() - dc / 2) * (100 - queryMapItemCorrection(target.currentPositionMap)) / 100;
        // step 3
        if (isCounterAttack) {
            hpDamage = hpDamage / 2;
        }
        hpDamage = reduceHP(hpDamage, target);
        // calculate moral damage
        int moraleDamage = hpDamage / (target.level + 5) / 3;

        moraleDamage = reduceMorale(moraleDamage, hpDamage, target);

        judgeKickOut(target, enemyAttack);

        int baseGainedExp = 0;
        int extraGainedExp = 0;

        if (!enemyAttack) {
            baseGainedExp = calculateBaseExp(target);
            extraGainedExp = calculateExtraExp(target);
        }
        if (baseGainedExp + extraGainedExp > 0) {
            gainExp(baseGainedExp + extraGainedExp);
        }

        LogUtil.printLog(battle.map.getCurrentRoundNo(), isCounterAttack ? "Counter-ATTACK" : "ATTACK", this.name,
                target.name, "hpDamage: " + hpDamage + " moraleDamage: " + moraleDamage + " unit: " + this.name
                        + " gain base exp: " + baseGainedExp + ", extra exp: " + extraGainedExp + ", target: " + target + ", kick-out?" + (target.isEvacuated));

        // Counter attack determination
        if (target instanceof Thief || target instanceof MartialArtist) {
            if (this instanceof Thief || this instanceof MartialArtist || this instanceof Rider
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
        rest();
    }

    public int generateDefensiveCorrection(BaseUnit target) {
        if (this instanceof Infantry) {
            if (target instanceof Bow) {
                return target.calculateDP() - target.calculateDP() / 4;
            } else if (target instanceof Rider) {
                return target.calculateDP() + target.calculateDP() / 4;
            }
        } else if (this instanceof Bow) {
            if (target instanceof Rider) {
                return target.calculateDP() - target.calculateDP() / 4;
            } else if (target instanceof Infantry) {
                return target.calculateDP() + target.calculateDP() / 4;
            }
        } else if (this instanceof Rider) {
            if (target instanceof Infantry) {
                return target.calculateDP() - target.calculateDP() / 4;
            } else if (target instanceof Rider) {
                return target.calculateDP() + target.calculateDP() / 4;
            }
        }
        return target.calculateDP();
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
        rest();
    }

    public void useItem(int itemIdx, BaseUnit... targets) throws ItemIndexOutOfBoundException, ItemNotConsumableException {
        if (itemIdx > items.size() - 1) {
            throw new ItemIndexOutOfBoundException();
        }
        BaseItem item = items.get(itemIdx);
        if (!(item instanceof ConsumableItem)) {
            throw new ItemNotConsumableException();
        }
        ConsumableItem consumableItem = (ConsumableItem) item;
        try {
            consumableItem.consume(this, targets);
        } catch (BaseException e) {
            throw new RuntimeException(e);
        }
        rest();
    }

    public void rest() {
        obtainItem();
        this.roundFinished = true;
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

    public void escape() {
        isEscaped = true;
    }

    public boolean isWeak() {
        if (this.currentArmyHP < calculateMaxArmyHP() * 0.4 || this.currentMorale < 40) {
            LogUtil.printInfo(0, this.name + "[" + this.y + "," + this.x + "]"
                    + " is weak.");
            return true;
        }
        return false;
    }

    protected void fillValuesArray(Action[][] values) {
        for (int j = 0; j < values.length; j++) {
            for (int i = 0; i < values[j].length; i++) {
                values[j][i] = new Action(PlayerAction.REST, 1);
            }
        }
    }

    protected void increaseRestorePlacesValue(MapItem[][] map, Action[][] values) {
        for (int j = 0; j < values.length; j++) {
            for (int i = 0; i < values[j].length; i++) {
                if (map[j][i] instanceof Village || map[j][i] instanceof Abatis || map[j][i] instanceof Barrack)
                    values[j][i].actionValue += 50;
            }
        }
    }

    public int reduceHP(int damage, BaseUnit target) {
        // step 4
        if (damage <= 0) {
            damage = 1;
        }
        // step 5
        if (damage > target.currentArmyHP) {
            damage = target.currentArmyHP;
        }
        target.currentArmyHP -= damage;
        return damage;
    }

    public int reduceMorale(int moraleDamage, int hpDamage, BaseUnit target) {
        if (0 == moraleDamage && hpDamage > 0) {
            moraleDamage = 1;
        }
        if (0 == moraleDamage && 0 == hpDamage) {
            return moraleDamage;
        }
        if (moraleDamage > target.currentMorale) {
            moraleDamage = target.currentMorale;
        }
        target.currentMorale -= moraleDamage;
        return moraleDamage;
    }

    public void judgeKickOut(BaseUnit target, boolean enemyAttack) {
        // ko
        if (0 == target.currentArmyHP) {
            if (enemyAttack) {
                battle.outOfBattlePlayerUnits.add(target);
            } else {
                battle.outOfBattleEnemyUnits.add((BotUnit) target);
            }
            target.evacuate();
        }
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
        return name + ",isLord?" + isLord + ",Lv:" + this.level + "[" + y + "," + x + "][" + force + "," + intelligence + "," + defense + "],[AP:" + calculateAP() + "][DP:" + calculateDP() + "]HP:" + currentArmyHP
                + ",Mo:" + currentMorale + ",Exp: " + exp;
    }

}
