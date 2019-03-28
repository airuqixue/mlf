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
        return null;
    }

    private double[] mmm(Double[] data){
        List<Double> dataList = Arrays.asList(data);
        Double[] sortedData = (Double[])dataList.stream().sorted().toArray();
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

    private double[] std(Double[] data){
        List<Double> dataList = Arrays.asList(data);
        Double[] sortedData = (Double[])dataList.stream().sorted().toArray();
        double min = sortedData[0];
        double max= sortedData[sortedData.length-1];

        Double all = dataList.stream().filter(d->d!=0).reduce(0d,(sum,item)->sum+item);
        double mean = (double)(all/dataList.size());

        double diff = max - min;
        double sum = 0d;
        for(int i = 0 ; i < data.length;i++){
           sum += Math.pow((data[i] - mean),2);
        }
        double std = Math.sqrt(sum/data.length);
        double newdata[] = new double[data.length];
        for(int i = 0 ; i < data.length;i++){
            newdata[i] = ((data[i]-mean)/std);
        }
        return newdata;
    }
}
