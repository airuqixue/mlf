package sx.magicbox.mlf.neuralnetwork;

import sun.awt.image.ToolkitImage;
import sx.magicbox.mlf.math.Matrix;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class MNISTUtils {

    //一张图片就是一行，这里会把图片的二位数组形式转换为一维数组
    //例如28*28的图片，展开变成1*784的数组。
    //做了scalling
    //数据来源 http://yann.lecun.com/exdb/mnist/
    public static double[][] readFeatruesFromFile(String fileName) throws Exception{
        FileInputStream fin = new FileInputStream(fileName);
        if(fin != null){
            BufferedInputStream bufIns = new BufferedInputStream(fin);
            byte[] header = new byte[16];
            bufIns.read(header,0,header.length);

            int magic = byte2Int(header,0);
            int row = byte2Int(header, 8);
            int col = byte2Int(header,12);
            int size = byte2Int(header,4);

            int len = row*col;
            double data[][] = new double[size][len];
            byte[] bufBytes = new byte[len];
            int idx = 16;
           // int n = 0;
            for(int i =0;i < size; i++){
                bufIns.read(bufBytes,0,len);
                for(int j = 0; (j) < bufBytes.length; j++){
                    data[i][j] = ((double)byte2Int2(bufBytes,j))/255.0;
                }
            }
            return data;

        }
        double dt[][] = {};
        return dt;
    }

    public static int byte2Int(byte[] bytes, int offset){
        return (bytes[offset]&0xff)<<24
                |(bytes[offset+1]&0xff)<<16
                |(bytes[offset+2]&0xff)<<8
                |(bytes[offset+3]&0xff);
    }

    public static int byte2Int2(byte[] bytes, int offset){
        return (int)(bytes[offset]&0xff);
    }

    public static int[] readIntLabelFromFile(String fileName) throws Exception{
        FileInputStream fin = new FileInputStream(fileName);
        if(fin != null) {
            BufferedInputStream bufIns = new BufferedInputStream(fin);
            byte[] header = new byte[8];
            bufIns.read(header,0,8);
            int magic = byte2Int(header,0);
            int size = byte2Int(header,4);
            byte[] y= new byte[size];
            bufIns.read(y,0,size);
            int data[] = new int[size];
            for(int i =0;i < size; i++){
                int label = (int)y[i]&0xff;
                data[i] = label;
            }
            return data;

        }
        int df[] = {};
        return df;
    }

    public static int[] readLabelSingleFromFile(String fileName) throws Exception{
        FileInputStream fin = new FileInputStream(fileName);
        if(fin != null) {
            BufferedInputStream bufIns = new BufferedInputStream(fin);
            byte[] header = new byte[8];
            bufIns.read(header,0,8);
            int magic = byte2Int(header,0);
            int size = byte2Int(header,4);
            byte[] y= new byte[size];
            bufIns.read(y,0,size);
            int data[] = new int[size];
            for(int i =0;i < size; i++){
                int label = (int)y[i]&0xff;
                data[i] = label;
            }
            return data;

        }
        int df[] = {};
        return df;
    }

    public static double[][] readLabelFromFile(String fileName) throws Exception{
        FileInputStream fin = new FileInputStream(fileName);
        if(fin != null) {
            BufferedInputStream bufIns = new BufferedInputStream(fin);
            byte[] header = new byte[8];
            bufIns.read(header,0,8);
            int magic = byte2Int(header,0);
            int size = byte2Int(header,4);
            byte[] y= new byte[size];
            bufIns.read(y,0,size);
            double data[][] = new double[size][10];
            for(int i =0;i < size; i++){
                int label = (int)y[i]&0xff;
                for(int j = 0;  j< 10 ;j++) {
                    if(j==label) {
                        data[i][j] = 1;
                    }else{
                        data[i][j] = 0;
                    }
                }
            }
            return data;

        }
        double df[][] = {};
        return df;
    }

    public static byte[][] readByteFeatruesFromFile(String fileName) throws Exception{
        FileInputStream fin = new FileInputStream(fileName);
        if(fin != null){
            BufferedInputStream bufIns = new BufferedInputStream(fin);
            byte[] header = new byte[16];
            bufIns.read(header,0,header.length);

            int magic = byte2Int(header,0);
            int row = byte2Int(header, 8);
            int col = byte2Int(header,12);
            int size = byte2Int(header,4);

            int len = row*col;
            byte data[][] = new byte[size][len];
            byte[] bufBytes = new byte[len];
            int idx = 16;
            // int n = 0;
            for(int i =0;i < size; i++){
                bufIns.read(bufBytes,0,len);
                for(int j = 0; (j) < bufBytes.length; j++){
                    data[i][j] = bufBytes[j];
                }
            }
            return data;

        }
        byte dt[][] = {};
        return dt;
    }

    public static void main(String[] args) throws Exception {
        Properties props = System.getProperties();
//        double label[][] = MNISTUtils.readLabelFromFile("D://projects/mlf/src/main/resources/MNIST/train-labels.idx1-ubyte");
      //  double data[][] = MNISTUtils.readFeatruesFromFile("D://projects/mlf/src/main/resources/MNIST/train-images.idx3-ubyte");
        byte dataByte[][] = MNISTUtils.readByteFeatruesFromFile("D://projects/mlf/src/main/resources/MNIST/train-images.idx3-ubyte");
        int label[] = MNISTUtils.readIntLabelFromFile("D://projects/mlf/src/main/resources/MNIST/train-labels.idx1-ubyte");
        //        for(int i = 0 ;i<5;i++){
//            Matrix m = new Matrix(28,28);
//            for(int r = 0; r < 28;r++){
//                for(int c = 0; c<28;c++){
//                    m.set(r,c,data[i][r*28+c]);
//                }
//            }
//            System.out.println(m);
//        }


          //  System.out.println(id);
        //    Image image = Toolkit.getDefaultToolkit().createImage(dataByte[0]);
            int index = 0;
            BufferedImage bufferedImage = getBufferedImage(dataByte[index]);
            Image image = bufferedImage.getScaledInstance(28*10,28*10,Image.SCALE_DEFAULT);
            BufferedImage tag = new BufferedImage(28*10,28*10,BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image,0,0,null);
            ImageIcon icon = new ImageIcon(image);
            JLabel draw = new JLabel(icon);
            JFrame frame = new JFrame();
            JPanel ctrPane = new JPanel();
           // ctrPane.add(draw);
            JButton next = new JButton("next");

            JButton prev = new JButton("prev");
            ctrPane.add(next);
            ctrPane.add(prev);
            frame.getContentPane().setLayout(new BorderLayout());
            frame.getContentPane().add(draw, BorderLayout.CENTER);
            frame.getContentPane().add(ctrPane, BorderLayout.SOUTH);
            //frame.setContentPane(ctrPane);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("mnist");

            frame.setSize(300, 400);

           // frame.pack();
            frame.setVisible(true);

    }

    private static BufferedImage getBufferedImage(byte[] bytes) {
        BufferedImage bufferedImage =new BufferedImage(28,28,BufferedImage.TYPE_INT_RGB);
//            bufferedImage.
        for(int w = 0; w < 28; w++){
            for(int h = 0; h < 28; h++){
               // int color = (int)(dataByte[2][h*w]&0xff);
                byte rgbb[]= new byte[4];
                rgbb[0] = 0x00;
                rgbb[1] = (byte)(255-(byte)(bytes[w*28+h]&0xff));
                rgbb[2] = rgbb[1];
                rgbb[3] = rgbb[1];

                int rgb = byte2Int(rgbb,0);
                bufferedImage.setRGB(w,h,rgb);
            }
        }
        return bufferedImage;
    }
}
