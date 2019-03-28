package sx.magicbox.mlf;

import sx.magicbox.mlf.math.Matrix;

/**
 * Created by madic on 2016/10/23.
 * 逻辑回归成本函数，用于梯度递减(GradientDescent算法)
 */
public class LogisticRegressionCostFunction extends CostFunction {

    public LogisticRegressionCostFunction(Hypothesis hypothesis) {
        super(hypothesis);
    }

    /**
     * 成本函数即损失函数，注意此函数实现的已经是对参数求偏导数的结果
     * @param theta 向量化的参数
     * @param X  特征向量
     * @param Y  真实值向量
     * @return
     */
    @Override
    public double cost(Matrix theta, Matrix X, Matrix Y) {
        long m = X.getRow();
        return Y.multiplyEach(hypothesis.hypothFunc(theta, X).log()).add(
                Y.subtractBy(1.0d).multiplyEach(
                hypothesis.hypothFunc(theta, X).subtractBy(1.0d).log())
        ).sum()*(1.d/(double)m)*(-1.0d);
    }
}
