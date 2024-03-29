package tm.mcts.mcts4j.tictactoe;

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
public class TicTacToeRunner extends SampleRunner<TicTacToeTransition> {

	public TicTacToeRunner() {
		// Change the thinking depth value > 0
		super(new TicTacToeIA());
	}

	public static void main(String[] args) {
		SampleRunner<TicTacToeTransition> runner = new TicTacToeRunner();
		Listener<TicTacToeTransition> l = new Listener<TicTacToeTransition>() {

			@Override
			public void onMove(MonteCarloTreeSearch<TicTacToeTransition, ? extends Node<TicTacToeTransition>> mcts,
					TicTacToeTransition transition, int turn) {
				System.out.println("round: " + turn + " " + transition.getPlayer() + " x: " + transition.getX() + " y: "
						+ transition.getY());
				System.out.println(transition);
			}

			@Override
			public void onGameOver(
					MonteCarloTreeSearch<TicTacToeTransition, ? extends Node<TicTacToeTransition>> mcts) {
				System.out.println("Game over.");
			}

			@Override
			public void onNoPossibleMove(
					MonteCarloTreeSearch<TicTacToeTransition, ? extends Node<TicTacToeTransition>> mcts) {
				System.out.println("No possible move(s)");
			}
		};
		runner.setListener(l);
		runner.run();
	}

}