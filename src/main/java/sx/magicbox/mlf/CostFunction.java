package sx.magicbox.mlf;

import sx.magicbox.mlf.math.Matrix;

/**
 * Created by yinsx on 2016/09/06.
 */
public abstract class CostFunction {
    protected Hypothesis hypothesis;

    public CostFunction(Hypothesis hypothesis){
        this.hypothesis = hypothesis;
    }

    public abstract double cost(Matrix theta, Matrix X, Matrix Y);

}
