package sx.magicbox.mlf;

import sx.magicbox.mlf.math.Matrix;

/**
 * Created by madic on 2016/10/23.
 * �߼��ع�ɱ������������ݶȵݼ�(GradientDescent�㷨)
 */
public class LogisticRegressionCostFunction extends CostFunction {

    public LogisticRegressionCostFunction(Hypothesis hypothesis) {
        super(hypothesis);
    }

    /**
     * �ɱ���������ʧ������ע��˺���ʵ�ֵ��Ѿ��ǶԲ�����ƫ�����Ľ��
     * @param theta �������Ĳ���
     * @param X  ��������
     * @param Y  ��ʵֵ����
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
