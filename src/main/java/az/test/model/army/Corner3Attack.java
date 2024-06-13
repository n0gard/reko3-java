package az.test.model.army;

import az.test.reko3ibm.Action;

public class Corner3Attack extends AttackRange {
    private static class SingletonInner {
        private static final Corner3Attack singletonStaticInner = new Corner3Attack();
    }

    public static Corner3Attack getInstance() {
        return SingletonInner.singletonStaticInner;
    }

    @Override
    public void fillAttackRangeValues(Action[][] actionValuesArray, BaseUnit target, int attackActionValue) {
        /*
         * O O O T O O O<br>
         * O T O O O T O<br>
         * O O O O O O O<br>
         * T O O @ O O T<br>
         * O O O O O O O<br>
         * O T O O O T O<br>
         * O O O T O O O<br>
         */
        fillTargetAndActionValue(actionValuesArray, target.y - 3, target.x, attackActionValue, target);
        fillTargetAndActionValue(actionValuesArray, target.y + 2, target.x + 2, attackActionValue, target);
        fillTargetAndActionValue(actionValuesArray, target.y, target.x + 3, attackActionValue, target);
        fillTargetAndActionValue(actionValuesArray, target.y + 2, target.x - 2, attackActionValue, target);
        fillTargetAndActionValue(actionValuesArray, target.y + 3, target.x, attackActionValue, target);
        fillTargetAndActionValue(actionValuesArray, target.y - 2, target.x - 2, attackActionValue, target);
        fillTargetAndActionValue(actionValuesArray, target.y, target.x - 3, attackActionValue, target);
        fillTargetAndActionValue(actionValuesArray, target.y - 2, target.x + 2, attackActionValue, target);
    }
}
