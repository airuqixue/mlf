package sx.magicbox.mlf.math;

import java.util.Arrays;
import java.util.List;

/**
 * Created by madic on 2016/12/26.
 */
public class Scaling {
    public static final int MMM = 1;
    public static final int STD = 2;

    public static Matrix scale(Matrix matrix,int method){

            Matrix newMatrix = new Matrix(matrix.getRow(),matrix.getColumn());
            for(int j = 0; j<matrix.getColumn();j++){
                Double[] columnData = new Double[matrix.getRow()];
                for(int i = 0;i<matrix.getRow();i++){
                    columnData[i] = matrix.get(i,j);
                }
                double[] columnj = null ;
                if(method==MMM) {
                     columnj = mmm(columnData);
                }else if(method==STD){
                    columnj = std(columnData);
                }else{
                    throw new RuntimeException("Unsupported algorithm");
                }
                for(int i = 0;i<matrix.getRow();i++){
                    newMatrix.set(i,j,columnj[i]);
                }
            }
            return newMatrix;
    }


    private static double[] mmm(Double[] data){
        List<Double> dataList = Arrays.asList(data);
        Double[] sortedData = dataList.stream().sorted().toArray(Double[]::new);
        double min = sortedData[0];
        double max= sortedData[sortedData.length-1];

        Double all = dataList.stream().filter(d->d!=0).reduce(0d,(sum,item)->sum+item);
        double mean = (double)(all/dataList.size());

        double diff = max - min;
        double newdata[] = new double[data.length];
        for(int i = 0;i<data.length;i++){
            newdata[i] = ((data[i]-mean)/diff);
        }
        return newdata;
    }

    private static double[] std(Double[] data){
        List<Double> dataList = Arrays.asList(data);
        Double[] sortedData = (Double[])dataList.stream().sorted().toArray(size->new Double[size]);
        double min = sortedData[0];
        double max= sortedData[sortedData.length-1];

        Double all = dataList.stream().filter(d->d!=0).reduce(0d,(sum,item)->sum+item);
        double mean = (double)(all/dataList.size());
        double sum = 0d;
        for(int i = 0 ; i < data.length;i++){
            sum += (data[i] - mean)*(data[i] - mean);
        }

        double std = Math.sqrt(sum/(data.length));
        double newdata[] = new double[data.length];
        for(int i = 0 ; i < data.length;i++){
            newdata[i] = std==0?0:((data[i]-mean)/std);
        }

        return newdata;
    }
}