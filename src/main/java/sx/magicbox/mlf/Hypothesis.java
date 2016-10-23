package sx.magicbox.mlf;

import sx.magicbox.mlf.math.Matrix;

/**
 * Created by yinsx on 2016/09/06.
 */
public interface Hypothesis {
    Matrix hypothFunc(Matrix theta, Matrix features);
}
