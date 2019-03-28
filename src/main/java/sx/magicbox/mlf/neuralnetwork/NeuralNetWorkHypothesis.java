package sx.magicbox.mlf.neuralnetwork;

import sx.magicbox.mlf.GradientDescent;
import sx.magicbox.mlf.LogisticHypothesis;
import sx.magicbox.mlf.math.Matrix;

public class NeuralNetWorkHypothesis {

    LogisticHypothesis logisticHypothesis = new LogisticHypothesis();
    private int[] nUnits;
    private Matrix[] theata;
    private Matrix[] featuresOfLayer;

    public NeuralNetWorkHypothesis(int layerSize, int unitsPerLayer) {
        if (layerSize < 2) {
            throw new RuntimeException("层数不能小于2");
        }
        if (unitsPerLayer < 2) {
            throw new RuntimeException("每层单元数不能小于2");
        }
        nUnits = new int[layerSize];
        for (int i = 0; i < nUnits.length; i++) {
            nUnits[i] = unitsPerLayer;
        }
        theata = new Matrix[layerSize];
        for (int i = 0; i < layerSize - 1; i++) {
            //row add one , in order to add bias
            theata[i] = GradientDescent.randomTheta(nUnits[i]+1, nUnits[i+1]);
        }
    }

    public Matrix[] getInitialTheta(){
        return theata;
    }

    public NeuralNetWorkHypothesis(int[] unitsPerLayer) {
        if (unitsPerLayer.length < 2) {
            throw new RuntimeException("层数不能小于2");
        }
        nUnits = unitsPerLayer;
        theata = new Matrix[unitsPerLayer.length];
        for (int i = 0; i < unitsPerLayer.length - 1; i++) {
            //row add one , in order to add bias
            theata[i] = GradientDescent.randomTheta(nUnits[i]+1, nUnits[i+1]);
        }
    }


    public int getLayer() {
        return nUnits.length;
    }

    public int getUnitOfLayer(int layer) {
        return nUnits[layer];
    }
    // forward propagation , theta does not change

    public Matrix hypothFunc(Matrix theta[], Matrix features[]) {
        Matrix result = null;
        for (int i = 0; i < getLayer()-1; i++) {
            result = logisticHypothesis.hypothFunc(theta[i], features[i]);
            if (i + 1 < getLayer()-1) {
                features[i + 1] = addBias(result);
            }
        }
        return result;
    }

    private Matrix addBias(Matrix result) {
        Matrix withBias = new Matrix(result.getRow(), result.getColumn() + 1);
        for (int h = 0; h < result.getRow(); h++) {
            withBias.set(h, 0, 1d);
        }
        for (int r = 0; r < result.getRow(); r++) {
            for (int c = 0; c < result.getColumn(); c++) {
                withBias.set(r, c + 1, result.get(r, c));
            }
        }
        return withBias;
    }

    public static void main(String[] args) {
        int[] unitsPerLayer = new int[2];
        unitsPerLayer[0] = 2;
        unitsPerLayer[1] = 1;
        NeuralNetWorkHypothesis neural = new NeuralNetWorkHypothesis(unitsPerLayer);
        Matrix[] theta = new Matrix[1];
        Matrix[] featrues = new Matrix[2];

        for (int i = 0; i < theta.length; i++) {
            double[][] theta1 = {{-30}, {20}, {20}};
            theta[i] = new Matrix(theta1);
        }

        for (int i = 0; i < 1; i++) {
            double[][] dt = {{1, 0, 0}, {1, 0, 1}, {1, 1, 0}, {1, 1, 1}};
            featrues[i] = new Matrix(dt);
        }

        Matrix m = neural.hypothFunc(theta, featrues);
        System.out.println(m);

        int[] upl = new int[3];
        upl[0] = 2;
        upl[1] = 2;
        upl[2] = 1;
        NeuralNetWorkHypothesis n3 = new NeuralNetWorkHypothesis(upl);
        Matrix[] t3 = new Matrix[3];
       // Matrix[] f3 = new Matrix[2];

            double[][] t33 = {{-30,10}, {20,-20}, {20,-20}};
            t3[0] = new Matrix(t33);
            double[][] t32 = {{-10},{20},{20}};
            t3[1] = new Matrix(t32);


        for (int i = 0; i < 1; i++) {
            double[][] dt = {{1, 0, 0}, {1, 0, 1}, {1, 1, 0}, {1, 1, 1}};
            featrues[i] = new Matrix(dt);
        }

        Matrix Y = new Matrix(4,1);
        Matrix m2 = n3.hypothFunc(t3, featrues);
        NeuralNetWorkCostFunction costFunction = new NeuralNetWorkCostFunction(n3);
        double c = costFunction.cost(t3,featrues,Y);
        System.out.println(m2);
        System.out.println(c);
    }
}
