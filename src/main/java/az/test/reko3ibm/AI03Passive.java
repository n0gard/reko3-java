package az.test.reko3ibm;

import java.util.HashSet;
import java.util.Set;

import az.test.battle.BattleInfo;
import az.test.exception.*;
import az.test.model.army.BaseUnit;
import az.test.model.army.bow.Bow;
import az.test.model.army.bow.BowSoldier;
import az.test.model.map.MapItem;
import az.test.util.LogUtil;

public class AI03Passive extends ActionAIType {

    public AI03Passive(boolean isEnemy) {
        super();
        super.isEnemy = isEnemy;
    }

    @Override
    public void action(BattleInfo battle, BaseUnit army, boolean isSim) {
        LogUtil.printLog(battle.map.getCurrentRoundNo(), "action", army.name + "[" + army.y + "," + army.x + "]",
                "AI03", "action start");
        // calculate action value
        Action[][] actionValuesArray = botAction.generateMyActionValues();
        String valuesArray2String = parseValuesArray2String(actionValuesArray);
        // calculate move range
        army.canMoveToCoordinateRange = new HashSet<>();
        army.calculateMoveRange(battle, army.calculateMoveAbility(), army.y, army.x);
        // army.drawMap(army.y, army.x);
        if (findAttackTarget(battle, army, army.canMoveToCoordinateRange)) {
            MapItem maxValueMapItem = null;
            Action maxValueAction = null;
            int maxValue = Integer.MIN_VALUE;
            for (MapItem c : army.canMoveToCoordinateRange) {
                if (actionValuesArray[c.y][c.x].actionValue > maxValue && (null == c.army || army == c.army)) {
                    maxValue = actionValuesArray[c.y][c.x].actionValue;
                    maxValueMapItem = c;
                    maxValueAction = actionValuesArray[c.y][c.x];
                }
            }
            LogUtil.printLog(battle.map.getCurrentRoundNo(), "action", army.name, "AI03", valuesArray2String);
            // move to max value target
            // try {
            army.moveTo(battle, maxValueMapItem.y, maxValueMapItem.x, isSim);
            // } catch (OutOfMoveRangeException e) {
            // System.err.println(e.getWho().name + " wanna moved to [" +
            // e.getWannaMovedTo().y + ","
            // + e.getWannaMovedTo().x + "] army: " +
            // e.getWannaMovedTo().army.name);
            // return;
            // }
            // print map
            army.drawMap(battle, -1, -1);
            // calculate attack target
            BaseUnit target = maxValueAction.target;
            if (null != target) {
                switch (maxValueAction.playerAction) {
                    case ATTACK:
                        try {
                            army.attack(battle, target, false, isEnemy, isSim);
                        } catch (OutOfAttackRangeException ooare) {
                            ooare.printStackTrace();
                        } catch (CounterattackHappenedException che) {
                            LogUtil.printInfo(battle.map.getCurrentRoundNo(), "AI01Active: ATTACK, get Counter attack");
                        }
                        break;
                    case USE_ITEM:
                        try {
                            army.useItem(maxValueAction.itemIdx, target);
                        } catch (BaseException e) {
                            throw new RuntimeException(e);
                        }
                }

            }
        } else {
            LogUtil.printInfo(battle.map.getCurrentRoundNo(), "No player could be attacked.");
        }
    }

    private boolean findAttackTarget(BattleInfo battle, BaseUnit army, Set<MapItem> range) {
        for (MapItem mi : range) {
            try {
                if (!(army instanceof Bow)) {
                    if (null != mi.army && battle.playerUnits.contains(mi.army)) {
                        return true;
                    }
                    if (mi.y - 1 >= 0) {
                        MapItem north = battle.map.map[mi.y - 1][mi.x];
                        if (null != north.army && battle.playerUnits.contains(north.army)) {
                            return true;
                        }
                    }
                    if (mi.x + 1 < battle.map.map[0].length) {
                        MapItem east = battle.map.map[mi.y][mi.x + 1];
                        if (null != east.army && battle.playerUnits.contains(east.army)) {
                            return true;
                        }
                    }
                    if (mi.y + 1 < battle.map.map.length) {
                        MapItem south = battle.map.map[mi.y + 1][mi.x];
                        if (null != south.army && battle.playerUnits.contains(south.army)) {
                            return true;
                        }
                    }
                    if (mi.x - 1 >= 0) {
                        MapItem west = battle.map.map[mi.y][mi.x - 1];
                        if (null != west.army && battle.playerUnits.contains(west.army)) {
                            return true;
                        }
                    }
                } else {
                    if (army instanceof BowSoldier) {
                        if (mi.y - 1 >= 0 && mi.x + 1 < battle.map.map[0].length) {
                            MapItem northWest = battle.map.map[mi.y - 1][mi.x + 1];
                            if (null != northWest.army && battle.playerUnits.contains(northWest.army)) {
                                return true;
                            }
                        }
                        if (mi.y + 1 < battle.map.map.length && mi.x + 1 < battle.map.map[0].length) {
                            MapItem southWest = battle.map.map[mi.y + 1][mi.x + 1];
                            if (null != southWest.army && battle.playerUnits.contains(southWest.army)) {
                                return true;
                            }
                        }
                        if (mi.y + 1 < battle.map.map.length && mi.x - 1 >= 0) {
                            MapItem southEast = battle.map.map[mi.y + 1][mi.x - 1];
                            if (null != southEast.army && battle.playerUnits.contains(southEast.army)) {
                                return true;
                            }
                        }
                        if (mi.y - 1 >= 0 && mi.x - 1 >= 0) {
                            MapItem northEast = battle.map.map[mi.y - 1][mi.x - 1];
                            if (null != northEast.army && battle.playerUnits.contains(northEast.army)) {
                                return true;
                            }
                        }
                        if (mi.x + 2 < battle.map.map[0].length) {
                            MapItem east = battle.map.map[mi.y][mi.x + 2];
                            if (null != east.army && battle.playerUnits.contains(east.army)) {
                                return true;
                            }
                        }
                        if (mi.x - 2 >= 0) {
                            MapItem west = battle.map.map[mi.y][mi.x - 2];
                            if (null != west.army && battle.playerUnits.contains(west.army)) {
                                return true;
                            }
                        }
                        if (mi.y + 2 < battle.map.map.length) {
                            MapItem south = battle.map.map[mi.y + 2][mi.x];
                            if (null != south.army && battle.playerUnits.contains(south.army)) {
                                return true;
                            }
                        }
                        if (mi.y - 2 >= 0) {
                            MapItem north = battle.map.map[mi.y - 2][mi.x];
                            if (null != north.army && battle.playerUnits.contains(north.army)) {
                                return true;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
        return false;
    }

    private String parseValuesArray2String(Action[][] actions) {
        StringBuilder builder = new StringBuilder("\n");
        for (Action[] action : actions) {
            builder.append("[");
            for (int x = 0; x < actions[0].length; x++) {
                builder.append(action[x].actionValue);
                if (x < actions[x].length - 1) {
                    if (action[x].actionValue > 9) {
                        builder.append(",");
                    } else {
                        builder.append(", ");
                    }
                }
            }
            builder.append("]\n");
        }
        return builder.toString();
    }
}
