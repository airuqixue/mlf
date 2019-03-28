package sx.magicbox.mlf;

import sx.magicbox.mlf.math.Matrix;

/**
 * Created by yinsx on 2016/09/06.
 * 线性回归
 */
public class LinearHypothesis implements Hypothesis {

    /**
     * 多元特征向量的线性回归模型
     * 这里没有多项式，高阶特征向量，如果需要可以单独
     * @param theta 参数向量
     * @param features 特征向量
     * @return
     */
    public Matrix hypothFunc(Matrix theta, Matrix features) {
 //       theta.transpose().multiply(features);
        return (features).multiply(theta);

    }
}
