package az.test.model.item.consumption;

import az.test.battle.BattleInfo;
import az.test.exception.BaseException;
import az.test.model.army.BaseUnit;
import az.test.model.effect.EffectAction;
import az.test.model.item.BaseItem;

/**
 * 00 遁甲天书 01 青囊书 02 鼓吹具
 * 10 弓术指南书 11 马术指南书 12 剑术指南书
 * 13 长枪 14 步兵车
 * 15 连弩 16 发石车
 * 17 马铠 18 近卫铠
 * 19 无赖精神 1A 侠义精神
 * 1B 酒 1C 特级酒 1D 老酒
 * 1E 豆 1F 麦 20 米
 * 21 炸弹
 * 22 落石书 23 山崩书 24 山洪书
 * 25 旋涡书 26 浊流书 27 海啸书
 * 28 焦热书 29 火龙书 2A 猛火书
 * 2B 浓雾书 2C 雷阵雨书 2D 豪雨书
 * 2E 援队书 2F 援部书 30 援军书
 * 31 平气书 32 活气书 33 勇气书
 * 34 伤药 35 中药 36 茶
 */
public abstract class ConsumableItem extends BaseItem implements ItemConsume {
    public EffectAction effectAction;

    public ConsumableItem(int id, String name, EffectAction effectAction) {
        super(id, name);
        this.effectAction = effectAction;
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
        effectAction.effect(player, null, targets);
    }

    @Override
    public void reduceItem(BaseUnit player) {
        player.items.remove(this);
    }
}
