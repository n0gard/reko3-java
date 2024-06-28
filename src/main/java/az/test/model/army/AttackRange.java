package az.test.model.army;

import az.test.battle.enums.PlayerAction;
import az.test.reko3ibm.Action;

import java.io.Serializable;

public abstract class AttackRange implements Serializable {

    protected void fillTargetAndActionValue(Action[][] actionArray, int y, int x, int value, BaseUnit target) {
        // 过滤掉不可攻击的范围（超过地图边界）
        if (y >= actionArray.length || x >= actionArray[0].length || y < 0 || x < 0) {
            return;
        }
        if (value > actionArray[y][x].actionValue) {
            actionArray[y][x].playerAction = PlayerAction.ATTACK;
            actionArray[y][x].actionValue = value;
            actionArray[y][x].target = target;
        }
    }

    public abstract void fillAttackRangeValues(Action[][] actionValuesArray, BaseUnit target, int attackActionValue);
}
