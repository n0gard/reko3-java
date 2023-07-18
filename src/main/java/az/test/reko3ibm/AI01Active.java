package az.test.reko3ibm;

import az.test.battle.BattleInfo;
import az.test.exception.CounterattackHappenedException;
import az.test.exception.OutOfAttackRangeException;
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
        // caculate action value
        Action[][] actionValuesArray = army.generateMyActionValues(battle);
        LogUtil.printLog(battle.map.getCurrentRoundNo(), "action", army.name, "AI01", "values: "
                + JSON.toJSONString(actionValuesArray));
        // caculate move range
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
        // caculate attack target
        BaseUnit target = maxValueAction.target;
        if (null != target) {
            if ("Attack".equals(maxValueAction.actionType)) {
                try {
                    army.attack(battle, target, false, isEnemy, isSim);
                } catch (OutOfAttackRangeException ooare) {
                    ooare.printStackTrace();
                } catch (CounterattackHappenedException che) {

                }
            } else if ("Strategy".equals(maxValueAction.actionType)) {
            } else if ("Rest".equals(maxValueAction.actionType)) {
            }
        }
    }

}
