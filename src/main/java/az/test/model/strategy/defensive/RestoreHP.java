package az.test.model.strategy.defensive;

import az.test.exception.HPFullException;
import az.test.model.army.BaseUnit;

public interface RestoreHP {
    public void restoreHP(BaseUnit player, BaseUnit... targets) throws HPFullException;
}
