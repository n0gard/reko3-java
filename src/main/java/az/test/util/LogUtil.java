package az.test.util;

import az.test.battle.BattleInfo;

public class LogUtil {
    public static void printlnInfo(BattleInfo bi, String info) {
        System.out.println("[battle at:" + bi.map.battleName + ",round:" + bi.map.getCurrentRoundNo() + "]" + info);
    }

    public static void printInfoWithNoReturn(String info) {
        System.out.print(info);
    }

    public static void printlnInfo(String info) {
        System.out.println(info);
    }

    public static void printlnInfo(int roundNo, String info) {
        System.out.println("[Round " + roundNo + "]" + info);
    }

    public static void printInfo(int roundNo, String info) {
        System.out.print("[Round " + roundNo + "]" + info);
    }

    public static void printlnInfo(int roundNo, String action, String info) {
        System.out.println("[Round " + roundNo + "][" + action + "]" + info);
    }

    public static void printInfoWithNoReturn(int roundNo, String action, String info) {
        System.out.print("[Round " + roundNo + "][" + action + "]" + info);
    }

    public static void printLog(int roundNo, String action, String unitName, String targetName, String msg) {
        System.out.println(
                "[Round " + roundNo + "][" + action + "]" + unitName + " -> " + targetName + " result: " + msg);
    }

    public static void main(String[] args) {
        printLog(0, "init", "liubei", "bubingdui", "slkdjflksjdlkjf");
    }
}
