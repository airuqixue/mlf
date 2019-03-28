package sx.magicbox.mlf;

import sx.magicbox.mlf.gui.MyDraw;
import sx.magicbox.mlf.gui.StdDraw;
import sx.magicbox.mlf.math.Matrix;

import java.awt.*;

public class LogisticTrainer {
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

    LogisticHypothesis  hypothesis = new LogisticHypothesis();

    CostFunction costFunction = new LogisticRegressionCostFunction(hypothesis);

    GradientDescent gradientDescent = new GradientDescent(hypothesis);

    int step = 1;

    double mu = 0.000000000000001;

    public LogisticTrainer(Matrix X,Matrix Y,Matrix theta){
        this.X = X;
        this.Y = Y;
        this.theta = theta;
    }

    public Matrix train(){
        cost = costFunction.cost(theta,X,Y);
        int i = 0;
        for(i = 0; i<step*10000000;i+=step){
            theta = gradientDescent.gradient(X,theta,Y);
            double newCost = costFunction.cost(theta,X,Y);
            // System.out.println("new cost "+newCost);
            if(Math.abs(newCost-cost)<=mu){
                System.out.println("theta:\n"+theta);
                System.out.println("step:"+ (i/step));
                System.out.println(" cost:" + cost);
                System.out.println("ncost:" + newCost);
                return theta;
            }
            if(newCost <= mu){
                System.out.println("step:"+ (i/step));
                System.out.println("cost:" + newCost);
                return theta;
            }
            if(newCost > cost){
                System.out.println(i/step);
                System.out.println("can't converge, please make sure alpha is small enough");
                return theta;
            }
            cost = newCost;
            if(i%10000==0){
                System.out.println(i/step);
            }
        }
        System.out.println(i/step);
        System.out.println("please make sure the mu is bigger enough");
        return theta;
    }
    public Matrix  predict(Matrix X){
        return hypothesis.hypothFunc(this.theta,X);
    }
    public static Matrix randomTheta(int row, int col){
       return (new GradientDescent(new LogisticHypothesis())).randomTheta(row, col);
    }

    public Matrix getTheta() {
        return theta;
    }

    public void setTheta(Matrix theta) {
        this.theta = theta;
    }

    public static void main(String[] args){

        String data ="-0.017612 14.053064 0\n" +
                "-1.395634 4.6625410 1\n" +
                "-0.752157 6.5386200 0\n" +
                "-1.322371 7.1528530 0\n" +
                "0.4233630 11.054677 0\n" +
                "0.4067040 7.0673350 1\n" +
                "0.6673940 12.741452 0\n" +
                "-2.460150 6.8668050 1\n" +
                "0.5694110 9.5487550 0\n" +
                "-0.026632 10.427743 0\n" +
                "0.8504330 6.9203340 1\n" +
                "1.3471830 13.175500 0\n" +
                "1.1768130 3.1670200 1\n" +
                "-1.781871 9.0979530 0\n" +
                "-0.566606 5.7490030 1\n" +
                "0.9316350 1.5895050 1\n" +
                "-0.024205 6.1518230 1\n" +
                "-0.036453 2.6909880 1\n" +
                "-0.196949 0.4441650 1\n" +
                "1.0144590 5.7543990 1\n" +
                "1.9852980 3.2306190 1\n" +
                "-1.693453 -0.557540 1\n" +
                "-0.576525 11.778922 0\n" +
                "-0.346811 -1.678730 1\n" +
                "-2.124484 2.6724710 1\n" +
                "1.2179160 9.5970150 0\n" +
                "-0.733928 9.0986870 0\n" +
                "-3.642001 -1.618087 1\n" +
                "0.3159850 3.5239530 1\n" +
                "1.4166140 9.6192320 0\n" +
                "-0.386323 3.9892860 1\n" +
                "0.5569210 8.2949840 1\n" +
                "1.2248630 11.587360 0\n" +
                "-1.347803 -2.406051 1\n" +
                "1.1966040 4.9518510 1\n" +
                "0.2752210 9.5436470 0\n" +
                "0.4705750 9.3324880 0\n" +
                "-1.889567 9.5426620 0\n" +
                "-1.527893 12.150579 0\n" +
                "-1.185247 11.309318 0\n" +
                "-0.445678 3.2973030 1\n" +
                "1.0422220 6.1051550 1\n" +
                "-0.618787 10.320986 0\n" +
                "1.1520830 0.5484670 1\n" +
                "0.8285340 2.6760450 1\n" +
                "-1.237728 10.549033 0\n" +
                "-0.683565 -2.166125 1\n" +
                "0.2294560 5.9219380 1\n" +
                "-0.959885 11.555336 0\n" +
                "0.4929110 10.993324 0\n" +
                "0.1849920 8.7214880 0\n" +
                "-0.355715 10.325976 0\n" +
                "-0.397822 8.0583970 0\n" +
                "0.8248390 13.730343 0\n" +
                "1.5072780 5.0278660 1\n" +
                "0.0996710 6.8358390 1\n" +
                "-0.344008 10.717485 0\n" +
                "1.7859280 7.7186450 1\n" +
                "-0.918801 11.560217 0\n" +
                "-0.364009 4.7473000 1\n" +
                "-0.841722 4.1190830 1\n" +
                "0.4904260 1.9605390 1\n" +
                "-0.007194 9.0757920 0\n" +
                "0.3561070 12.447863 0\n" +
                "0.3425780 12.281162 0\n" +
                "-0.810823 -1.466018 1\n" +
                "2.5307770 6.4768010 1\n" +
                "1.2966830 11.607559 0\n" +
                "0.4754870 12.040035 0\n" +
                "-0.783277 11.009725 0\n" +
                "0.0747980 11.023650 0\n" +
                "-1.337472 0.4683390 1\n" +
                "-0.102781 13.763651 0\n" +
                "-0.147324 2.8748460 1\n" +
                "0.5183890 9.8870350 0\n" +
                "1.0153990 7.5718820 0\n" +
                "-1.658086 -0.027255 1\n" +
                "1.3199440 2.1712280 1\n" +
                "2.0562160 5.0199810 1\n" +
                "-0.851633 4.3756910 1\n" +
                "-1.510047 6.0619920 0\n" +
                "-1.076637 -3.181888 1\n" +
                "1.8210960 10.283990 0\n" +
                "3.0101500 8.4017660 1\n" +
                "-1.099458 1.6882740 1\n" +
                "-0.834872 -1.733869 1\n" +
                "-0.846637 3.8490750 1\n" +
                "1.4001020 12.628781 0\n" +
                "1.7528420 5.4681660 1\n" +
                "0.0785570 0.0597360 1\n" +
                "0.0893920 -0.715300 1\n" +
                "1.8256620 12.693808 0\n" +
                "0.1974450 9.7446380 0\n" +
                "0.1261170 0.9223110 1\n" +
                "-0.679797 1.2205300 1\n" +
                "0.6779830 2.5566660 1\n" +
                "0.7613490 10.693862 0\n" +
                "-2.168791 0.1436320 1\n" +
                "1.3886100 9.3419970 0\n" +
                "0.3170290 14.739025 0\n";

        String datas[] = data.split("\n");
        double ldata[][] = new double[datas.length][3];
        double lr[][] = new double[datas.length][1];
       // StdDraw.setPenRadius(0.001);
        MyDraw myDraw = new MyDraw();
        myDraw.setScale(1);
        int scale = 35;
        for(int i = 0; i < datas.length;i++){
            String xs[] = datas[i].split(" ");

            ldata[i][0] = 1.0d;
            ldata[i][1] = Double.parseDouble(xs[0].trim());
            ldata[i][2] = Double.parseDouble(xs[1].trim());

            lr[i][0] = Double.parseDouble(xs[2].trim());
            if(lr[i][0] == 0.0){
                myDraw.setPenColor(Color.RED);
             //   StdDraw.setPenColor(Color.RED);
            }else{
             //   StdDraw.setPenColor(Color.GREEN);
                myDraw.setPenColor(Color.GREEN);
            }
            int x = (int)(ldata[i][1]*scale);
            int y = (int)(ldata[i][2]*scale);
            myDraw.drawCirle(x,y,(int)(0.2*scale));
            myDraw.drawString(x,y,i+"");

        }

        Matrix lX = new Matrix(ldata);
        Matrix lY = new Matrix(lr);
        Matrix ltheta = LogisticTrainer.randomTheta(3,1);
 //       LogisticTrainer lt = new LogisticTrainer(lX, lY,ltheta);
 //       lt.train();

//        double[][] yx = {{0.470575d, 9.332488d},{1.196604d,4.951851d}};
//        Matrix pm = lt.predict(lX);
//       System.out.println("predict:\n" + pm.tostr());
//       double right = 0;
//       double wrong = 0;
//       for(int i = 0; i < pm.getRow();i++){
//           for(int j = 0; j < pm.getColumn();j++){
//               if(pm.get(i,j) >= 0.5d){
//                   if(lY.get(i,j)==1){
//                       right++;
//                   }else{
//                       wrong++;
//                   }
//               }else{
//                   if(lY.get(i,j)==1){
//                       wrong++;
//                   }else{
//                       right++;
//                   }
//               }
//           }
//       }
//       System.out.println("correct rate:" + right/(right+wrong));
//
//        Matrix t = lt.getTheta();


        double a = 14.75189364955011;
        double b = 1.2535657682675894;
        double c = -2.002638794709033;

        double xc = 0;
        double xb = (-a-c*xc)/b;

        //1 xc = 1;
        xb = (-a-c)/b;
        int x1 = (int)(xb*scale);
        int y1 = 1*scale;

        //2 xc = 10;
        xb = (-a-c*15)/b;
        int x2 = (int)(xb*scale);
        int y2 = 15*scale;

        myDraw.setPenColor(Color.BLACK);
        myDraw.drawLine(x1,y1,x2,y2);




    }

}
