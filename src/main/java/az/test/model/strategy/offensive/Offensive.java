package az.test.model.strategy.offensive;

import az.test.model.army.BaseUnit;
import az.test.model.army.other.MilitaryBand;
import az.test.model.army.other.TransportTeam;
import az.test.model.army.other.Wizard;
import az.test.model.strategy.Tactic;
import az.test.util.RandomHelper;

public abstract class Offensive implements Tactic {
    public boolean isTacticSuccess(BaseUnit army, BaseUnit target) {
        int evasionRate = (target.intelligence * target.level / 100 + target.intelligence) / 4;
        if (target instanceof MilitaryBand || target instanceof TransportTeam || target instanceof Wizard) {
            evasionRate *= 2;
        }
        int hitRate = RandomHelper.generateInt(0, (army.intelligence * army.level / 100 + army.intelligence) - 1);
        return hitRate > evasionRate;
    }
}
