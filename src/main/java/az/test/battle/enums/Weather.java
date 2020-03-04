package az.test.battle.enums;

public enum Weather {

	SUN, CLOUD, RAIN, IMPOSSIBLE;

	public static int generateNextWeather(int last) {
		int current = 0;
		// step 1
		int a = (int) (Math.random() * 6.0);
		int b = last;
		// step 2
		int c = 0;
		if (b < a) {
			c = b + 1;
		}
		if (b > a) {
			c = b - 1;
		}
		if (b == a) {
			c = b;
		}
		// step 3
		switch (c) {
		case 0:
			current = 5;
			break;
		case 1:
		case 2:
		case 3:
		case 4:
			current = c;
			break;
		case 5:
			current = 0;
			break;
		}
		return current;
	}

	public static Weather parseInt2Weather(int current) {
		switch (current) {
		case 0:
		case 1:
		case 2:
			return SUN;
		case 3:
			return CLOUD;
		case 4:
		case 5:
			return RAIN;
		default:
			return IMPOSSIBLE;
		}
	}
}
