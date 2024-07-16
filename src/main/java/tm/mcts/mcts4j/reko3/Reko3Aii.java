package tm.mcts.mcts4j.reko3;

import az.test.battle.BattleInfo;
import az.test.battle.BattleInfoSnapshot;
import az.test.battle.enums.PlayerAction;
import az.test.exception.*;
import az.test.map.BattleMap000TEST001;
import az.test.model.PlayerUnitGenerator;
import az.test.model.army.BaseUnit;
import az.test.model.map.MapItem;
import az.test.reko3ibm.Action;
import az.test.reko3ibm.Exe;
import az.test.util.LogUtil;
import az.test.util.RandomHelper;
import com.alibaba.fastjson.JSON;
import tm.mcts.mcts4j.DefaultNode;
import tm.mcts.mcts4j.Node;
import tm.mcts.mcts4j.UCT;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * This file is part of mcts4j.
 * <https://github.com/avianey/mcts4j>
 *
 * Copyright (C) 2012 Antoine Vianey
 *
 * minimax4j is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * minimax4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with minimax4j. If not, see <http://www.gnu.org/licenses/lgpl.html>
 */

/**
 * Reko3 AIii
 *
 * @author Tommy
 */
public class Reko3Aii extends UCT<Reko3Transition, DefaultNode<Reko3Transition>> {
    private BattleInfo battle;
    private int totalGold = 500;
    private final static Map<Long, BattleInfoSnapshot> battleRecordMap = new ConcurrentHashMap<>();
    private static final AtomicInteger transitionId = new AtomicInteger(0);

    public Reko3Aii() {
        super();
        battle = new BattleInfo();
        try {
            // load map first !!!
            battle.loadMap(new BattleMap000TEST001());
            battle.map.loadEnemies(battle);
            // load player
            BaseUnit royalUncleLiu = PlayerUnitGenerator.getInstance(battle).loadLiuBei(1, 1, null);
            battle.addPlayerUnit(royalUncleLiu);
            royalUncleLiu.isLord = true;
        } catch (MaxPlayerUnitsLimitedException e) {
            System.err.println("Max Players Limited.");
        }
        System.out.println("PLAYERS: -> " + battle.playerUnits);
        System.out.println("ENEMIES: -> " + battle.enemyUnits);
        newGame();
    }

    public void newGame() {
        battle.initRound();
    }

    @Override
    public boolean isOver() {
        if (IS_SIM) {
            System.out.println("[Reko3A-Simulation]isOver    player win ? " + battle.map.isPlayerSuccess() + " | Rounds exceed limited ? "
                    + battle.map.isRunningOutOfRounds() + " limit-> " + battle.map.getRoundLimit() + ",currentRound:" + battle.map.getCurrentRoundNo() + "| someone died ? "
                    + battle.areAllPlayersEvacuatedOrSomeoneEvacuated());
        } else {
            System.out.println("[Reko3A]isOver    player win ? " + battle.map.isPlayerSuccess() + " | Rounds limited ? "
                    + battle.map.isRunningOutOfRounds() + " limit-> " + battle.map.getRoundLimit() + ",currentRound:" + battle.map.getCurrentRoundNo() + "| someone died ? "
                    + battle.areAllPlayersEvacuatedOrSomeoneEvacuated());
        }

        return battle.map.isPlayerSuccess() || battle.map.isRunningOutOfRounds()
                || battle.areAllPlayersEvacuatedOrSomeoneEvacuated();
    }

    @Override
    public void makeTransition(Reko3Transition transition) {
        if (IS_SIM) {
            System.out.println("[Reko3A-Simulation]makeTransition " + transition);
        } else {
            System.out.println("[Reko3A]makeTransition " + transition);
        }
        // TODO action (item, strategy)
        BaseUnit player = null;
        for (BaseUnit pbu : battle.playerUnits) {
            if (pbu.x == transition.getPlayerX() && pbu.y == transition.getPlayerY()) {
                player = pbu;
                break;
            }
        }
        System.out.println("[Reko3A]makeTransition player " + player);
        BaseUnit target = null;
        for (BaseUnit ebu : battle.enemyUnits) {
            if (ebu.x == transition.getTargetX() && ebu.y == transition.getTargetY()) {
                target = ebu;
                break;
            }
        }
        System.out.println("[Reko3A]makeTransition target " + target);

        // snapshot
        BattleInfoSnapshot biSnapshot = new BattleInfoSnapshot(battle);
        battleRecordMap.put(transition.getTransitionId(), biSnapshot);
        System.out.println("[Reko3Aii]makeTransition[SNAPSHOT]" + biSnapshot);
        // record last state

        // try {
        player.moveTo(battle, transition.getMoveTargetY(), transition.getMoveTargetX(), IS_SIM);
        // } catch (OutOfMoveRangeException oomre) {
        // oomre.printStackTrace();
        // }
        switch (transition.getAction().playerAction) {
            case REST:
                break;
            case ATTACK:
                try {
                    player.attack(battle, target, false, false, IS_SIM);
                } catch (OutOfAttackRangeException ooare) {
                    ooare.printStackTrace();
                } catch (CounterattackHappenedException che) {
                    che.printStackTrace();
                }
                break;
            case USE_ITEM:
                try {
                    player.useItem(transition.getAction().itemIdx, target);
                } catch (ItemIndexOutOfBoundException e) {
                    e.printStackTrace();
                } catch (BaseException e) {
                    throw new RuntimeException(e);
                }
                break;
            case TRANSPORT_ITEM:
                break;
            case DROP_ITEM:
                break;
            case STRATEGY:
                break;

        }

        Exe.enemyArmyActions(battle, IS_SIM);
        battle.initRound();
        next();
    }

    @Override
    public void unmakeTransition(Reko3Transition transition) {
        if (!IS_SIM)
            System.out.println("[Reko3A]unmakeTransition " + transition);
        else LogUtil.printInfo(battle.map.getCurrentRoundNo(), "[Simulation][Reko3Aii]unmakeTransition");
        // System.out.println(transition.getBattle().enemyUnits.get(0).currentArmyHP
        // + " <<===");
        // System.out.println(transition.getBattle().map.map[0][0].army.currentArmyHP
        // + " <<===");
        // if (!IS_SIM)
        // System.out.println("[Reko3A]unmakeTransition transition stores: " +
        // battle.playerUnits.get(0).name
        // + " pos: " + battle.playerUnits.get(0).y + "," +
        // battle.playerUnits.get(0).x);
        // if (!IS_SIM)
        // System.out.println("[Reko3A]unmakeTransition after restore: " +
        // battle.playerUnits.get(0).name + " pos: "
        // + battle.playerUnits.get(0).y + "," + battle.playerUnits.get(0).x);

//        transition.getPlayerUnit().restoreLastState(battle);
//        if (null != transition.getTarget()) {
//            transition.getTarget().restoreLastState(battle);
//        }

//        if (battle.map.getCurrentRoundNo() > 1 && battle.map.getCurrentRoundNo() < transition.getRound()) {
//            battle.map.setCurrentRoundNo(battle.map.getCurrentRoundNo() - 1);
        if (IS_SIM) {
            System.out.print("[Reko3A-Simulation]previous");
        } else {
            System.out.print("[Reko3A]previous");
        }
        System.out.print("[Reko3A]unmakeTransition previous round from " + battle.map.getCurrentRoundNo() + " to ");
        BattleInfoSnapshot snapshot = battleRecordMap.get(transition.getTransitionId());
        previous();
        System.out.println(battle.map.getCurrentRoundNo());
        if (null != snapshot) {
            System.out.println(snapshot);
            System.out.print("[Reko3A]unmakeTransition snapshot round from " + battle.map.getCurrentRoundNo() + " to ");
            battle = new BattleInfo(snapshot);
        } else {
            System.out.print("[Reko3A]unmakeTransition snapshot is NULL ");
        }
        if (null != snapshot) {
            System.out.println(snapshot.getMap().getCurrentRoundNo());
        } else {
            System.out.println();
        }
//        }
        battleRecordMap.remove(transition.getTransitionId());
    }

    private BaseUnit pickPlayerUnit() {
        for (BaseUnit playerUnit : battle.playerUnits) {
            if (!playerUnit.roundFinished) {
                return playerUnit;
            }
        }
        return null;
    }

    @Override
    public Set<Reko3Transition> getPossibleTransitions() {
//        if (IS_SIM) {
//            System.out.println("[Reko3A-Simulation]getPossibleTransitions");
//        } else {
//            System.out.println("[Reko3A]getPossibleTransitions");
//        }
        Set<Reko3Transition> moves = new HashSet<>();
        BaseUnit currentPlayer = pickPlayerUnit();
        if (null == currentPlayer) {
            return moves;
        }
        currentPlayer.canMoveToCoordinateRange = new HashSet<>();
        currentPlayer.calculateMoveRange(battle, currentPlayer.calculateMoveAbility(), currentPlayer.y, currentPlayer.x);

        for (MapItem mi : currentPlayer.canMoveToCoordinateRange) {
            Reko3Transition r3t = null;
            BaseUnit target = currentPlayer.calculateAssignedPositionAttackTarget(battle, mi.y, mi.x, currentPlayer.y,
                    currentPlayer.x);
            if (null != target) {
                r3t = new Reko3Transition(transitionId.incrementAndGet(), currentPlayer, mi.y, mi.x, new Action(PlayerAction.ATTACK, -1), target.x, target.y);
                // TODO strategy
                // TODO item
            } else {
                r3t = new Reko3Transition(transitionId.incrementAndGet(), currentPlayer, mi.y, mi.x, new Action(PlayerAction.REST, -1), -1, -1);
                // TODO strategy
                // TODO item
            }
            moves.add(r3t);
        }
//        for (Transition t : moves) {
//            System.out.println("[Reko3A]getPossibleTransitions " + t);
//        }
        return moves;
    }

    @Override
    public void next() {
        if (!IS_SIM)
            System.out.println("[Reko3A]next");
        battle.map.setCurrentRoundNo(battle.map.getCurrentRoundNo() + 1);
    }

    @Override
    public void previous() {
        battle.map.setCurrentRoundNo(battle.map.getCurrentRoundNo() - 1);
    }

    public String toString() {
        return JSON.toJSONString(battle);
    }

    @Override
    public Reko3Transition simulationTransition(Set<Reko3Transition> possibleTransitions) {
        if (!IS_SIM)
            System.out.println("[Reko3A]simulationTransition");
        List<Reko3Transition> transitions = new ArrayList<>(possibleTransitions);
        return transitions.get(RandomHelper.generateInt(0, possibleTransitions.size() - 1));
    }

    @Override
    public Reko3Transition expansionTransition(Set<Reko3Transition> possibleTransitions) {
//        if (IS_SIM) {
//            System.out.println("[Reko3A-Simulation]expansionTransition");
//        } else {
//            System.out.println("[Reko3A]expansionTransition");
//        }
        List<Reko3Transition> transitions = new ArrayList<>(possibleTransitions);
        int choosePossibleTransitionIdx = RandomHelper.generateInt(0, possibleTransitions.size() - 1);
//        System.out.println("[Reko3A]expansionTransition possibleTransitions.size()->" + possibleTransitions.size());
//        System.out.println("[Reko3A]expansionTransition choosePossibleTransitionIdx->" + choosePossibleTransitionIdx);
//        System.out.println("[Reko3A]expansionTransition choosePossibleTransitionUUID->" + transition.getTransitionId());
        return transitions.get(choosePossibleTransitionIdx);
    }

    @Override
    public int getWinner() {
        if (IS_SIM) {
            System.out.println("[Reko3A-Simulation]getWinner --------------------------------------> "
                    + (battle.map.lord.isEvacuated ? 0 : 1));
        } else {
            System.out.println("[Reko3A]getWinner --------------------------------------> "
                    + (battle.map.lord.isEvacuated ? 0 : 1));
        }
        return battle.map.lord.isEvacuated ? 0 : 1;
    }

    @Override
    public int getCurrentPlayer() {
        return 0;
    }

    @Override
    public DefaultNode<Reko3Transition> newNode(Node<Reko3Transition> parent, boolean terminal) {
        return new DefaultNode<Reko3Transition>(parent, terminal);
    }

}