package az.test.model.effect;

import az.test.exception.BaseException;
import az.test.model.army.BaseUnit;

public abstract class EffectAction {
    public abstract void effect(BaseUnit player, BaseUnit ...targets) throws BaseException;
}
