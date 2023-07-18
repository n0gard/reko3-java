package tm.mcts;

import java.util.List;

public class MCTSTester {
	public static int avgExpGained = 0;

	public static void main(String[] args) {
		MCTSNode root = new MCTSNode();

		if (!root.battle.map.isPlayerSuccess()) {

		}

		List<MCTSNode> nodes = root.nodes;
		// no leaves
		if (0 == nodes.size()) {

		}
		// choose
		else {
			double maxUCB = Double.MIN_VALUE;
			MCTSNode nextChoose = null;
			for (MCTSNode leaf : nodes) {
				leaf.calculateUCBValue();
				if (maxUCB < leaf.ucbValue) {
					maxUCB = leaf.ucbValue;
					nextChoose = leaf;
				}
			}
			select(nextChoose);
		}
	}

	public static void select(MCTSNode node) {
		node.visitedCount++;
		List<MCTSNode> nodes = node.nodes;
		// no leaves
		if (0 == nodes.size() || node.finalExpGained < avgExpGained) {
			expansion(node);
		}
		// choose
		else {
			double maxUCB = Double.MIN_VALUE;
			MCTSNode nextChoose = null;
			for (MCTSNode leaf : nodes) {
				leaf.calculateUCBValue();
				if (maxUCB < leaf.ucbValue) {
					maxUCB = leaf.ucbValue;
					nextChoose = leaf;
				}
			}
			select(nextChoose);
		}
	}

	public static void expansion(MCTSNode node) {
		
	}
}
