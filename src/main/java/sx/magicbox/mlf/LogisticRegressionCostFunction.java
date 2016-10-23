package sx.magicbox.mlf;

import sx.magicbox.mlf.math.Matrix;

/**
 * Created by madic on 2016/10/23.
 */
public class LogisticRegressionCostFunction extends CostFunction {

    public LogisticRegressionCostFunction(Hypothesis hypothesis) {
        super(hypothesis);
    }

    @Override
    public double cost(Matrix theta, Matrix X, Matrix Y) {
        long m = X.getRow();
        return Y.multiplyEach(hypothesis.hypothFunc(theta, X).add(Y.subtractBy(1.0d).multiplyEach(
                hypothesis.hypothFunc(theta, X).subtractBy(1.0d)
        ))).sum()*0.5*m*(-1.0d);
    }
}
