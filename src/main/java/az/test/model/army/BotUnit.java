package az.test.model.army;

import az.test.battle.BattleInfo;
import az.test.reko3ibm.Action;
import az.test.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class BotUnit extends BaseUnit implements BotAction {
    public Action[][] actionValuesArray;
    public List<AttackRange> attackRangeList = new ArrayList<>();

    public BotUnit(BattleInfo battle) {
        super(battle);
        this.actionValuesArray = new Action[battle.map.map.length][battle.map.map[0].length];
    }

    public Action[][] generateMyActionValues(BattleInfo battle) {
        // step 0 init values
        fillValuesArray(actionValuesArray);

        // step 1 deal with weak army
        if (this.isWeak()) {
            increaseRestorePlacesValue(battle.map.map, actionValuesArray);
        }

        // step 2 attack
        for (BaseUnit target : battle.playerUnits) {
            if (target.isEvacuated) {
                continue;
            }
            int attackBaseValue = (calculateAP() - generateDefensiveCorrection(target) / 2)
                    * (100 - queryMapItemCorrection(target.currentPositionMap)) / 100 + this.currentArmyHP / 6;
            if (null != this.specialEnemy) {
                if (target.name.equals(this.specialEnemy.name)) {
                    attackBaseValue += 30;
                }
            }
            int attackActionValue = attackBaseValue / 16;
            if (attackActionValue < 1) {
                attackActionValue = 1;
            }

            for (AttackRange ar : attackRangeList) {
                ar.fillAttackRangeValues(actionValuesArray, target, attackActionValue);
            }

        }
        for (int i = 0; i < actionValuesArray.length; i++) {
            for (int j = 0; j < actionValuesArray[i].length; j++) {
                if (actionValuesArray[i][j].actionValue > 9) {
                    LogUtil.printInfo(battle.getMap().getCurrentRoundNo(), actionValuesArray[i][j].toString());
                } else {
                    LogUtil.printInfo(battle.getMap().getCurrentRoundNo(), " " + actionValuesArray[i][j]);
                }
            }
            LogUtil.printInfo(battle.getMap().getCurrentRoundNo(), "");
        }
        return actionValuesArray;
    }

}
