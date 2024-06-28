package az.test.model.strategy;

import az.test.model.Consume;
import az.test.model.army.BaseUnit;

public interface ConsumeTactic extends Tactic, Consume {
    public void reduceStrategy(BaseUnit army);
}
