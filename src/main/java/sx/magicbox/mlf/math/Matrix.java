package sx.magicbox.mlf.math;

import java.io.Serializable;

/**
 * Created by yinsx on 2016/09/06.
 */
public class Matrix implements Serializable {
    private final int row;
    private final int column;

    private final double data[][];

   public Matrix(int row, int column){
        if(row<1||column <1){
            throw new RuntimeException("the size of matrix can't be less than 1");
        }
        this.row = row;
        this.column= column;
        data = new double[row][column];
        for(int i = 0; i<row;i++){
            for(int j = 0; j<column;j++){
                data[i][j] = 0;
            }
        }
    }

    public Matrix(double data[][]){
        this(data.length,data[0].length);
        for(int i = 0; i<row;i++){
            for(int j = 0; j<column;j++){
                this.data[i][j] = data[i][j];
            }
        }
    }

    public Matrix add(Matrix m){
        if(m==null){
            throw new RuntimeException("matrix can't be null");
        }
        if((m.row!=this.row)||(m.column!=this.column)){
            throw new RuntimeException(String.format("matrix size %sx%s is not fit for size %sx%s",this.row,this.column
            ,m.row,m.column));
        }
        Matrix newMatrix = new Matrix(row,column);
        for(int i=0;i<row;i++){
            for(int j = 0; j<column;j++){
                newMatrix.data[i][j] = this.data[i][j] + m.data[i][j];
            }
        }
        return newMatrix;
    }

    public Matrix subtract(Matrix m){
        if(m==null){
            throw new RuntimeException("matrix can't be null");
        }
        if((m.row!=this.row)||(m.column!=this.column)){
            throw new RuntimeException(String.format("matrix size %sx%s is not fit for size %sx%s",this.row,this.column
                    ,m.row,m.column));
        }
        Matrix newMatrix = new Matrix(row,column);
        for(int i=0;i<row;i++){
            for(int j = 0; j<column;j++){
                newMatrix.data[i][j] = this.data[i][j] - m.data[i][j];
            }
        }
        return newMatrix;
    }

    public Matrix multiply(Matrix m){
        if(m==null){
            throw new RuntimeException("matrix can't be null");
        }
        if(this.column != m.row){
            throw new RuntimeException(String.format("matrix size %sx%s is not fit for size %sx%s",this.row,this.column
                    ,m.row,m.column));
        }
        Matrix newMatrix = new Matrix(this.row,m.column);
        for(int i = 0; i < this.row; i++){
            for(int j = 0; j < m.column; j++){
                newMatrix.data[i][j] = rowMulColumn(i,j, m);
            }
        }
        return newMatrix;
    }

    private double rowMulColumn(int row,int column,Matrix m) {
        double result = 0;
        for(int i = 0; i<this.data[row].length;i++){
            result += this.data[row][i]*m.data[i][column];
        }
        return result;
    }

    public Matrix divide(Matrix m){
        return new Matrix(2,2);
    }

    public Matrix divideBy(double value){
        Matrix newMatrix = new Matrix(this.row,this.column);
        for(int i = 0 ; i<row;i++){
            for(int j = 0 ; j < column ;j++){
                newMatrix.set(i,j,value/data[i][j]);
            }
        }
        return newMatrix;
    }

    public Matrix add(double a){
        return new Matrix(2,2);
    }

    public Matrix subtract(double a){
        return new Matrix(2,2);
    }

    public Matrix subtractBy(double a){
        Matrix newMatrix = new Matrix(this.row,this.column);
        for(int i = 0 ; i<row;i++){
            for(int j = 0 ; j < column ;j++){
                newMatrix.set(i,j,a-data[i][j]);
            }
        }
        return newMatrix;
    }
    public Matrix multiply(double a){
        Matrix newMatrix = new Matrix(this.row,this.column);
        for(int i = 0 ; i < row; i++){
            for(int j = 0; j< column;j++){
                newMatrix.data[i][j] = this.data[i][j]*a;
            }
        }
        return newMatrix;
    }
    public Matrix multiplyEach(Matrix m){
        Matrix newMatrix = new Matrix(this.row,this.column);
        for(int i = 0 ; i < row; i++){
            for(int j = 0; j< column;j++){
                newMatrix.data[i][j] = this.data[i][j]*m.data[i][j];
            }
        }
        return newMatrix;
    }

    public Matrix transpose(){
        Matrix matrix  = new Matrix(column,row);
        for(int i = 0 ;i < this.row;i++){
            for(int j = 0; j<this.column ;j++){
                matrix.data[j][i] = this.data[i][j];
            }
        }
        return matrix;
    }

    public Matrix square(){
        Matrix newMatrix = new Matrix(this.row,this.column);
        for(int i = 0 ; i < row; i++){
            for(int j = 0; j< column;j++){
                newMatrix.data[i][j] = this.data[i][j]*this.data[i][j];
            }
        }
        return newMatrix;
    }

    public double getSingleValue(){
        if(this.row==1&&this.column==1){
            return this.data[0][0];
        }
        return 0;
    }

    public int getRow(){
       return this.row;
    }

    public int getColumn(){
        return this.column;
    }


    public double sum(){
        double result = 0;
        for(int i = 0;i<row;i++){
            for(int j = 0;j<column;j++){
                result += this.data[i][j];
            }
        }
        return result;
    }


    public double get(int i,int j){
        if((i<row && j<column)
                && (i>=0&& j>=0)){
            return this.data[i][j];
        }else{
            throw new RuntimeException(String.format("index (%s,%s) out of boundary ",i,j));
        }
    }

    public double get(int i){
        if((i<row ) && (i>0)){
            return this.data[i][0];
        }else{
            throw new RuntimeException(String.format("index (%s,%s) out of boundary ",i,0));
        }
    }

    public void set(int row, int column,double value){
        if(row<this.row && column < this.column && row >=0 && column >=0){
            this.data[row][column] = value;
        }
    }

    public static Matrix exponentOfE(Matrix exponent){
        Matrix result = new Matrix(exponent.getRow(),exponent.getColumn());
        for(int i = 0 ; i < exponent.getRow(); i++){
            for(int j = 0; j< exponent.getColumn();j++){
                result.data[i][j] = Math.pow(Math.E, exponent.get(i,j));
            }
        }
        return result;
    }

    public Matrix log(){
        Matrix result = new Matrix(getRow(),getColumn());
        for(int i = 0 ; i < getRow(); i++){
            for(int j = 0; j< getColumn();j++){
                result.data[i][j] = Math.log(get(i,j));
            }
        }
        return result;
    }

    @Override
    public String toString(){
        StringBuffer buffer = new StringBuffer();
        for(int i = 0;i<this.row;i++){
            for(int j = 0; j<this.column;j++){
                buffer.append(this.data[i][j]+"    ");
            }
            buffer.append("\n");
        }
        return buffer.toString();
    }




}
