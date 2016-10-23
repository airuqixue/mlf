package sx.magicbox.mlf;

import sx.magicbox.mlf.math.Matrix;
import org.apache.commons.lang3.RandomUtils;

/**
 * Created by yinsx on 2016/09/06.
 */
public class GradientDescent {

    private double alpha = 0.0000000003;
    Hypothesis hypothesis = new LinearHypothesis();

   public Matrix randomTheta(int row, int column) {
        double data[][] = new double[row][column];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                data[i][j] = RandomUtils.nextDouble(0.1,1);
            }
        }

        return new Matrix(data);

    }

    public Matrix gradient(Matrix X, Matrix theta,Matrix Y){
        int m = X.getRow();
        return theta.subtract(X.transpose().multiply(hypothesis.hypothFunc(theta,X).subtract(Y).multiply(alpha*(((double)1)/((double)m)))));

    }

}
