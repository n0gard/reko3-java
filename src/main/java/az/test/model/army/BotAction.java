package az.test.model.army;

import az.test.battle.BattleInfo;
import az.test.battle.enums.PlayerAction;
import az.test.model.map.Abatis;
import az.test.model.map.Barrack;
import az.test.model.map.MapItem;
import az.test.model.map.Village;
import az.test.reko3ibm.Action;
import az.test.util.LogUtil;

public class BotAction {
    public Action[][] actionValuesArray;
    public BaseUnit army;
    public BattleInfo battleInfo;

    public BotAction(BaseUnit army, BattleInfo battle) {
        this.army = army;
        this.battleInfo = battle;
        this.actionValuesArray = new Action[battle.map.map.length][battle.map.map[0].length];
    }


    /**
     * 英杰传移动型AI算法<br>
     * <br>
     * 定义：   (A)部队当前的横纵坐标；<br>
     * (B)部队的目标坐标；<br>
     * (C)部队本回合的目标坐标。<br>
     * <br>
     * 若AI=0（移动）或4（无攻击移动）：<br>
     * <br>
     * 1、如果存在仇人，则取仇人的坐标为目标坐标(B)。<br>
     * 2、在移动范围内，查找最近敌的战场代码。查找方法为：<br>
     * 2.1、以(A)为中心，将所有的坐标都标上其消耗的总移动力，移动范围之外的标记-1<br>
     * 2.2、令x=0~移动力的循环，每次循环，都按逐行扫描的方式检查该坐标的移动力总消耗是否等于x，如果是，就按上右下左的顺序检查该格的周围四格是否有敌人，若有，则结束查找，返回该敌人的战场代码。<br>
     * 2.3、如果查找不到敌人，则返回-1。<br>
     * 3、如果最近敌恰好为仇人，那么<br>
     * 3.1、若(B)没人则(B)的行动价值+30；<br>
     * 3.2、以(B)为中心，周围4格中属于(A)为中心的移动范围内的格子，行动价值+30（若(B)没人则只+10）；<br>
     * 3.3、以(B)为中心，上2右2下2左2以及斜四格中属于(A)为中心的移动范围的格子，行动价值+10（若(B)没人则不加）；<br>
     * 4、如果最近敌不是仇人或没有仇人或之前返回-1，则<br>
     * 4.1、以(B)作为中心坐标，移动力无限，计算战场上所有坐标的总消耗移动力；<br>
     * 4.2、如果发现(B)到(A)的路线被封堵（注意是(B)到(A)不是(A)到(B)，即以无限的移动力经过一个回合的移动无法到达(A)，或者(B)是不可移动地形），则本回合采用AI=1（攻击最近敌）的方式操作，否则转下一步。<br>
     * 4.3、取(C)=(A)；<br>
     * 4.4、以(C)作为中心坐标，按上右下左的顺序查找周围四格中总消耗移动力最小的格子，并用那个格子取代(C)作为新的(C)；<br>
     * 4.5、重复4.4，直到移动力消耗完毕或者(C)=(B)为止。<br>
     * 4.6、若(C)没人则(C)的行动价值+30；<br>
     * 4.7、以(C)为中心，周围4格中属于(A)为中心的移动范围内的格子，行动价值+30（若(C)没人则只+10）；<br>
     * 4.8、以(C)为中心，上2右2下2左2以及斜四格中属于(A)为中心的移动范围的格子，行动价值+10（若(C)没人则不加）；<br>
     * <br>
     * <br>
     * 若AI=1（攻击最近敌）<br>
     * <br>
     * 1、以(A)为中心，如果移动范围+攻击范围内有敌人，则按照AI=3（休息）的方式操作；<br>
     * 2、以(A)为中心，无限移动力，如果移动范围+攻击范围内没有敌人，则本回合不动，也不用策略，否则转第3步；<br>
     * 3、查找最近敌坐标<br>
     * 3.1、以(A)为中心，将所有的坐标都标上其消耗的总移动力，移动范围之外的标记-1<br>
     * 3.2、令x=0~移动力的循环，每次循环，都按逐行扫描的方式检查该坐标的移动力总消耗是否等于x，如果是，就按上右下左的顺序检查该格的周围四格是否有敌人，若有，则结束查找，返回该敌人的战场代码。<br>
     * 3.3、(C)取为该敌人的坐标<br>
     * 3.4、以(B)作为中心坐标，移动力无限，计算战场上所有坐标的总消耗移动力；<br>
     * 3.5、如果发现(B)到(A)的路线被封堵（即以无限的移动力经过一个回合的移动无法到达(A)，或者(B)是不可移动地形），则本回合不动也不使用策略，否则转下一步。<br>
     * 3.6、取(C)=(A)；<br>
     * 3.7、以(C)作为中心坐标，按上右下左的顺序查找周围四格中总消耗移动力最小的格子，并用那个格子取代(C)作为新的(C)；<br>
     * 3.8、重复3.7，直到移动力消耗完毕或者(C)=(B)为止。<br>
     * 3.9、若(C)没人则(C)的行动价值+30；<br>
     * 3.10、以(C)为中心，周围4格中属于(A)为中心的移动范围内的格子，行动价值+30（若(C)没人则只+10）；<br>
     * 3.11、以(C)为中心，上2右2下2左2以及斜四格中属于(A)为中心的移动范围的格子，行动价值+10（若(C)没人则不加）；<br>
     * <br>
     * <br>
     * 最后，计算移动范围内各个坐标的行动价值。（AI=4时不计算攻击或策略的行动价值）<br>
     *
     */
    public Action[][] generateMyActionValues() {
        // step 0 init values
        fillValuesArray(actionValuesArray);

        // step 1 deal with weak army
        if (army.isWeak()) {
            increaseRestorePlacesValue(battleInfo.map.map, actionValuesArray);
        }

        // step 2 attack
        for (BaseUnit target : battleInfo.playerUnits) {
            if (target.isEvacuated) {
                continue;
            }
            int attackBaseValue = (army.calculateAP() - army.generateDefensiveCorrection(target) / 2)
                    * (100 - army.queryMapItemCorrection(target.currentPositionMap)) / 100 + army.currentArmyHP / 6;
            if (null != army.specialEnemy) {
                if (target.name.equals(army.specialEnemy.name)) {
                    attackBaseValue += 30;
                }
            }
            int attackActionValue = attackBaseValue / 16;
            if (attackActionValue < 1) {
                attackActionValue = 1;
            }

            for (AttackRange ar : army.attackRangeList) {
                ar.fillAttackRangeValues(actionValuesArray, target, attackActionValue);
            }

        }
//        for (int i = 0; i < actionValuesArray.length; i++) {
//            for (int j = 0; j < actionValuesArray[i].length; j++) {
//                if (actionValuesArray[i][j].actionValue > 9) {
//                    LogUtil.printlnInfo(battleInfo.getMap().getCurrentRoundNo(), actionValuesArray[i][j].toString());
//                } else {
//                    LogUtil.printlnInfo(battleInfo.getMap().getCurrentRoundNo(), " " + actionValuesArray[i][j]);
//                }
//            }
//            LogUtil.printlnInfo(battleInfo.getMap().getCurrentRoundNo(), "");
//        }
        return actionValuesArray;
    }

    private void fillValuesArray(Action[][] values) {
        for (int j = 0; j < values.length; j++) {
            for (int i = 0; i < values[j].length; i++) {
                values[j][i] = new Action(PlayerAction.REST, 1);
            }
        }
    }

    private void increaseRestorePlacesValue(MapItem[][] map, Action[][] values) {
        for (int j = 0; j < values.length; j++) {
            for (int i = 0; i < values[j].length; i++) {
                if (null == map[j][i].army && (map[j][i] instanceof Village || map[j][i] instanceof Abatis || map[j][i] instanceof Barrack))
                    values[j][i].actionValue += 50;
            }
        }
    }
}
