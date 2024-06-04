package az.test.battle;

import az.test.battle.enums.Weather;
import az.test.map.BattleMap;
import az.test.model.army.BaseUnit;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BattleInfoSnapshot {
    private List<Weather> weatherList;
    private int lastRoundWeatherCode = 2;
    //    public static final int WEATHER_RAND = (int) (Math.random() * 6.0);
    private List<Integer> weatherRands = new ArrayList<>();
    private List<BaseUnit> playerUnits = new ArrayList<>();
    private List<BaseUnit> outOfBattlePlayerUnits = new ArrayList<>();
    public static final int MAX_PLAYER_UNITS = 20;
    private List<BaseUnit> friendUnits = new ArrayList<>();
    private List<BaseUnit> outOfBattleFriendUnits = new ArrayList<>();
    private List<BaseUnit> enemyUnits = new ArrayList<>();
    private List<BaseUnit> outOfBattleEnemyUnits = new ArrayList<>();
    private BattleMap map;
    private long timestamp = 0L;

    public BattleInfoSnapshot(BattleInfo battleInfo) {
        super();
        setTimestamp(battleInfo.getTimestamp());
        setWeatherList(new ArrayList<>(battleInfo.weatherList));
        setLastRoundWeatherCode(battleInfo.getLastRoundWeatherCode());
        setWeatherRands(new ArrayList<>(battleInfo.getWeatherRands()));
        setPlayerUnits(new ArrayList<>(battleInfo.getPlayerUnits()));
        setOutOfBattlePlayerUnits(new ArrayList<>(battleInfo.getOutOfBattlePlayerUnits()));
        setFriendUnits(new ArrayList<>(battleInfo.getFriendUnits()));
        setOutOfBattleFriendUnits(new ArrayList<>(battleInfo.getOutOfBattleFriendUnits()));
        setEnemyUnits(new ArrayList<>(battleInfo.getEnemyUnits()));
        setOutOfBattleEnemyUnits(new ArrayList<>(battleInfo.getOutOfBattleEnemyUnits()));
    }

}
