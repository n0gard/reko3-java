package az.test.battle;

import az.test.model.army.BaseUnit;
import az.test.model.map.MapItem;
import lombok.Data;

import java.util.List;

@Data
public class WinningCondition {
    private List<BaseUnit> someones;
    private boolean everyoneMatched = false;
    private List<MapItem> somewheres;
    private boolean allPlacesMatched = false;
}
