package sx.magicbox.mlf;

import sx.magicbox.mlf.math.Matrix;

/**
 * Created by yinsx on 2016/09/06.
 */
public interface Hypothesis {
    /**
     * 预测模型函数
     * @param theta 参数向量
     * @param features 特征向量
     * @return 返回预测结果
     */
    Matrix hypothFunc(Matrix theta, Matrix features);
}
