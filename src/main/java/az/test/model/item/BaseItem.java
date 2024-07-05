package az.test.model.item;

import az.test.model.effect.EffectAction;

/**
 * 00 遁甲天书&nbsp;&nbsp;&nbsp;01 青囊书&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;02 鼓吹具<br>
 * 03 孙子兵法&nbsp;&nbsp;&nbsp;04 孟德新书&nbsp;&nbsp;&nbsp;05 黄爪飞电<br>
 * 06 的卢马&nbsp;&nbsp;&nbsp;07 赤兔马&nbsp;&nbsp;&nbsp;08 玉玺<br>
 * 09 倚天剑&nbsp;&nbsp;&#9;0A 青虹剑&nbsp;&nbsp;0B 七星剑<br>
 * 0C 青龙偃月刀 0D 三尖刀 0E 方天画戟<br>
 * 0F 蛇矛 10 弓术指南书 11 马术指南书<br>
 * 12 剑术指南书 13 长枪 14 步兵车<br>
 * 15 连弩 16 发石车 17 马铠<br>
 * 18 近卫铠 19 无赖精神 1A 侠义精神<br>
 * 1B 酒 1C 特级酒 1D 老酒<br>
 * 1E 豆 1F 麦 20 米<br>
 * 21 炸弹 22 落石书 23 山崩书<br>
 * 24 山洪书 25 旋涡书 26 浊流书<br>
 * 27 海啸书 28 焦热书 29 火龙书<br>
 * 2A 猛火书 2B 浓雾书 2C 雷阵雨书<br>
 * 2D 豪雨书 2E 援队书 2F 援部书<br>
 * 30 援军书 31 平气书 32 活气书<br>
 * 33 勇气书 34 伤药 35 中药<br>
 * 36 茶 37 赦命书 38 援军报告<br>
 * 39 雌雄双剑 3A 英雄之剑 3B 霸王之剑<br>
 * 3C 六韬 3D 三略 3E 吴子兵法<br>
 * FF 金<br>
 */
public abstract class BaseItem {
    public int id;
    public String name;

    public BaseItem(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
