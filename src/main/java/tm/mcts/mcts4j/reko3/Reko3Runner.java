package tm.mcts.mcts4j.reko3;

import tm.mcts.mcts4j.MonteCarloTreeSearch;
import tm.mcts.mcts4j.Node;

/*
 * This file is part of minimax4j.
 * <https://github.com/avianey/minimax4j>
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
 * Run a game between two TicTacToeIA opponent...
 * 
 * @author antoine vianey
 */
public class Reko3Runner extends Runner<Reko3Transition> {

	public Reko3Runner() {
		// Change the thinking depth value > 0
		super(new Reko3Aii());
	}

	public static void main(String[] args) {
		Runner<Reko3Transition> runner = new Reko3Runner();
		Listener<Reko3Transition> l = new Listener<Reko3Transition>() {

			@Override
			public void onMove(MonteCarloTreeSearch<Reko3Transition, ? extends Node<Reko3Transition>> mcts,
					Reko3Transition transition, int turn) {
				System.out.println("round: " + turn + " " + transition.getPlayerUnit().name + " "
						+ transition.getAction() + " y: " + transition.getY() + " x: " + transition.getX());
//				System.out.println(transition);
			}

			@Override
			public void onGameOver(MonteCarloTreeSearch<Reko3Transition, ? extends Node<Reko3Transition>> mcts) {
				System.out.println("[Reko3Runner][Listener][onGameOver]Game over.");
			}

			@Override
			public void onNoPossibleMove(MonteCarloTreeSearch<Reko3Transition, ? extends Node<Reko3Transition>> mcts) {
				System.out.println("[Reko3Runner][Listener][onGameOver]No possible move(s)");
			}
		};
		runner.setListener(l);
		runner.run();
	}

}