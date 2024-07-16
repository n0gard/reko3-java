package az.test.reko3ibm;

import az.test.battle.BattleInfo;
import az.test.exception.*;
import az.test.model.army.BaseUnit;
import az.test.model.map.MapItem;
import az.test.util.LogUtil;

import com.alibaba.fastjson.JSON;

public class AI01Active extends ActionAIType {

    public AI01Active(boolean isEnemy) {
        super();
        super.isEnemy = isEnemy;
    }

    @Override
    public void action(BattleInfo battle, BaseUnit army, boolean isSim) {
        LogUtil.printLog(battle.map.getCurrentRoundNo(), "action", army.name, "AI01", "action start");
        // calculate action value
        Action[][] actionValuesArray = botAction.generateMyActionValues();
        LogUtil.printLog(battle.map.getCurrentRoundNo(), "action", army.name, "AI01", "values: "
                + JSON.toJSONString(actionValuesArray));
        // calculate move range
        army.calculateMoveRange(battle, army.moveAbility, army.y, army.x);
        MapItem maxValueCoordinate = null;
        Action maxValueAction = null;
        int maxValue = Integer.MIN_VALUE;
        for (MapItem c : army.canMoveToCoordinateRange) {
            if (actionValuesArray[c.y][c.x].actionValue > maxValue && null == battle.map.map[c.y][c.x].army) {
                maxValue = actionValuesArray[c.y][c.x].actionValue;
                maxValueCoordinate = c;
                maxValueAction = actionValuesArray[c.y][c.x];
            }
        }
        // move to max value target
        // try {
        army.moveTo(battle, maxValueCoordinate.y, maxValueCoordinate.x, isSim);
        // } catch (OutOfMoveRangeException e) {
        // e.printStackTrace();
        // return;
        // }
        // calculate attack target
        BaseUnit target = maxValueAction.target;
        if (null != target) {
            switch (maxValueAction.playerAction) {
                case ATTACK:
                    try {
                        army.attack(battle, target, false, isEnemy, isSim);
                    } catch (OutOfAttackRangeException ooare) {
                        ooare.printStackTrace();
                    } catch (CounterattackHappenedException che) {
                        LogUtil.printInfo(battle.map.getCurrentRoundNo(), "AI01Active: ATTACK, get Counter attack");
                    }
                    break;
                case USE_ITEM:
                    try {
                        army.useItem(maxValueAction.itemIdx, target);
                    } catch (BaseException e) {
                        throw new RuntimeException(e);
                    }
            }
        }
    }

}
