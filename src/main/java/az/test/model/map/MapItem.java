package az.test.model.map;

import az.test.model.army.BaseUnit;
import az.test.model.army.other.Barbarian;
import az.test.model.army.other.BeastArmy;
import az.test.model.army.other.MartialArtist;
import az.test.model.army.other.MilitaryBand;
import az.test.model.army.other.TransportTeam;
import az.test.model.army.ride.Rider;
import az.test.model.army.theif.Thief;
import az.test.model.item.BaseItem;
import az.test.model.item.Item;

import java.io.Serializable;

/**
 * 00 平原  01 森林  02 山地  03 河流  04 桥梁  05  城墙  06  城池  07  草原
 * 08 村庄  09 悬崖  0A 城门  0B 荒地  0C 栅栏  0D 鹿砦  0E  兵营  0F  粮仓
 * 10 宝物库  11 房舍  12 火焰  13 浊流
 */
public abstract class MapItem implements Serializable, Comparable<MapItem> {
    public int id;
    public boolean canStay = true;
    public BaseUnit army = null;
    public int y;
    public int x;
    public String name;
    public BaseItem item;
    public boolean isEscape;

    public static MapItem generateById(int id, int y, int x, boolean isEscape) {
        MapItem mi = generateById(id, y, x);
        mi.isEscape = isEscape;
        return mi;
    }

    public abstract void drawMapItem();

    /**
     * 00 平原
     * 01 森林
     * 02 山地
     * 03 河流
     * 04 桥梁
     * 05 城墙
     * 06 城池
     * 07 草原
     * 08 村庄
     * 09 悬崖
     * 0A 城门
     * 0B 荒地
     * 0C 栅栏
     * 0D 鹿砦
     * 0E 兵营
     * 0F 粮仓
     * 10 宝物库
     * 11 房舍
     * 12 火焰
     * 13 浊流
     */
    public static MapItem generateById(int id, int y, int x) {
        MapItem mi;
        switch (id) {
            case 0:
                mi = new Plain();// all
                break;
            case 1:
                mi = new Forest();// all but Ride
                break;
            case 2:
                mi = new Mountain();// MA(武术家),Thief only
                break;
            case 3:
                mi = new River();// none
                break;
            case 4:
                mi = new Bridge();// all
                break;
            case 5:
                mi = new Wall();// none
                break;
            case 6:
                mi = new City();// all
                break;
            case 7:
                mi = new Grassland();// all
                break;
            case 8:
                mi = new Village();// all
                break;
            case 9:
                mi = new Cliff();// none
                break;
            case 10: // 0x0A
                mi = new Gate();// none
                break;
            case 11: // 0x0B
                mi = new Wasteland();// all
                break;
            case 12: // 0x0C
                mi = new Fence();// none
                break;
            case 13: // 0x0D
                mi = new Abatis();// all
                break;
            case 14:
                mi = new Barrack();// all
                break;
            case 15:
                mi = new Granary();// all
                break;
            case 16:
                mi = new TreasureStore();// all
                break;
            case 17:
                mi = new House();// none
                break;
            case 18:
                mi = new Flame(); // none
                break;
            case 19:
                mi = new Flow(); // none
            default:
                mi = new Plain();
                break;
        }
        mi.y = y;
        mi.x = x;
        return mi;
    }

    public boolean isItemPlace() {
        return null != item;
    }

    public boolean isRestoreHPPlace() {
        return this instanceof Village || this instanceof Abatis || this instanceof Barrack;
    }

    public boolean isRestoreMoralePlace() {
        return this instanceof Village || this instanceof Abatis;
    }

    public int queryCost(BaseUnit army) {
        if (this instanceof Plain || this instanceof City || this instanceof Grassland || this instanceof Bridge) {
            return 1;
        }
        if (!canStay) {
            return Integer.MAX_VALUE;
        }
        if (this instanceof Forest) {
            if (army instanceof Rider) {
                return Integer.MAX_VALUE;
            }
        } else if (this instanceof Wasteland) {
            if (army instanceof MilitaryBand || army instanceof TransportTeam || army instanceof Rider) {
                return 2;
            }
            return 1;
        } else if (this instanceof Mountain) {
            if (army instanceof Thief || army instanceof MartialArtist || army instanceof Barbarian
                    || army instanceof BeastArmy) {
                return 1;
            }
            return Integer.MAX_VALUE;
        }
        if (this instanceof Village) {
            return 2;
        }
        if (this instanceof Abatis || this instanceof Barrack || this instanceof TreasureStore
                || this instanceof Granary) {
            if (army instanceof Rider) {
                return 3;
            }
            return 2;
        }
        return Integer.MAX_VALUE;
    }

    @Override
    public String toString() {
        return y + "," + x + "\n";
    }

    @Override
    public int compareTo(MapItem o) {
        if (this.y == o.y && this.x == o.x) {
            return 0;
        } else {
            if (this.y > o.y) {
                return 1;
            } else if (this.y == o.y) {
                if (this.x > o.x) {
                    return 1;
                } else {
                    return -1;
                }
            } else {
                return -1;
            }
        }
    }
}
