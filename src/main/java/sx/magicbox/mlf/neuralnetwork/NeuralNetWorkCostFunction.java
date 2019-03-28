package sx.magicbox.mlf.neuralnetwork;

import sx.magicbox.mlf.LogisticHypothesis;
import sx.magicbox.mlf.LogisticRegressionCostFunction;
import sx.magicbox.mlf.math.Matrix;

public class NeuralNetWorkCostFunction {

    NeuralNetWorkHypothesis hypothesis ;
    double lambdas[] ;
    LogisticHypothesis logisticHypothesis = new LogisticHypothesis();
    LogisticRegressionCostFunction logisticCost = new LogisticRegressionCostFunction(logisticHypothesis);

    public NeuralNetWorkCostFunction(NeuralNetWorkHypothesis hypothesis){
        this.hypothesis = hypothesis;
    }

    public double[] getLambdas() {
        return lambdas;
    }

    public void setLambdas(double lambdas[]) {
        this.lambdas = lambdas;
    }

    public double cost(Matrix[] theta, Matrix X[], Matrix Y){
        int m = X[0].getRow();
        double sum = 0.0;
      //  for(int i = 0; i < Y.getColumn();i++){
            //Y from 1-n
//            Matrix Yi = new Matrix(Y.getRow(),1);
//            for(int j=0;j<Y.getRow();j++){
//                Yi.set(j,0,Y.get(j,i));
//            }
            Matrix Yi = Y;
            Matrix temp = hypothesis.hypothFunc(theta,X);
            Matrix templog = temp.log();
            Matrix minusTemplog = temp.subtractBy(1.0d).log();
            sum =Yi.multiply(-1.0d).multiplyEach(templog).subtract(
                    (Yi.subtractBy(1.0d)).multiplyEach(minusTemplog)
            ).sum();
       // }
        //regularation sum
        double rsum =0.0;
        for(int  i = 0; i < theta.length-1; i++){
          //  Matrix ti = Matrix.copy(theta[i]);
           // Matrix.setColumn(ti,0,0);
            Matrix ti = theta[i];
            rsum += ti.square().sum() * (lambdas[i]/(double)(2*m));
        }

        sum = (sum/(double)m);
        sum = sum + rsum;
        System.out.println("sum="+sum+",rsum="+ rsum);
        return sum;
    }
}
