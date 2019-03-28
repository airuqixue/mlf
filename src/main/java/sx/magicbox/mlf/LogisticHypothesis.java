package sx.magicbox.mlf;

import sx.magicbox.mlf.math.Matrix;

/**
 * Created by madic on 2016/10/23.
 * 逻辑回归模型，使用了 1/(1+e^(-g)) s函数作为模型
 */
public class LogisticHypothesis implements Hypothesis {

    LinearHypothesis linearHypothesis = new LinearHypothesis();

    // -Z 函数依然是线性回归的 -(theta)^T X
    public Matrix hypothFunc(Matrix theta, Matrix features) {
        return g(linearHypothesis.hypothFunc(theta,features).multiply(-1));
    }

    /**
     * 1/(1+e^(-g))
     * @param linMatrix
     * @return
     */
    private Matrix g(Matrix linMatrix){
        return Matrix.exponentOfE(linMatrix).add(1.0d).divide(1.0d);
    }
}
