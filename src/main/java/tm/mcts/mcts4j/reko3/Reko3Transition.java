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
    private long transitionId;
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

    public Reko3Transition(long transitionId, BaseUnit player, int y, int x, PlayerAction action, int targetX, int targetY) {
        this.transitionId = transitionId;
        this.playerY = player.y;
        this.playerX = player.x;
        this.moveTargetY = y;
        this.moveTargetX = x;
        this.action = action;
        this.targetX = targetX;
        this.targetY = targetY;
    }

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