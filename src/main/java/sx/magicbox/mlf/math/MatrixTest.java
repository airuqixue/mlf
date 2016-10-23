package sx.magicbox.mlf.math;

/**
 * Created by yinsx on 2016/09/06.
 */
public class MatrixTest {
    public static void main(String args[]){
        double data[][] = {{2,3,1},
                           {4,5,2}};
        double data2[][] = {{2,1},{1,1},{1.5,0.3}};
        Matrix m = new Matrix(data);
        Matrix m2 =  m.multiply(2);
        Matrix m3 = new Matrix(data2);
        Matrix m4 = m.multiply(m3);
        System.out.println(m);
        System.out.println( m.transpose());



    }
}
