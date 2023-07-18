package tm.mcts;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import az.test.battle.BattleInfo;

public class MCTSNode {
	public BattleInfo battle;
	public int finalExpGained;
	public int visitedCount;
	public List<MCTSNode> nodes = new ArrayList<MCTSNode>();
	public MCTSNode myRoot;
	public double ucbValue = 1.0;
	public static final double UCB_C = 1.96;
	double totalWinTimes;

	public double calculateUCBValue() {
		if (null != myRoot && visitedCount > 0) {
			ucbValue = finalExpGained + UCB_C * (Math.log1p(myRoot.visitedCount) / visitedCount);
		}
		return ucbValue;
	}

	public void selectAction() {
		List<MCTSNode> visited = new LinkedList<MCTSNode>();
		MCTSNode cur = this;
		visited.add(this);
		while (!cur.isLeaf()) {
			cur = cur.select();
			visited.add(cur);
		}
		cur.expansion();
		MCTSNode newNode = cur.select();
		visited.add(newNode);
		// move to expansion
		double value = rollOut(newNode);
		for (MCTSNode node : visited) {
			// would need extra logic for n-player game
			node.updateStats(value);
		}
	}

	public MCTSNode select() {
		// choose
		double maxUCB = Double.MIN_VALUE;
		MCTSNode nextChoose = null;
		for (MCTSNode leaf : nodes) {
			leaf.calculateUCBValue();
			if (maxUCB < leaf.ucbValue) {
				maxUCB = leaf.ucbValue;
				nextChoose = leaf;
			}
		}
		return nextChoose;
	}

	public void expansion() {

	}

	public static double rollOut(MCTSNode tn) {
		// random a value ???
		return Math.random();
	}

	public void updateStats(double value) {
		visitedCount++;
		totalWinTimes += value;
	}

	public boolean isLeaf() {
		return 0 == nodes.size();
	}
}
