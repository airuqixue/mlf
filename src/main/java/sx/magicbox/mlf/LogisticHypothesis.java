package sx.magicbox.mlf;

import sx.magicbox.mlf.math.Matrix;

/**
 * Created by madic on 2016/10/23.
 */
public class LogisticHypothesis implements Hypothesis {
    LinearHypothesis linearHypothesis = new LinearHypothesis();
    public Matrix hypothFunc(Matrix theta, Matrix features) {
        return g(linearHypothesis.hypothFunc(theta,features));
    }

    private Matrix g(Matrix linMatrix){
        return Matrix.exponentOfE(linMatrix).add(1.0d).divideBy(1.0d);
    }
}
