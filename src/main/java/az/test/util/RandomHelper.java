package az.test.util;

public class RandomHelper {

	public static int generateInt(int rangeStart, int rangeEnd) {
		return (int) (Math.random() * (double) (rangeEnd - rangeStart)) + rangeStart;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 100; i++)
			System.out.println(RandomHelper.generateInt(1, 5000));
	}

}
