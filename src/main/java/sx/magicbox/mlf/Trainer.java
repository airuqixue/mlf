package sx.magicbox.mlf;

import sx.magicbox.mlf.math.Matrix;

/**
 * Created by yinsx on 2016/09/06.
 */
public class Trainer {

    /**
     * features
     */
    private Matrix X;

    /**
     * labeled result set
     */
    private Matrix Y;

    /**
     * theta vector
     */
    private Matrix theta;

     double cost = 0;

    LinearHypothesis  hypothesis = new LinearHypothesis();

    CostFunction costFunction = new LinearRegressionCostFunction(hypothesis);

    GradientDescent gradientDescent = new GradientDescent(hypothesis);

    int step = 300;

    double mu = 0.1;

    public Trainer(Matrix X,Matrix Y,Matrix theta){
        this.X = X;
        this.Y = Y;
        this.theta = theta;
    }

    public Matrix train(){
        cost = costFunction.cost(theta,X,Y);
        for(int i = 0; i<step*100000;i+=step){
           theta = gradientDescent.gradient(X,theta,Y);
            double newCost = costFunction.cost(theta,X,Y);
           // System.out.println("new cost "+newCost);
            if(Math.abs(newCost-cost)<=mu){
                System.out.println(theta);
                System.out.println(i/step);
                return theta;
            }
            if(newCost > cost){
                System.out.println(i/step);
                System.out.println("can't converge, please make sure alpha is small enough");
                return theta;
            }
            cost = newCost;
        }
        System.out.println("please make sure the mu is bigger enough");
        return theta;
    }

    public Matrix  predict(Matrix X){
        return hypothesis.hypothFunc(this.theta,X);
    }

    public static void main(String[] args){
        double data[][] = {
                {1,710,},{1,676},{1,750},{1,788},
                {1,1050},{1,964},{1,1062},{1,1055},
                {1,1100},{1,1055},{1,901},{1,1148},
                {1,1244},{1,1224},{1,1323},{1,1397},
                {1,1456},{1,1567}
        };
        double ty[][] = {
                {6457.5},{4680},{6637.5},{6907},{10507.5},
                {9517.5},{10282.5},{4207.5},{15367.5},{9652.5},
                {11587.5},{10530},{15030},{17122.5},{21712.5},
                {18045},{16875},{18841.5}
        };
        Matrix X = new Matrix(data);
        Matrix Y = new Matrix(ty);
        GradientDescent gradientDescent = new GradientDescent(new LinearHypothesis());
        Matrix theta = gradientDescent.randomTheta(2,1);
        //theta.set(0,0,0);
        Trainer trainer = new Trainer(X,Y,theta);
        trainer.train();
        double preData[][] = {{1,1200}};
        Matrix predict  = trainer.predict(new Matrix(preData));
        System.out.println(predict);


    }
}
