package az.test.model.item;

import az.test.model.item.consumption.Bean;
import az.test.model.item.consumption.transform.Dunjiatianshu;
import az.test.model.item.consumption.transform.Guchuiju;
import az.test.model.item.consumption.transform.Qingnangshu;

public class ItemGenerator {

    /**
     * 00 遁甲天书 01 青囊书 02 鼓吹具
     * 03 孙子兵法 04 孟德新书 05 黄爪飞电
     * 06 的卢马 07 赤兔马 08 玉玺
     * 09 倚天剑 0A 青虹剑 0B 七星剑
     * 0C 青龙偃月刀 0D 三尖刀 0E 方天画戟
     * 0F 蛇矛 10 弓术指南书 11 马术指南书
     * 12 剑术指南书 13 长枪 14 步兵车
     * 15 连弩 16 发石车 17 马铠
     * 18 近卫铠 19 无赖精神 1A 侠义精神
     * 1B 酒 1C 特级酒 1D 老酒
     * 1E 豆 1F 麦 20 米
     * 21 炸弹 22 落石书 23 山崩书
     * 24 山洪书 25 旋涡书 26 浊流书
     * 27 海啸书 28 焦热书 29 火龙书
     * 2A 猛火书 2B 浓雾书 2C 雷阵雨书
     * 2D 豪雨书 2E 援队书 2F 援部书
     * 30 援军书 31 平气书 32 活气书
     * 33 勇气书 34 伤药 35 中药
     * 36 茶 37 赦命书 38 援军报告
     * 39 雌雄双剑 3A 英雄之剑 3B 霸王之剑
     * 3C 六韬 3D 三略 3E 吴子兵法
     */
    public static BaseItem generateItemById(int id) {
        switch (id) {
            case 0x00:
                return new Dunjiatianshu();
            case 0x01:
                return new Qingnangshu();
            case 0x02:
                return new Guchuiju();
            case 0x1E:
                return new Bean();
        }
        return null;
    }

    public static Gold generateGold(int gold) {
        return new Gold(gold);
    }
}
