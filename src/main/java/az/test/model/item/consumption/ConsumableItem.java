package az.test.model.item.consumption;

import az.test.battle.BattleInfo;
import az.test.exception.BaseException;
import az.test.model.army.BaseUnit;
import az.test.model.effect.Effection;
import az.test.model.item.BaseItem;

/**
 * 00 遁甲天书 01 青囊书 02 鼓吹具<br>
 * 10 弓术指南书 11 马术指南书 12 剑术指南书<br>
 * 13 长枪 14 步兵车<br>
 * 15 连弩 16 发石车<br>
 * 17 马铠 18 近卫铠<br>
 * 19 无赖精神 1A 侠义精神<br>
 * 1B 酒 1C 特级酒 1D 老酒<br>
 * 1E 豆 1F 麦 20 米<br>
 * 21 炸弹<br>
 * 22 落石书 23 山崩书 24 山洪书<br>
 * 25 旋涡书 26 浊流书 27 海啸书<br>
 * 28 焦热书 29 火龙书 2A 猛火书<br>
 * 2B 浓雾书 2C 雷阵雨书 2D 豪雨书<br>
 * 2E 援队书 2F 援部书 30 援军书<br>
 * 31 平气书 32 活气书 33 勇气书<br>
 * 34 伤药 35 中药 36 茶<br>
 */
public abstract class ConsumableItem extends BaseItem implements ItemConsume {
    public Effection effection;

    public ConsumableItem(int id, String name, Effection effection) {
        super(id, name);
        this.effection = effection;
    }

    @Override
    public boolean consumptionCouldBeHappened(BattleInfo info) {
        return true;
    }

    @Override
    public boolean consumptionCouldBeHappened(BaseUnit target) {
        return true;
    }

    @Override
    public void consume(BaseUnit player, BaseUnit... targets) throws BaseException {
        effection.effect(player, null, targets);
    }

    @Override
    public void reduceItem(BaseUnit player) {
        player.items.remove(this);
    }
}
