package az.test.reko3ibm;

import az.test.battle.BattleInfo;
import az.test.battle.enums.BattleState;
import az.test.exception.CounterattackHappenedException;
import az.test.exception.ItemIndexOutOfBoundException;
import az.test.exception.MaxPlayerUnitsLimitedException;
import az.test.exception.OutOfAttackRangeException;
import az.test.map.BattleMapTestItemBug;
import az.test.model.PlayerUnitGenerator;
import az.test.model.army.BaseUnit;
import az.test.model.army.BotUnit;
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
        // ssgzz.loadMap(new BattleMap01());
//        ssgzz.loadMap(new BattleMap000TEST001());
        ssgzz.loadMap(new BattleMapTestItemBug());
        // enemies
        ssgzz.map.loadEnemies(ssgzz);
        // friends, optional

        // add players
        try {
            ssgzz.addPlayerUnit(PlayerUnitGenerator.getInstance(ssgzz).loadLiuBei(1, 2, null));
            ssgzz.map.loadSomeone(ssgzz.playerUnits.get(0));
        } catch (MaxPlayerUnitsLimitedException e) {
            throw new RuntimeException(e);
        }
        // ssgzz.addPlayerUnit(PlayerUnitGenerator.loadGuanyu(10, 20));
        // ssgzz.addPlayerUnit(PlayerUnitGenerator.loadZhangfei(9, 20));

        // } catch (MaxPlayerUnitsLimitedException e) {
        // e.printStackTrace();
        // }
        // System.out.println(JSON.toJSONString(ssgzz));
        String cmd = getTestItemBug(ssgzz.map.getCurrentRoundNo());
        BattleState state = BattleState.INIT;
        while (true) {
            boolean allfinished = false;
            switch (state) {
                case INIT:
                    LogUtil.printInfo(
                            ssgzz.map.getCurrentRoundNo(),
                            "state",
                            state.name()
                                    + "==========================================================================================================================================");
                    ssgzz.initRound();
                    state = BattleState.PLAYER_OPERATION;
                    break;
                case PLAYER_OPERATION:
                    LogUtil.printInfo(ssgzz.map.getCurrentRoundNo(), "state", state.name());
                    LogUtil.printInfo(ssgzz.map.getCurrentRoundNo(), "cmd: " + cmd);
                    // TODO COLLECTION PLAYER INFOS
                    // player select
                    String playerUnitNo = cmd.substring(cmd.indexOf("p") + 1, cmd.indexOf("m"));
                    int playerUnitIdx = Integer.parseInt(playerUnitNo);
                    BaseUnit lb = ssgzz.playerUnits.get(playerUnitIdx);
                    // move
                    String xy = cmd.substring(cmd.indexOf("m") + 1, cmd.indexOf("a"));
                    if (!xy.isEmpty()) {
                        int y = Integer.parseInt(xy.split(",")[0]);
                        int x = Integer.parseInt(xy.split(",")[1]);
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
                    lb.drawMap(ssgzz, -1, -1);
                    // attack
                    String attackTargetCoordinate = cmd.substring(cmd.indexOf("a") + 1, cmd.indexOf("s"));
                    if (!attackTargetCoordinate.isEmpty()) {
                        int y = Integer.parseInt(attackTargetCoordinate.split(",")[0]);
                        int x = Integer.parseInt(attackTargetCoordinate.split(",")[1]);
                        BaseUnit target = ssgzz.queryUnitByCoordinate(y, x);
                        try {
                            lb.attack(ssgzz, target, false, false, isSim);
                        } catch (OutOfAttackRangeException e) {
                            e.printStackTrace();
                        } catch (CounterattackHappenedException e) {
                            // TODO maybe we need to handle sth
                        }
                    }

                    // strategy
                    String strategyTargetCoordinate = cmd.substring(cmd.indexOf("s") + 1, cmd.indexOf("i"));
                    if (!strategyTargetCoordinate.isEmpty()) {
                        int y = Integer.parseInt(strategyTargetCoordinate.split(",")[0]);
                        int x = Integer.parseInt(strategyTargetCoordinate.split(",")[1]);
                        BaseUnit target = ssgzz.queryUnitByCoordinate(y, x);
                        lb.strategy(target);
                    }

                    // item
                    String itemAndTarget = cmd.substring(cmd.indexOf("i") + 1, cmd.indexOf("r"));
                    if (!itemAndTarget.isEmpty()) {
                        int i = Integer.parseInt(itemAndTarget.split(",")[0]);
                        int y = Integer.parseInt(itemAndTarget.split(",")[1]);
                        int x = Integer.parseInt(itemAndTarget.split(",")[2]);
                        BaseUnit target = ssgzz.queryEnemyUnitByCoordinate(y, x);
                        try {
                            lb.useItem(i, target);
                        } catch (ItemIndexOutOfBoundException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    // rest
                    // System.out.println(lb);
                    determinationGameEnding(ssgzz);
                    state = BattleState.FRIENDS_ACTION;
                    break;
                case FRIENDS_ACTION:
                    LogUtil.printInfo(ssgzz.map.getCurrentRoundNo(), "state", state.name());
                    friendArmyActions(ssgzz, isSim);
                    state = BattleState.ENEMY_ACTION;
                    break;
                case ENEMY_ACTION:
                    LogUtil.printInfo(ssgzz.map.getCurrentRoundNo(), "state", state.name());
                    enemyArmyActions(ssgzz, isSim);
                    state = BattleState.DETERMINATION;
                    break;
                case DETERMINATION:
                    LogUtil.printInfo(ssgzz.map.getCurrentRoundNo(), "state", state.name());
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
                        cmd = getDemoPlayMove(ssgzz.map.getCurrentRoundNo());
                    }
                    break;
                case FINISHED:
                    LogUtil.printInfo(ssgzz.map.getCurrentRoundNo(), "state", state.name());
                    allfinished = true;
                    break;
            }
            if (allfinished) {
                break;
            }
        }
        System.out.println("All done.");
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
            LogUtil.printInfo(bi.map.getCurrentRoundNo(), "You have won!");
            statistics(bi);
            System.exit(0);
        }
    }

    public static void statistics(BattleInfo bi) {
        System.out.println(JSON.toJSONString(bi));
    }

    private static void friendArmyActions(BattleInfo battle, boolean isSim) {
        List<BotUnit> friends = battle.friendUnits;
        List<BotUnit> atRestorePlaceFriends = new ArrayList<>();
        for (BotUnit friend : friends) {
            if (battle.map.isRestorePlace(friend.currentPositionMap)) {
                atRestorePlaceFriends.add(friend);
            }
        }
        for (BotUnit friend : atRestorePlaceFriends) {
            action(battle, friend, isSim);
        }
        List<BotUnit> weakFriends = new ArrayList<>();
        for (BotUnit friend : friends) {
            if (friend.isWeak()) {
                weakFriends.add(friend);
            }
        }
        for (BotUnit friend : weakFriends) {
            action(battle, friend, isSim);
        }
        for (BotUnit friend : friends) {
            action(battle, friend, isSim);
        }
    }

    public static void enemyArmyActions(BattleInfo battle, boolean isSim) {
        List<BotUnit> enemies = battle.enemyUnits;
        if (enemies.isEmpty()) {
            LogUtil.printInfo(battle.map.getCurrentRoundNo(), "Action][Enemy", " no enemies to action. ");
        }
        List<BotUnit> atRestorePlaceEnemies = new ArrayList<>();
        for (BotUnit enemy : enemies) {
            if (battle.map.isRestorePlace(enemy.currentPositionMap)) {
                atRestorePlaceEnemies.add(enemy);
            }
        }
        LogUtil.printInfo(battle.map.getCurrentRoundNo(), "Action][Enemy", atRestorePlaceEnemies.size()
                + " armies at restore place(s).");
        for (BotUnit enemy : atRestorePlaceEnemies) {
            if (!enemy.isEvacuated) {
                action(battle, enemy, isSim);
            }
        }
        List<BotUnit> weakEnemies = new ArrayList<>();
        for (BotUnit enemy : enemies) {
            if (enemy.isWeak() && !enemy.isEvacuated) {
                weakEnemies.add(enemy);
            }
        }
        LogUtil.printInfo(battle.map.getCurrentRoundNo(), "Action][Enemy", weakEnemies.size() + " armies is(are) weak.");
        for (BotUnit enemy : weakEnemies) {
            if (!enemy.roundFinished && !enemy.isEvacuated) {
                action(battle, enemy, isSim);
            }
        }
        for (BotUnit enemy : enemies) {
            if (!enemy.roundFinished && !enemy.isEvacuated) {
                action(battle, enemy, isSim);
            }
        }
    }

    private static void action(BattleInfo bi, BotUnit bu, boolean isSim) {
        if (bu.isEvacuated) {
            LogUtil.printInfo(bi.map.getCurrentRoundNo(), "action", bu + " is already out.");
            return;
        }
        if (bu.isInChaos) {
            LogUtil.printInfo(bi.map.getCurrentRoundNo(), "action", bu + " is in chaos.");
            return;
        }
        if (bu.roundFinished) {
            LogUtil.printInfo(bi.map.getCurrentRoundNo(), "action", bu + " has already finished this round.");
            return;
        }
        if (null != bu.aiType) {
            bu.aiType.action(bi, bu, isSim);
        }
        bu.roundFinished = true;
        LogUtil.printInfo(bi.map.getCurrentRoundNo(), "action", bu + " round finished.");
    }
}
