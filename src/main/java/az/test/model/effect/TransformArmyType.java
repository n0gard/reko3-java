package az.test.model.effect;

import az.test.exception.HPFullException;
import az.test.exception.LiuBeiCannotTransformException;
import az.test.model.army.BaseUnit;
import az.test.model.army.other.Wizard;
import az.test.model.item.Item;
import az.test.util.RandomHelper;

public class TransformArmyType extends EffectAction {

    public TransformArmyType() {
    }

    @Override
    public void effect(BaseUnit player, BaseUnit... targets) throws LiuBeiCannotTransformException {
        if ("LiuBei".equalsIgnoreCase(player.name)) {
            throw new LiuBeiCannotTransformException();
        }
    }
}
