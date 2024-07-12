package az.test.model.effect;

import az.test.exception.BaseException;
import az.test.model.army.BaseUnit;

import java.util.function.Function;

public abstract class Effection {
    public String spellName;

    public abstract void effect(BaseUnit player, Function<Integer, Integer> calculateExtra, BaseUnit... targets) throws BaseException;
}
