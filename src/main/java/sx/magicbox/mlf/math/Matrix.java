package sx.magicbox.mlf.math;

import java.io.Serializable;
import java.text.DecimalFormat;

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
                //newMatrix.data[i][j] = this.data[i][j] - m.data[i][j];
                newMatrix.set(i,j,this.data[i][j] - m.data[i][j]);
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
    public Matrix divide(double value) {
        Matrix newMatrix = new Matrix(this.row,this.column);
        for(int i = 0 ; i<row;i++){
            for(int j = 0 ; j < column ;j++){
                newMatrix.set(i,j,value/data[i][j]);
            }
        }
        return newMatrix;
    }
    public Matrix divideBy(double value){
        Matrix newMatrix = new Matrix(this.row,this.column);
        for(int i = 0 ; i<row;i++){
            for(int j = 0 ; j < column ;j++){
                newMatrix.set(i,j,data[i][j]/(value));
            }
        }
        return newMatrix;
    }

    public Matrix add(double a){

        Matrix newMatrix = new Matrix(this.row,this.column);
        for(int i = 0 ; i<row;i++){
            for(int j = 0 ; j < column ;j++){
                newMatrix.set(i,j,data[i][j]+a);
            }
        }
        return newMatrix;
    }

    public Matrix subtract(double a){
       return add(-1*a);
    }

    public Matrix subtractBy(double a){
        Matrix newMatrix = new Matrix(this.row,this.column);
        for(int i = 0 ; i<this.row;i++){
            for(int j = 0 ; j < this.column ;j++){
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

    public static Matrix addBias(Matrix result) {
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

    public static Matrix subBias(Matrix result) {
        Matrix withoutBias = new Matrix(result.getRow(), result.getColumn() - 1);

        for (int r = 0; r < result.getRow(); r++) {
            for (int c = 1; c < result.getColumn(); c++) {
                withoutBias.set(r, c - 1, result.get(r, c));
            }
        }
        return withoutBias;
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

    public static Matrix copy(Matrix m){
       Matrix newm = new Matrix(m.getRow(), m.getColumn());
       for(int i = 0; i < m.getRow();i++){
           System.arraycopy(m.data[i],0,newm.data[i],0,m.data[i].length);
       }
       return newm;
    }

    public static Matrix setColumn(Matrix m, int column, double value){
       if(column >= m.getColumn()){
           throw new RuntimeException(String.format("column %s is bigger than  %s",column, m.getColumn()));
       }
       for(int i = 0; i < m.getRow(); i++){
           m.set(i,column,value);
       }
       return m;
    }

    public static Matrix setRow(Matrix m, int row, double value){
        if(row >= m.getRow()){
            throw new RuntimeException(String.format("row %s is bigger than  %s",row, m.getRow()));
        }
        for(int i = 0; i < m.column; i++){
            m.set(row,i,value);
        }
        return m;
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

    public double[][] getData(){
       return this.data;
    }

    public String tostr(){
       StringBuffer bf = new StringBuffer();
       DecimalFormat df = new DecimalFormat("#.####");
       for(int i = 0;i<this.getRow();i++){
           for(int j = 0; j<this.getColumn();j++){
               double d = this.get(i,j);
               bf.append(df.format(d)+"    ");
           }
           bf.append("\n");
       }
       return bf.toString();
    }



}
