package sx.magicbox.mlf;

import sx.magicbox.mlf.math.Matrix;

/**
 * Created by yinsx on 2016/09/06.
 * ���Իع�
 */
public class LinearHypothesis implements Hypothesis {

    /**
     * ��Ԫ�������������Իع�ģ��
     * ����û�ж���ʽ���߽����������������Ҫ���Ե���
     * @param theta ��������
     * @param features ��������
     * @return
     */
    public Matrix hypothFunc(Matrix theta, Matrix features) {
 //       theta.transpose().multiply(features);
        return (features).multiply(theta);

    }
}
