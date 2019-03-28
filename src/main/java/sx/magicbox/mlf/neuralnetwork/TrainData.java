package sx.magicbox.mlf.neuralnetwork;

import sx.magicbox.mlf.math.Matrix;

public class TrainData {
    Matrix[] X;
    Matrix Y;

    public Matrix[] getX() {
        return X;
    }

    public void setX(Matrix[] x) {
        X = x;
    }

    public Matrix getY() {
        return Y;
    }

    public void setY(Matrix y) {
        Y = y;
    }
}
