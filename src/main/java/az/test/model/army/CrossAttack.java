package az.test.model.army;

import az.test.reko3ibm.Action;

public class CrossAttack extends AttackRange {

    private static class SingletonInner {
        private static final CrossAttack singletonStaticInner = new CrossAttack();
    }

    public static CrossAttack getInstance() {
        return SingletonInner.singletonStaticInner;
    }

    @Override
    public void fillAttackRangeValues(Action[][] actionValuesArray, BaseUnit target, int attackActionValue) {
        /*
         * O T O<br>
         * T @ T<br>
         * O T O<br>
         */
        fillTargetAndActionValue(actionValuesArray, target.y - 1, target.x, attackActionValue, target);
        fillTargetAndActionValue(actionValuesArray, target.y, target.x + 1, attackActionValue, target);
        fillTargetAndActionValue(actionValuesArray, target.y + 1, target.x, attackActionValue, target);
        fillTargetAndActionValue(actionValuesArray, target.y, target.x - 1, attackActionValue, target);
    }
}
