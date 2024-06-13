package az.test.model.army;

import az.test.reko3ibm.Action;

public class CornerAttack extends AttackRange {
    private static class SingletonInner {
        private static final CornerAttack singletonStaticInner = new CornerAttack();
    }

    public static CornerAttack getInstance() {
        return SingletonInner.singletonStaticInner;
    }

    @Override
    public void fillAttackRangeValues(Action[][] actionValuesArray, BaseUnit target, int attackActionValue) {
        /*
         * T O T<br>
         * O @ O<br>
         * T O T<br>
         */
        fillTargetAndActionValue(actionValuesArray, target.y - 1, target.x + 1, attackActionValue, target);
        fillTargetAndActionValue(actionValuesArray, target.y + 1, target.x + 1, attackActionValue, target);
        fillTargetAndActionValue(actionValuesArray, target.y + 1, target.x - 1, attackActionValue, target);
        fillTargetAndActionValue(actionValuesArray, target.y - 1, target.x - 1, attackActionValue, target);
    }
}
