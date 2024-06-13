package az.test.battle;

import az.test.battle.enums.Weather;
import az.test.map.BattleMap;
import az.test.model.army.BaseUnit;
import az.test.model.army.BotUnit;
import az.test.util.ObjectCopyUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BattleInfoSnapshot {
    private List<Weather> weatherList;
    private int lastRoundWeatherCode = 2;
    private List<Integer> weatherRands = new ArrayList<>();
    private List<BaseUnit> playerUnits = new ArrayList<>();
    private List<BaseUnit> outOfBattlePlayerUnits = new ArrayList<>();
    public static final int MAX_PLAYER_UNITS = 20;
    private List<BotUnit> friendUnits = new ArrayList<>();
    private List<BotUnit> outOfBattleFriendUnits = new ArrayList<>();
    private List<BotUnit> enemyUnits = new ArrayList<>();
    private List<BotUnit> outOfBattleEnemyUnits = new ArrayList<>();
    private BattleMap map;
    private long timestamp = 0L;

    public BattleInfoSnapshot(BattleInfo battleInfo) {
        super();
        setTimestamp(battleInfo.getTimestamp());
        setMap((BattleMap)ObjectCopyUtil.deepCopy(battleInfo.getMap()));
        setWeatherList(ObjectCopyUtil.deepCopy(battleInfo.weatherList));
        setLastRoundWeatherCode(battleInfo.getLastRoundWeatherCode());
        setWeatherRands(ObjectCopyUtil.deepCopy(battleInfo.getWeatherRands()));
        setPlayerUnits(ObjectCopyUtil.deepCopy(battleInfo.getPlayerUnits()));
        setOutOfBattlePlayerUnits(ObjectCopyUtil.deepCopy(battleInfo.getOutOfBattlePlayerUnits()));
        setFriendUnits(ObjectCopyUtil.deepCopy(battleInfo.getFriendUnits()));
        setOutOfBattleFriendUnits(ObjectCopyUtil.deepCopy(battleInfo.getOutOfBattleFriendUnits()));
        setEnemyUnits(ObjectCopyUtil.deepCopy(battleInfo.getEnemyUnits()));
        for(BotUnit ebu : getEnemyUnits()) {
            if (ebu.isLord) {
                getMap().setLord(ebu);
            }
        }
        setOutOfBattleEnemyUnits(ObjectCopyUtil.deepCopy(battleInfo.getOutOfBattleEnemyUnits()));
    }

    @Override
    public String toString() {
        return "BattleInfoSnapshot{" +
                "weatherList=" + weatherList +
                ", lastRoundWeatherCode=" + lastRoundWeatherCode +
                ", weatherRands=" + weatherRands +
                ", playerUnits=" + playerUnits +
                ", outOfBattlePlayerUnits=" + outOfBattlePlayerUnits +
                ", friendUnits=" + friendUnits +
                ", outOfBattleFriendUnits=" + outOfBattleFriendUnits +
                ", enemyUnits=" + enemyUnits +
                ", outOfBattleEnemyUnits=" + outOfBattleEnemyUnits +
                ", timestamp=" + timestamp +
                '}';
    }
}
