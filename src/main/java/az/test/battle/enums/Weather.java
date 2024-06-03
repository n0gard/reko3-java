package az.test.battle.enums;

import java.util.ArrayList;
import java.util.List;

public enum Weather {

	SUN, CLOUD, RAIN, IMPOSSIBLE;

	public static int generateNextWeather(int last, int rand) {
		int current = 0;
		// step 1
		int a = rand;
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

	public static List<Weather> generateWeatherList(int rand, int maxRound) {
		List<Weather> weatherList = new ArrayList<>();
		int last = 2;
		for(int i = 0; i < maxRound; i++) {
			int weather = generateNextWeather(last, rand);
			weatherList.add(parseInt2Weather(weather));
			last = weather;
		}
		return weatherList;
	}
}
