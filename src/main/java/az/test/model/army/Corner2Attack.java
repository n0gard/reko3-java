package az.test.model.army;

import az.test.reko3ibm.Action;

public class Corner2Attack extends AttackRange {
    private static class SingletonInner {
        private static final Corner2Attack singletonStaticInner = new Corner2Attack();
    }

    public static Corner2Attack getInstance() {
        return SingletonInner.singletonStaticInner;
    }

    @Override
    public void fillAttackRangeValues(Action[][] actionValuesArray, BaseUnit target, int attackActionValue) {
        /*
         * O T O T O<br>
         * T O O O T<br>
         * O O @ O O<br>
         * T O O O T<br>
         * O T O T O<br>
         */
        fillTargetAndActionValue(actionValuesArray, target.y - 2, target.x + 1, attackActionValue, target);
        fillTargetAndActionValue(actionValuesArray, target.y - 1, target.x + 2, attackActionValue, target);

        fillTargetAndActionValue(actionValuesArray, target.y + 1, target.x + 2, attackActionValue, target);
        fillTargetAndActionValue(actionValuesArray, target.y + 2, target.x + 1, attackActionValue, target);

        fillTargetAndActionValue(actionValuesArray, target.y + 2, target.x - 1, attackActionValue, target);
        fillTargetAndActionValue(actionValuesArray, target.y + 1, target.x - 2, attackActionValue, target);

        fillTargetAndActionValue(actionValuesArray, target.y - 1, target.x - 2, attackActionValue, target);
        fillTargetAndActionValue(actionValuesArray, target.y - 2, target.x - 1, attackActionValue, target);

    }
}
