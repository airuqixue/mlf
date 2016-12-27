import org.junit.Assert;
import org.junit.Test;
import sx.magicbox.mlf.math.Matrix;
import sx.magicbox.mlf.math.Scaling;

/**
 * Created by madic on 16-12-27.
 */
public class MatrixTest {

    private static double[][] X = {
    {1,  25,500},
    {1.8,44,624},
    {1.1,38,445},
    {1.3,39,425},
    {2.5,66,754},
    {2.3,77,800},
    {1.6,56,765}
    };
    private static double[][] Y={
            {1,2,3},
            {1,1,1}
    };
    @Test
    public void scaleTest(){
        Matrix matrix = new Matrix(X);
        Assert.assertEquals(1d,matrix.get(0,0),0.00001d);
        Assert.assertEquals(2.3d,matrix.get(5,0),0.00001d);
        Assert.assertEquals(7,matrix.getRow());
        Assert.assertEquals(3,matrix.getColumn());
        Matrix newm = Scaling.scale(matrix,Scaling.MMM);
        System.out.print(newm);
        System.out.println();
        Matrix stdm = Scaling.scale(matrix,Scaling.STD);
        System.out.print(stdm);
    }

    @Test
    public void stdTest(){
        Matrix matrix = new Matrix(Y);
        Matrix stdm = Scaling.scale(matrix,Scaling.STD);
        System.out.print(stdm);
    }
}
