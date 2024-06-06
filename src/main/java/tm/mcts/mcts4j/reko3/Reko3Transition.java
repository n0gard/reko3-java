package tm.mcts.mcts4j.reko3;

import az.test.battle.enums.PlayerAction;
import az.test.model.army.BaseUnit;
import lombok.Data;
import tm.mcts.mcts4j.Transition;

import java.util.Objects;

/**
 * A basic move implementation : who and where...
 *
 * @author Tommy
 */
@Data
public class Reko3Transition implements Transition {
    private String transitionId;
    /**
     * player unit coordinates
     */
    private int playerX;
    private int playerY;

    private int moveTargetX;
    private int moveTargetY;

    private PlayerAction action;
    private int targetX;
    private int targetY;

    public Reko3Transition(String transitionId, BaseUnit player, int y, int x, PlayerAction action, int targetX, int targetY) {
        this.transitionId = transitionId;
        this.playerY = player.y;
        this.playerX = player.x;
        this.moveTargetY = y;
        this.moveTargetX = x;
        this.action = action;
        this.targetX = targetX;
        this.targetY = targetY;
    }

//    @Override
//    public int hashCode() {
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + ((action == null) ? 0 : action.hashCode());
//        result = prime * result + ((playerUnit == null) ? 0 : playerUnit.hashCode());
//        result = prime * result + round;
//        result = prime * result + ((target == null) ? 0 : target.hashCode());
//        result = prime * result + x;
//        result = prime * result + y;
//        result = prime * result + Long.valueOf(transitionId).intValue();
//        return result;
//    }

//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj)
//            return true;
//        if (obj == null)
//            return false;
//        if (getClass() != obj.getClass())
//            return false;
//        Reko3Transition other = (Reko3Transition) obj;
//        if (action != other.action)
//            return false;
//        if (playerUnit == null) {
//            if (other.playerUnit != null)
//                return false;
//        } else if (!playerUnit.equals(other.playerUnit))
//            return false;
//        if (round != other.round)
//            return false;
//        if (target == null) {
//            if (other.target != null)
//                return false;
//        } else if (!target.equals(other.target))
//            return false;
//        if (x != other.x)
//            return false;
//        if (y != other.y)
//            return false;
//        if (transitionId != other.transitionId)
//            return false;
//        return true;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reko3Transition that = (Reko3Transition) o;
        return Objects.equals(transitionId, that.transitionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transitionId);
    }

    @Override
    public String toString() {
        return "Reko3Transition " + transitionId + "[playerX=" + playerX + ",playerY=" + playerY + ", moveTargetY=" + moveTargetY + ", moveTargetX=" + moveTargetX + ", action=" + action
                + ", targetX=" + targetX + ", targetY=" + targetY + "]";
    }

}