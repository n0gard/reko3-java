package az.test.model.army;

import az.test.reko3ibm.Action;

public class Cross2Attack extends AttackRange {
    private static class SingletonInner {
        private static final Cross2Attack singletonStaticInner = new Cross2Attack();
    }

    public static Cross2Attack getInstance() {
        return SingletonInner.singletonStaticInner;
    }

    @Override
    public void fillAttackRangeValues(Action[][] actionValuesArray, BaseUnit target, int attackActionValue) {
        /*
         * O O T O O<br>
         * O O O 0 O<br>
         * T O @ O T<br>
         * O O O O O<br>
         * O O T O O<br>
         */
        fillTargetAndActionValue(actionValuesArray, target.y - 2, target.x, attackActionValue, target);
        fillTargetAndActionValue(actionValuesArray, target.y, target.x + 2, attackActionValue, target);
        fillTargetAndActionValue(actionValuesArray, target.y + 2, target.x, attackActionValue, target);
        fillTargetAndActionValue(actionValuesArray, target.y, target.x - 2, attackActionValue, target);
    }
}
