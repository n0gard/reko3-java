package az.test.battle;

import az.test.battle.enums.Weather;
import az.test.map.BattleMap;
import az.test.model.army.BaseUnit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Data
public class BattleInfoSnapshot {
    public List<Weather> weatherList;
    public int lastRoundWeatherCode = 2;
//    public static final int WEATHER_RAND = (int) (Math.random() * 6.0);
    public static final int WEATHER_RAND = 5;
    public List<BaseUnit> playerUnits = new ArrayList<BaseUnit>();
    public List<BaseUnit> outOfBattlePlayerUnits = new ArrayList<BaseUnit>();
    public static final int MAX_PLAYER_UNITS = 20;
    public List<BaseUnit> friendUnits = new ArrayList<BaseUnit>();
    public List<BaseUnit> outOfBattleFriendUnits = new ArrayList<BaseUnit>();
    public List<BaseUnit> enemyUnits = new ArrayList<BaseUnit>();
    public List<BaseUnit> outOfBattleEnemyUnits = new ArrayList<BaseUnit>();
    public BattleMap map;
    private long timestamp = 0L;


    public BattleInfoSnapshot() {
        super();
        setTimestamp(System.nanoTime());
    }




    public BaseUnit queryEnemyUnitByCoordinate(int y, int x) {
        for (BaseUnit u : enemyUnits) {
            if (u.y == y && u.x == x) {
                return u;
            }
        }
        return null;
    }

    public BaseUnit queryUnitByCoordinate(int y, int x) {
        if (y < 0 || x < 0 || y >= map.map.length || x >= map.map[0].length) {
            return null;
        }
        return map.map[y][x].army;
    }

    public static void main(String[] args) {
        // int last =
        List<Weather> weatherRand50Rounds = Weather.generateWeatherList(WEATHER_RAND, 50);
        for (int i = 0; i < weatherRand50Rounds.size(); i++) {
            System.out.println(weatherRand50Rounds.get(i));
        }
    }
}
