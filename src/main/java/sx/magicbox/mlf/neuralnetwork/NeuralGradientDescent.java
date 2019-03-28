package sx.magicbox.mlf.neuralnetwork;

import sx.magicbox.mlf.LogisticHypothesis;
import sx.magicbox.mlf.math.Matrix;

public class NeuralGradientDescent {

    LogisticHypothesis logisticHypothesis;
    NeuralNetWorkHypothesis hypothesis;
    NeuralNetWorkCostFunction costFunction;

    public NeuralNetWorkHypothesis getHypothesis() {
        return hypothesis;
    }

    public void setHypothesis(NeuralNetWorkHypothesis hypothesis) {
        this.hypothesis = hypothesis;
    }

    public NeuralNetWorkCostFunction getCostFunction() {
        return costFunction;
    }

    public void setCostFunction(NeuralNetWorkCostFunction costFunction) {
        this.costFunction = costFunction;
        this.costFunction.setLambdas(lambdas);
    }

    double alpha = 0.57;
    double lambda = 0.00001;
    double lambdas[] = {0.0017, 0.00027, 0.00012};
    double epsilon = 0.002;

    CheckingGradient checkingGradient;

    public NeuralGradientDescent() {
        logisticHypothesis = new LogisticHypothesis();

    }

    public CheckingGradient getCheckingGradient() {
        return checkingGradient;
    }

    public void setCheckingGradient(CheckingGradient checkingGradient) {
        this.checkingGradient = checkingGradient;
    }

    public Matrix[] gradient(Matrix X[], Matrix theta[], Matrix Y) {
        int m = X[0].getRow();
        int layer = theta.length;
        Matrix[] retM = new Matrix[layer];
        Matrix[] newTheta = backpropagation(X, theta, Y);

        for (int l = 0; l < layer - 1; l++) {
            retM[l] = theta[l].subtract(newTheta[l].multiply(alpha));
        }
        // alpha = alpha*0.999d;
        //  System.out.println("alpha=" + alpha);
        return retM;
    }

    //according to backpropagation
    private Matrix[] backpropagation(Matrix X[], Matrix theta[], Matrix Y) {
        int m = X[0].getRow();
        int layer = theta.length;
        Matrix[] ret = new Matrix[layer - 1];
        Matrix[] ai = X;
        Matrix Yi = Y;

        //forward propagation
        for (int l = 0; l < layer; l++) {
            Matrix a = logisticHypothesis.hypothFunc(theta[l], ai[l]);
            if (l == layer - 2) {
                ai[l + 1] = a;
                break;
            }
            ai[l + 1] = Matrix.addBias(a);

        }

        //compute error^(L)
        //a little trick, errors[0] is not used
        Matrix errors[] = new Matrix[layer];

        errors[layer - 1] = ai[layer - 1].subtract(Yi);

        for (int e = layer - 2; e > 0; e--) {
            errors[e] = errors[e + 1].multiply(theta[e].transpose()).multiplyEach((ai[e].multiplyEach(ai[e].subtractBy(1))));
            errors[e] = Matrix.subBias(errors[e]);
        }
        for (int l = 0; l < layer - 1; l++) {
            if (ret[l] == null) {
                ret[l] = ai[l].transpose().multiply(errors[l + 1]);
            } else {
                ret[l] = ret[l].add(ai[l].transpose().multiply(errors[l + 1]));
            }

        }
//        }
        //regularation
        //add all then make
        for (int i = 0; i < layer - 1; i++) {
            Matrix regTheta = Matrix.copy(theta[i]);
            Matrix.setRow(regTheta, 0, 0);
            ret[i] = ret[i].divideBy((double) m).add(regTheta.multiply(lambdas[i]));
        }
        return ret;
    }
}
