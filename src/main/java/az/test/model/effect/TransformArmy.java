package az.test.model.effect;

import az.test.exception.LiuBeiCannotTransformException;
import az.test.model.army.BaseUnit;

import java.util.function.Function;

public class TransformArmy extends Effection {

    public TransformArmy() {
    }

    @Override
    public void effect(BaseUnit player, Function<Integer, Integer> extra ,BaseUnit... targets) throws LiuBeiCannotTransformException {
        if ("LiuBei".equalsIgnoreCase(player.name)) {
            throw new LiuBeiCannotTransformException();
        }
    }
}
