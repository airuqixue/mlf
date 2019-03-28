package sx.magicbox.mlf;

import sx.magicbox.mlf.math.Matrix;
import org.apache.commons.lang3.RandomUtils;

/**
 * Created by yinsx on 2016/09/06.
 * 梯度递减算法，运用对theta求导方式递减找到最优化解
 */
public class GradientDescent {

    private double alpha = 0.07;

    public GradientDescent(Hypothesis hypothesis){
        this.hypothesis = hypothesis;
    }
    Hypothesis hypothesis ;

   public static Matrix randomTheta(int row, int column) {
        double data[][] = new double[row][column];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                data[i][j] = -0.12 + RandomUtils.nextDouble(0,0.24);
            }
        }

        return new Matrix(data);

    }

    public Matrix gradient(Matrix X, Matrix theta,Matrix Y){
        int m = X.getRow();
        return theta.subtract(X.transpose().multiply(hypothesis.hypothFunc(theta,X).subtract(Y).multiply(alpha*(((double)1)/((double)m)))));
       // return  theta.subtract(hypothesis.hypothFunc(theta,X).subtract(Y).multiplyEach(X)).multiply(alpha*((1.0d/(double)m)));
    }

    public void setAlpha(double alpha){
       this.alpha = alpha;
    }



}
