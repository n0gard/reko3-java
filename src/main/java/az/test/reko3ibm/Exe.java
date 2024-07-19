package az.test.reko3ibm;

import az.test.battle.BattleInfo;
import az.test.battle.enums.BattleState;
import az.test.exception.*;
import az.test.map.BattleMap01;
import az.test.model.PlayerUnitGenerator;
import az.test.model.army.BaseUnit;
import az.test.util.LogUtil;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Exe {
    public static void readPlayerInput() {
        Scanner reader = new Scanner(System.in); // Reading from System.in
        System.out.println("Enter player cmd: ");
        String cmd = reader.nextLine();
        System.out.println("your input: " + cmd);
        // once finished
        reader.close();
    }

    public static void main(String[] args) {
        boolean isSim = false;
        BattleInfo ssgzz = new BattleInfo();
        // try {
        // load map first !!!
        ssgzz.loadMap(new BattleMap01());
//        ssgzz.loadMap(new BattleMap000TEST001());
//        ssgzz.loadMap(new BattleMapTestItemBug());
        // enemies
        ssgzz.map.loadEnemies(ssgzz);
        // friends, optional

        // add players
        try {
            // ssgzz.addPlayerUnit(PlayerUnitGenerator.getInstance(ssgzz).loadLiuBei(1, 2, null));
            ssgzz.addPlayerUnit(PlayerUnitGenerator.getInstance(ssgzz).loadLiuBei(9, 22, null));

            BaseUnit liubei = ssgzz.playerUnits.get(0);
            liubei.isLord = true;
        } catch (MaxPlayerUnitsLimitedException e) {
            throw new RuntimeException(e);
        }
        // ssgzz.addPlayerUnit(PlayerUnitGenerator.getInstance(ssgzz).loadLiuBei(9, 22));
        // ssgzz.addPlayerUnit(PlayerUnitGenerator.loadGuanyu(10, 20));
        // ssgzz.addPlayerUnit(PlayerUnitGenerator.loadZhangfei(9, 20));

        // } catch (MaxPlayerUnitsLimitedException e) {
        // e.printStackTrace();
        // }
        // System.out.println(JSON.toJSONString(ssgzz));
        String cmd = singleByLiuBei(ssgzz.map.getCurrentRoundNo());
        BattleState state = BattleState.INIT;

        while (true) {
            boolean allfinished = false;
            switch (state) {
                case INIT:
                    LogUtil.printlnInfo(
                            ssgzz.map.getCurrentRoundNo(),
                            "state",
                            state.name()
                                    + "==========================================================================================================================================");
                    ssgzz.initRound();
                    state = BattleState.PLAYER_OPERATION;
                    break;
                case PLAYER_OPERATION: {
                    LogUtil.printlnInfo(ssgzz.map.getCurrentRoundNo(), "state", state.name());
                    LogUtil.printlnInfo(ssgzz.map.getCurrentRoundNo(), "cmd: " + cmd);
                    // TODO COLLECTION PLAYER INFOS
                    // player select
                    String playerUnitNo = cmd.substring(cmd.indexOf("p") + 1, cmd.indexOf("m"));
                    int playerUnitIdx = Integer.parseInt(playerUnitNo);
                    BaseUnit lb = ssgzz.playerUnits.get(playerUnitIdx);
                    // move
                    String xy = cmd.substring(cmd.indexOf("m") + 1, cmd.indexOf("a"));
                    if (!xy.isEmpty()) {
                        int y = lb.y;
                        int x = lb.x;
                        // 坐标定位
                        if (xy.contains(",")) {
                            y = Integer.parseInt(xy.split(",")[0]);
                            x = Integer.parseInt(xy.split(",")[1]);
                        } else
                        // 相对位置定位
                        {
                            Coordinate targetCoordinate = parseRelativeCMD2Coordinate(y, x, xy);
                            y = targetCoordinate.y;
                            x = targetCoordinate.x;
                        }
                        // try {
                        lb.moveTo(ssgzz, y, x, isSim);
                        // } catch (OutOfMoveRangeException e) {
                        // e.printStackTrace();
                        // System.err.println(e.getWho().name + " wanna moved to ["
                        // + e.getWannaMovedTo().y + ","
                        // + e.getWannaMovedTo().x + "] army: " +
                        // e.getWannaMovedTo().army.name);
                        // System.exit(0);
                        // }
                    }
                    lb.drawMap(ssgzz, lb.y, lb.x);
                    // attack
                    String attackTargetCoordinate = cmd.substring(cmd.indexOf("a") + 1, cmd.indexOf("s"));
                    if (!attackTargetCoordinate.isEmpty()) {
                        int y = lb.y;
                        int x = lb.x;
                        // 绝对位置
                        if (attackTargetCoordinate.contains(",")) {
                            y = Integer.parseInt(attackTargetCoordinate.split(",")[0]);
                            x = Integer.parseInt(attackTargetCoordinate.split(",")[1]);
                        }
                        // 相对位置
                        else {
                            Coordinate targetCoordinate = parseRelativeCMD2Coordinate(y, x, attackTargetCoordinate);
                            y = targetCoordinate.y;
                            x = targetCoordinate.x;
                        }
                        BaseUnit target = ssgzz.queryUnitByCoordinate(y, x);
                        if (null == target) {
                            System.err.println("ERR: NULL TARGET");
                            System.exit(1);
                        }
                        try {
                            lb.attack(ssgzz, target, false, false, isSim);
                        } catch (OutOfAttackRangeException e) {
                            e.printStackTrace();
                        } catch (CounterattackHappenedException e) {
                            // TODO maybe we need to handle sth
                        }
                    }
                    // strategy
                    if (!cmd.substring(cmd.indexOf("s") + 1, cmd.indexOf("i")).isEmpty()) {
                        String strategyIndexStr = cmd.substring(cmd.indexOf("s") + 1, cmd.indexOf("s") + 2);
                        String strategyTargetCoordinate = cmd.substring(cmd.indexOf("s") + 2, cmd.indexOf("i"));

                        int idx = Integer.parseInt(strategyIndexStr);

                        if (!strategyTargetCoordinate.isEmpty()) {
                            int y = lb.y;
                            int x = lb.x;
                            if (strategyTargetCoordinate.contains(",")) {
                                y = Integer.parseInt(strategyTargetCoordinate.split(",")[0]);
                                x = Integer.parseInt(strategyTargetCoordinate.split(",")[1]);
                            } else {
                                Coordinate sTargetCoordinate = parseRelativeCMD2Coordinate(y, x, strategyTargetCoordinate);
                                y = sTargetCoordinate.y;
                                x = sTargetCoordinate.x;
                            }
                            BaseUnit target = ssgzz.queryUnitByCoordinate(y, x);
                            lb.strategy(idx, target);
                        }
                    }

                    // item
                    if (!cmd.substring(cmd.indexOf("i") + 1, cmd.length() - 1).isEmpty()) {
                        String itemIndexStr = cmd.substring(cmd.indexOf("i") + 1, cmd.indexOf("i") + 2);
                        String itemAndTarget = cmd.substring(cmd.indexOf("i") + 2, cmd.length() - 1);

                        int itemIdx = Integer.parseInt(itemIndexStr);
                        if (!itemAndTarget.isEmpty()) {
                            int y = lb.y;
                            int x = lb.x;
                            if (itemAndTarget.contains(",")) {
                                y = Integer.parseInt(itemAndTarget.split(",")[0]);
                                x = Integer.parseInt(itemAndTarget.split(",")[1]);
                            } else {
                                Coordinate itemTargetCoordinate = parseRelativeCMD2Coordinate(y, x, itemAndTarget);
                                y = itemTargetCoordinate.y;
                                x = itemTargetCoordinate.x;
                            }
                            BaseUnit target = ssgzz.queryEnemyUnitByCoordinate(y, x);
                            try {
                                lb.useItem(itemIdx, target);
                            } catch (BaseException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }

                    // rest
                    // System.out.println(lb);
                    determinationGameEnding(ssgzz);
                    state = BattleState.FRIENDS_ACTION;
                    break;
                }
                case FRIENDS_ACTION:
                    LogUtil.printlnInfo(ssgzz.map.getCurrentRoundNo(), "state", state.name());
                    friendArmyActions(ssgzz, isSim);
                    state = BattleState.ENEMY_ACTION;
                    break;
                case ENEMY_ACTION:
                    LogUtil.printlnInfo(ssgzz.map.getCurrentRoundNo(), "state", state.name());
                    enemyArmyActions(ssgzz, isSim);
                    state = BattleState.DETERMINATION;
                    break;
                case DETERMINATION:
                    LogUtil.printlnInfo(ssgzz.map.getCurrentRoundNo(), "state", state.name());
                    if (ssgzz.map.isPlayerSuccess()) {
                        statistics(ssgzz);
                        System.out.println("GG.");
                        state = BattleState.FINISHED;
                        break;
                    }
                    if (ssgzz.areAllPlayersEvacuatedOrSomeoneEvacuated()) {
                        statistics(ssgzz);
                        System.out.println("GG.");
                        state = BattleState.FINISHED;
                        break;
                    }

                    // TODO end round or end all
                    if (ssgzz.map.isRunningOutOfRounds()) {
                        System.out.println("Running out of rounds.");
                        statistics(ssgzz);
                        System.out.println("GG.");
                        state = BattleState.FINISHED;
                    } else {
                        state = BattleState.INIT;
                        ssgzz.map.currentRoundNo++;
                        cmd = singleByLiuBei(ssgzz.map.getCurrentRoundNo());
                    }
                    break;
                case FINISHED:
                    LogUtil.printlnInfo(ssgzz.map.getCurrentRoundNo(), "state", state.name());
                    allfinished = true;
                    break;
            }
            if (allfinished) {
                break;
            }
        }
        System.out.println("All done.");
    }

    private static Coordinate parseRelativeCMD2Coordinate(int y, int x, String xy) {
        if (null == xy || xy.isEmpty()) {
            return new Coordinate();
        }
        // 相对左移
        if (xy.contains("l")) {
            x -= Integer.parseInt(xy.substring(xy.indexOf("l") + 1, xy.indexOf("l") + 2));
        }
        // 相对右移
        if (xy.contains("r")) {
            x += Integer.parseInt(xy.substring(xy.indexOf("r") + 1, xy.indexOf("r") + 2));
        }
        // 相对上移
        if (xy.contains("u")) {
            y -= Integer.parseInt(xy.substring(xy.indexOf("u") + 1, xy.indexOf("u") + 2));
        }
        // 相对下移
        if (xy.contains("d")) {
            y += Integer.parseInt(xy.substring(xy.indexOf("d") + 1, xy.indexOf("d") + 2));
        }
        return new Coordinate(y, x);
    }

    private static String getDemoPlayMove(int round) {
        switch (round) {
            case 0:
                return "p0m10,19asir";
            case 1:
                return "p0m10,15asir";
            case 2:
                return "p0m11,14a10,14sir";
            case 3:
                return "p0m12,13a11,13sir";
            case 4:
                return "p0ma11,13sir";
            case 5:
                return "p0ma11,13sir";
            case 6:
                return "p0ma11,13sir";
            case 7:
                return "p0ma12,14sir";
            case 8:
                return "p0ma12,14sir";
            case 9:
                return "p0ma12,14sir";
            case 10:
                return "p0m9,12asir";
            case 11:
                return "p0m8,11a8,12sir";
            case 12:
                return "p0ma8,12sir";
            case 13:
                return "p0ma8,12sir";
            case 14:
                return "p0ma8,12sir";
            case 15:
                return "p0m8,8asir";
            case 16:
                return "p0m8,11asir";
            case 17:
                return "p0ma8,10sir";
            case 18:
                return "p0ma8,10sir";
            case 19:
                return "p0ma8,10sir";
            case 20:
                return "p0ma9,11sir";
            case 21:
                return "p0ma9,11sir";
            case 22:
                return "p0ma9,11sir";
            case 23:
                return "p0m8,10a8,9sir";
            default:
                return "p0masir"; // rest
        }
    }

    private static String singleByLiuBei(int round) {
        switch (round) {
            case 1:
                return "p0ml3d1asir";
            case 2:
                return "p0ml4asir";
            case 3:
            case 4:
                return "p0ml1d1au1sir";
            case 5:
            case 6:
                return "p0md0ar1sir";
            case 7:
            case 8:
                return "p0ml0au1sir";
            case 9:
                return "p0ml1u3asir";
            case 10:
                return "p0ml1u1ar1sir";
            case 11:
            case 12:
                return "p0ml0ar1sir";
            case 13:
                return "p0ml3asir";
            case 14:
                return "p0mr3asir";
            case 15:
            case 16:
            case 17:
                return "p0ml0al1sir";
            case 18:
            case 19:
            case 20:
                return "p0ml0ad1sir";
            case 21:
                return "p0ml1al1sir";
            case 22:
                return "p0mu1al1sir";
            case 23:
                return "p0mr0al1sir";
            case 24:
                return "p0ml3asir";
            case 25:
                return "p0ml3d1asi1l1d1r";
            case 26:
            case 27:
                return "p0ml0asi1l1d1r";
            case 28:
                return "p0mr2u2asir";
            case 29:
                return "p0ml2d2al1sir";
            case 30:
                return "p0ml0al1sir";
            default:
                return "p0masir"; // rest
        }
    }

    private static String getTestItemBug(int round) {
        switch (round) {
            case 0:
                return "p0m1,1r";
            case 1:
                return "p0m0,1asi0,0,0r";
            default:
                return "p0m0,0asir"; // rest
        }
    }

    public static void determinationGameEnding(BattleInfo bi) {
        if (bi.map.isPlayerSuccess()) {
            LogUtil.printlnInfo(bi.map.getCurrentRoundNo(), "You have won!");
            statistics(bi);
            System.exit(0);
        }
    }

    public static void statistics(BattleInfo bi) {
        System.out.println(JSON.toJSONString(bi));
    }

    private static void friendArmyActions(BattleInfo battle, boolean isSim) {
        List<BaseUnit> friends = battle.friendUnits;
        List<BaseUnit> atRestorePlaceFriends = new ArrayList<>();
        for (BaseUnit friend : friends) {
            if (battle.map.isRestorePlace(friend.currentPositionMap)) {
                atRestorePlaceFriends.add(friend);
            }
        }
        for (BaseUnit friend : atRestorePlaceFriends) {
            action(battle, friend, isSim);
        }
        List<BaseUnit> weakFriends = new ArrayList<>();
        for (BaseUnit friend : friends) {
            if (friend.isWeak()) {
                weakFriends.add(friend);
            }
        }
        for (BaseUnit friend : weakFriends) {
            action(battle, friend, isSim);
        }
        for (BaseUnit friend : friends) {
            action(battle, friend, isSim);
        }
    }

    public static void enemyArmyActions(BattleInfo battle, boolean isSim) {
        List<BaseUnit> enemies = battle.enemyUnits;
        if (enemies.isEmpty()) {
            LogUtil.printlnInfo(battle.map.getCurrentRoundNo(), "Action][Enemy", " no enemies to action. ");
        }
        List<BaseUnit> atRestorePlaceEnemies = new ArrayList<>();
        for (BaseUnit enemy : enemies) {
            if (battle.map.isRestorePlace(enemy.currentPositionMap)) {
                atRestorePlaceEnemies.add(enemy);
            }
        }
        LogUtil.printlnInfo(battle.map.getCurrentRoundNo(), "Action][Enemy", atRestorePlaceEnemies.size()
                + " armies at restore place(s).");
        for (BaseUnit enemy : atRestorePlaceEnemies) {
            if (!enemy.isEvacuated) {
                action(battle, enemy, isSim);
            }
        }
        List<BaseUnit> weakEnemies = new ArrayList<>();
        for (BaseUnit enemy : enemies) {
            if (enemy.isWeak() && !enemy.isEvacuated) {
                weakEnemies.add(enemy);
            }
        }
        LogUtil.printlnInfo(battle.map.getCurrentRoundNo(), "Action][Enemy", weakEnemies.size() + " armies is(are) weak.");
        for (BaseUnit enemy : weakEnemies) {
            if (!enemy.roundFinished && !enemy.isEvacuated) {
                action(battle, enemy, isSim);
            }
        }
        for (BaseUnit enemy : enemies) {
            if (!enemy.roundFinished && !enemy.isEvacuated) {
                action(battle, enemy, isSim);
            }
        }
    }

    private static void action(BattleInfo bi, BaseUnit bu, boolean isSim) {
        if (bu.isEvacuated) {
            LogUtil.printlnInfo(bi.map.getCurrentRoundNo(), "action", bu + " is already out.");
            return;
        }
        if (bu.isInChaos) {
            LogUtil.printlnInfo(bi.map.getCurrentRoundNo(), "action", bu + " is in chaos.");
            return;
        }
        if (bu.roundFinished) {
            LogUtil.printlnInfo(bi.map.getCurrentRoundNo(), "action", bu + " has already finished this round.");
            return;
        }
        if (null != bu.aiType) {
            bu.aiType.action(bi, bu, isSim);
        }
        bu.roundFinished = true;
        LogUtil.printlnInfo(bi.map.getCurrentRoundNo(), "action", bu + " round finished.");
    }
}
