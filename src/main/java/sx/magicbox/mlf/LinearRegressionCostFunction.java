package sx.magicbox.mlf;

import sx.magicbox.mlf.math.Matrix;

/**
 * Created by madic on 2016/10/23.
 */
public class LinearRegressionCostFunction extends CostFunction{
   // LinearHypothesis hypothesis;

    public LinearRegressionCostFunction(LinearHypothesis hypothesis){
        super(hypothesis);
    }

    public double cost(Matrix theta, Matrix X, Matrix Y){
        int m = X.getRow();
        return (hypothesis.hypothFunc(theta,X).subtract(Y).square()).sum()*0.5*m;
    }
}
