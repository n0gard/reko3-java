package tm.mcts.mcts4j.reko3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tm.mcts.mcts4j.DefaultNode;
import tm.mcts.mcts4j.Node;
import tm.mcts.mcts4j.UCT;
import az.test.battle.BattleInfo;
import az.test.battle.enums.PlayerAction;
import az.test.exception.CounterattackHappenedException;
import az.test.exception.MaxPlayerUnitsLimitedException;
import az.test.exception.OutOfAttackRangeException;
import az.test.map.BattleMap000TEST001;
import az.test.map.BattleMap01;
import az.test.model.PlayerUnitGenerator;
import az.test.model.army.BaseUnit;
import az.test.model.map.MapItem;
import az.test.reko3ibm.Exe;

import com.alibaba.fastjson.JSON;

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
 * 
 */
public class Reko3Aii extends UCT<Reko3Transition, DefaultNode<Reko3Transition>> {

	private BattleInfo battle;
	public int currentPlayerId; // 0 player 1 reko3 ai

	public Reko3Aii() {
		super();
		battle = new BattleInfo();
		try {
			// load map first !!!
			battle.loadMap(new BattleMap000TEST001());
			// load player
			BaseUnit royalUncleLiu = PlayerUnitGenerator.loadLiuBei(1, 1, null);
			battle.addPlayerUnit(royalUncleLiu);
			battle.map.loadSomeone(royalUncleLiu);
		} catch (MaxPlayerUnitsLimitedException e) {
			e.printStackTrace();
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
		if (!IS_SIM)
			System.out.println("[Reko3A]isOver    player win ? " + battle.map.isPlayerSuccess() + " | Rounds limited ? "
					+ battle.map.isRunningOutOfRounds() + " | someone died ? "
					+ battle.areAllPlayersEvacuatedOrSomeoneEvacuated());
		return battle.map.isPlayerSuccess() || battle.map.isRunningOutOfRounds()
				|| battle.areAllPlayersEvacuatedOrSomeoneEvacuated();
	}

	@Override
	public void makeTransition(Reko3Transition transition) {
		if (!IS_SIM)
			System.out.println("[Reko3A]makeTransition " + transition);
		// TODO action (item, strategy)
		BaseUnit player = transition.getPlayerUnit();
		System.out.println("[Reko3A]makeTransition player " + player);
		BaseUnit target = transition.getTarget();
		System.out.println("[Reko3A]makeTransition target " + target);
		// transition.setLastBattleSnapshot(snapshot(transition.getBattle()));
		player.recordLastState();
		if (null != target) {
			target.recordLastState();
		}
		// try {
		player.moveTo(battle, transition.getY(), transition.getX(), IS_SIM);
		// } catch (OutOfMoveRangeException oomre) {
		// oomre.printStackTrace();
		// }
		switch (transition.getAction()) {
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
		battle.map.setCurrentRoundNo(battle.map.getCurrentRoundNo() + 1);
		next();
	}

	@Override
	public void unmakeTransition(Reko3Transition transition) {
		if (!IS_SIM)
			System.out.println("[Reko3A]unmakeTransition " + transition);
		// System.out.println(transition.getBattle().enemyUnits.get(0).currentArmyHP
		// + " <<===");
		// System.out.println(transition.getBattle().map.map[0][0].army.currentArmyHP
		// + " <<===");
		if (!IS_SIM)
			System.out.println("[Reko3A]unmakeTransition --> " + battle.getTimestamp());
		// if (!IS_SIM)
		// System.out.println("[Reko3A]unmakeTransition transition stores: " +
		// battle.playerUnits.get(0).name
		// + " pos: " + battle.playerUnits.get(0).y + "," +
		// battle.playerUnits.get(0).x);
		// if (!IS_SIM)
		// System.out.println("[Reko3A]unmakeTransition after restore: " +
		// battle.playerUnits.get(0).name + " pos: "
		// + battle.playerUnits.get(0).y + "," + battle.playerUnits.get(0).x);
		previous();
		transition.getPlayerUnit().restoreLastState(battle);
		if (null != transition.getTarget()) {
			transition.getTarget().restoreLastState(battle);
		}
		if (battle.map.getCurrentRoundNo() > 1 && battle.map.getCurrentRoundNo() < transition.getRound()) {
			battle.map.setCurrentRoundNo(battle.map.getCurrentRoundNo() - 1);
			System.out.println("[Reko3A]unmakeTransition restore round from " + transition.getRound() + " to " + battle.map.getCurrentRoundNo());
		}
		
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
		if (!IS_SIM)
			System.out.println("[Reko3A]getPossibleTransitions");
		Set<Reko3Transition> moves = new HashSet<Reko3Transition>();
		BaseUnit currentPlayer = pickPlayerUnit();
		if (null == currentPlayer) {
			return moves;
		}
		currentPlayer.canMoveToCoordinateRange = new ArrayList<MapItem>();
		currentPlayer.calculateMoveRange(battle, currentPlayer.moveAbility, currentPlayer.y, currentPlayer.x);

		for (MapItem mi : currentPlayer.canMoveToCoordinateRange) {
			Reko3Transition r3t = null;
			BaseUnit target = currentPlayer.calculateAssignedPositionAttackTarget(battle, mi.y, mi.x, currentPlayer.y,
					currentPlayer.x);
			if (null != target) {
				r3t = new Reko3Transition(mi.y, mi.x, currentPlayer, PlayerAction.ATTACK, target, battle.map.getCurrentRoundNo());
				// TODO strategy
				// TODO item
			} else {
				r3t = new Reko3Transition(mi.y, mi.x, currentPlayer, PlayerAction.REST, null, battle.map.getCurrentRoundNo());
			}
			if (!moves.contains(r3t)) {
				moves.add(r3t);
				// if (!IS_SIM)
				// System.out.println(r3t);
			}
		}
		System.out.println("[Reko3A]getPossibleTransitions " + moves);
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
		if (!IS_SIM)
			System.out.println("[Reko3A]previous");
		battle.map.setCurrentRoundNo(battle.map.getCurrentRoundNo() - 1);
	}

	public String toString() {
		return JSON.toJSONString(battle);
	}

	@Override
	public Reko3Transition simulationTransition(Set<Reko3Transition> possibleTransitions) {
		if (!IS_SIM)
			System.out.println("[Reko3A]simulationTransition");
		List<Reko3Transition> transitions = new ArrayList<Reko3Transition>(possibleTransitions);
		return transitions.get((int) Math.floor(Math.random() * possibleTransitions.size()));
	}

	@Override
	public Reko3Transition expansionTransition(Set<Reko3Transition> possibleTransitions) {
		if (!IS_SIM)
			System.out.println("[Reko3A]expansionTransition");
		List<Reko3Transition> transitions = new ArrayList<Reko3Transition>(possibleTransitions);
		return transitions.get((int) Math.floor(Math.random() * possibleTransitions.size()));
	}

	@Override
	public int getWinner() {
		if (!IS_SIM)
			System.out.println("[Reko3A]getWinner --------------------------------------> "
					+ (battle.map.lord.isEvacuated ? 0 : 1));
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