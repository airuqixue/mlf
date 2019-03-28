package sx.magicbox.mlf.neuralnetwork;

import org.apache.commons.lang3.RandomUtils;
import sx.magicbox.mlf.math.Matrix;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NNTrainer {

    NeuralGradientDescent descent;
    NeuralNetWorkHypothesis hypothesis;
    NeuralNetWorkCostFunction costFunction;

    Matrix theta[];
    double features[][];
    double labels[][];
    private static double mu = 0.000001;

    int batch = 1000;

    int epoch = 0;

    int units[] ;
    public NNTrainer(double featrues[][], double[][] labels, int batch, int units[]){
        this.features = featrues;
        this.labels = labels;
        this.batch = batch;
        this.units = units;
        hypothesis = new NeuralNetWorkHypothesis(units);
        costFunction = new NeuralNetWorkCostFunction(hypothesis);
        descent = new NeuralGradientDescent();
        theta = hypothesis.getInitialTheta();
    }



    private Matrix[] getX(){
        Matrix[] fx = new Matrix[theta.length];
        double[][] data_batch = new double[batch][units[0]];
        for(int i = epoch*batch,j=0; i<features.length&&j<batch;i++,j++){
            System.arraycopy(features[i],0,data_batch[j],0,units[0]);
        }
//        for(int i = 0; i < batch; i++){
//            int r = RandomUtils.nextInt(0,59999);
//            System.arraycopy(features[r], 0, data_batch[i],0,units[0]);
//        }
        fx[0] = Matrix.addBias(new Matrix(data_batch));

//        epoch++;
        return fx;
    }

    private Matrix getY(){
        double[][] data_batch = new double[batch][units[units.length-1]];
        for(int i = epoch*batch,j=0; i<labels.length&&j<batch;i++,j++){
            System.arraycopy(labels[i],0,data_batch[j],0,units[units.length-1]);
        }
        Matrix ly = new Matrix(data_batch);
        return ly;
    }

    private TrainData getData(){
        Matrix[] fx = new Matrix[theta.length];
        Map<Integer,Boolean> stat = new HashMap(batch);

        double[][] data_batch = new double[batch][units[0]];
        double[][] label_batch = new double[batch][units[units.length-1]];
        for(int i = 0; i < batch; i++){
            int r = RandomUtils.nextInt(0,59999);
            while(stat.get(r) != null){
                r = RandomUtils.nextInt(0, 59999);
            }
            stat.put(r,true);
            System.arraycopy(features[r], 0, data_batch[i],0,units[0]);
            System.arraycopy(labels[r],0,label_batch[i], 0, units[units.length-1]);
        }
        fx[0] = Matrix.addBias(new Matrix(data_batch));
        Matrix fy = new Matrix(label_batch);
        TrainData trainData = new TrainData();
        trainData.setX(fx);
        trainData.setY(fy);
//        epoch++;
        return trainData;
    }



    public Matrix[] train() {
        descent.setCostFunction(costFunction);
        descent.setCheckingGradient(new CheckingGradient(costFunction));
        TrainData tdata = getData();
        Matrix X[] = tdata.getX();
        Matrix Y = tdata.getY();
        double cost = costFunction.cost(theta, X, Y);

        for(int x = 0; x < 100; x++) {

            for (int i = 0; i < 50; i++) {

                theta = descent.gradient(X, theta, Y);
                double newCost = costFunction.cost(theta, X, Y);
                if(i==0){
                    System.out.println("start epoch lost="+ newCost);
                }
                if (Math.abs(newCost - cost) <= mu) {
                    System.out.println("return: i=" + i + " cost="+newCost);
                    return theta;
                }
//                if (newCost > cost) {
//                    System.out.println("return: i=" + i);
//                    return theta;
//                }

                cost = newCost;
            }
            System.out.println("end epoch lost="+ cost);
            int[] labels = getLabelsFromY(Y);
            this.correct(theta,X,labels);
            tdata = getData();
            X = tdata.getX();
            Y = tdata.getY();
            epoch++;

            System.out.println("*************epoch="+epoch);
            labels = getLabelsFromY(Y);
            double c = correct(theta,X,labels);
            while(c < 0.1){
                tdata = getData();
                X = tdata.getX();
                Y = tdata.getY();
                labels = getLabelsFromY(Y);
                c = correct(theta, X, labels);
            }
        }
        return theta;
    }

    public Matrix predict(Matrix[] X, Matrix[] theta) {
        return hypothesis.hypothFunc(theta, X);
    }

    public NeuralNetWorkHypothesis getHypothesis() {
        return hypothesis;
    }



    public static void main(String[] args) throws Exception {
        double datas[][] = MNISTUtils.readFeatruesFromFile("D://projects/mlf/src/main/resources/MNIST/train-images.idx3-ubyte");
        double labels[][] = MNISTUtils.readLabelFromFile("D://projects/mlf/src/main/resources/MNIST/train-labels.idx1-ubyte");
        int batch = 1024;
        double datas_batch[][] = new double[batch][784];
        double labels_batch[][] = new double[batch][10];
        double datas_prev[][] = new double[1][784];
        double label_prev[][] = new double[1][10];

        for (int i = 0; i < batch; i++) {
            System.arraycopy(datas[i], 0, datas_batch[i], 0, 784);
            System.arraycopy(labels[i], 0, labels_batch[i], 0, 10);
        }

        for(int i = 0; i < 1;i++){
            System.arraycopy(datas[i], 0,datas_prev[i],0 ,784 );
        }
        for(int i = 0 ; i < 1;i++){
            System.arraycopy(labels[i],0,label_prev[i],0,10);
        }

        //add bias

        Matrix[] X = new Matrix[4];
        X[0] = Matrix.addBias(new Matrix(datas_batch));

        Matrix Y = new Matrix(labels_batch);

//        NNTrainer nnTrainer = new NNTrainer(X, Y);
        int[] units = new int[4];
        units[0] = 784;
        units[1] = 300;
        units[2] = 36;
        units[3] = 10;
        NNTrainer nnTrainer = new NNTrainer(datas,labels,batch,units);
        Matrix[] finalTheta = nnTrainer.train();
        double[] theta = new double[785*300+301*36+37*10];
        List<Double> thetaList = new ArrayList();
        //StringBuilder  thetaStr = new StringBuilder(200000);
        for(int l = 0; l<finalTheta.length-1;l++){
            for(int i = 0;i < finalTheta[l].getRow();i++){
                for(int j = 0; j < finalTheta[l].getColumn();j++){
                    thetaList.add(finalTheta[l].get(i,j));
                }
            }
        }
        FileWriter fileWriter = new FileWriter("D://projects/mlf/src/main/resources/thetas.txt");

        for(int i = 0; i < thetaList.size();i++){
            //System.out.println(" "+thetaList.get(i));
            fileWriter.append(thetaList.get(i)+"\n");
        }
        fileWriter.flush();

        System.out.println();
        Matrix[] pX = new Matrix[3];
        pX[0] = Matrix.addBias(new Matrix(datas_prev));
        Matrix pY = nnTrainer.predict(pX,finalTheta);
        System.out.println(pY);

        System.out.println("should be:\n");
        Matrix y = new Matrix(label_prev);
        System.out.println(y);
    }

    private double correct(Matrix[] theta, Matrix[] X, int labels[]){
        Matrix result = hypothesis.hypothFunc(theta, X);
        int ret[] = new int[result.getRow()];
        for (int i = 0; i < result.getRow(); i++) {
            double max = result.get(0, 0);
            int maxIdx = 0;
            for (int j = 0; j < result.getColumn(); j++) {
                if (result.get(i, j) > max) {
                    max = result.get(i, j);
                    maxIdx = j;
                }
            }
            ret[i] = maxIdx;
        }
        double right = 0.0d;
        double wrong = 0.0d;
        for (int i = 0; i < ret.length; i++) {
            if(ret[i] == labels[i]){
                right += 1.0d;
            }else{
                wrong += 1.0d;
            }
//            System.out.print(y[i] + " ");
        }
        System.out.println("correct rate:="+ (right/(right+wrong)));
        System.out.println();
        return right/(right+wrong);
    }

    public int[] getLabelsFromY(Matrix Y){
        double[][] data = Y.getData();
        int m = data.length;
        int[] ret = new int[m];
        for(int i = 0; i < m ;i++){
            for(int j = 0; j < 10;j++){
                if(data[i][j] == 1){
                    ret[i] = j;
                    break;
                }
            }
        }
        return ret;
    }

}
