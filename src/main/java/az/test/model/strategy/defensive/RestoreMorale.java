package az.test.model.strategy.defensive;

import az.test.exception.HPFullException;
import az.test.exception.MoraleFullException;
import az.test.exception.NotInChaosException;
import az.test.model.army.BaseUnit;

public interface RestoreMorale {
    public void restoreMorale(BaseUnit player, BaseUnit... targets) throws MoraleFullException, NotInChaosException;
}
