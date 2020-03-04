package tm.mcts.mcts4j.reko3;

import az.test.battle.enums.PlayerAction;
import az.test.model.army.BaseUnit;
import tm.mcts.mcts4j.Transition;

/**
 * A basic move implementation : who and where...
 * 
 * @author Tommy
 */
public class Reko3Transition implements Transition {

	/** player unit */
	private BaseUnit playerUnit;

	/** y coordinate of the move */
	private int y;
	/** x coordinate of the move */
	private int x;
	private PlayerAction action;
	private BaseUnit target;
	private int round;

	public Reko3Transition(int y, int x, BaseUnit playerUnit, PlayerAction action, BaseUnit target, int round) {
		this.y = y;
		this.x = x;
		this.playerUnit = playerUnit;
		this.action = action;
		this.target = target;
		this.round = round;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((playerUnit == null) ? 0 : playerUnit.hashCode());
		result = prime * result + round;
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reko3Transition other = (Reko3Transition) obj;
		if (action != other.action)
			return false;
		if (playerUnit == null) {
			if (other.playerUnit != null)
				return false;
		} else if (!playerUnit.equals(other.playerUnit))
			return false;
		if (round != other.round)
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Reko3Transition [playerUnit=" + playerUnit + ", y=" + y + ", x=" + x + ", action=" + action
				+ ", target=" + target + ", round=" + round + "]";
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public BaseUnit getPlayerUnit() {
		return playerUnit;
	}

	public void setPlayerUnit(BaseUnit playerUnit) {
		this.playerUnit = playerUnit;
	}

	public PlayerAction getAction() {
		return action;
	}

	public void setAction(PlayerAction action) {
		this.action = action;
	}

	public BaseUnit getTarget() {
		return target;
	}

	public void setTarget(BaseUnit target) {
		this.target = target;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

}