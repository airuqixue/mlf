package sx.magicbox.mlf;

import sx.magicbox.mlf.math.Matrix;

/**
 * Created by madic on 2016/10/23.
 * 线性回归损失(成本)函数
 */
public class LinearRegressionCostFunction extends CostFunction{
   // LinearHypothesis hypothesis;

    public LinearRegressionCostFunction(LinearHypothesis hypothesis){
        super(hypothesis);
    }

    /**
     * 成本(损失)函数即模型预测与真实数据的方差
     * @param theta 参数向量
     * @param X 特征向量
     * @param Y 真实结果向量
     * @return
     */
    public double cost(Matrix theta, Matrix X, Matrix Y){
        int m = X.getRow();
        return (hypothesis.hypothFunc(theta,X).subtract(Y).square()).sum()*0.5*m;
    }
}
