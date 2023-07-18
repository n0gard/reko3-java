package az.test.util;

public class LogUtil {

	public static void printInfo(int roundNo, String info) {
		System.out.println("[Round " + roundNo + "]" + info);
	}

	public static void printInfo(int roundNo, String action, String info) {
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
