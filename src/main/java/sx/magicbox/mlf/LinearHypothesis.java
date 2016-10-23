package sx.magicbox.mlf;

import sx.magicbox.mlf.math.Matrix;

/**
 * Created by yinsx on 2016/09/06.
 */
public class LinearHypothesis implements Hypothesis {

    public Matrix hypothFunc(Matrix theta, Matrix features) {
        return (features).multiply(theta);

    }
}
