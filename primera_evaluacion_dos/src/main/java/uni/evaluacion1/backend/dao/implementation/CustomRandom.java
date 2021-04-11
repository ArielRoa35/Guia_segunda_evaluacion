package uni.evaluacion1.backend.dao.implementation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class CustomRandom {

    private RandomAccessFile rafH;
    private RandomAccessFile rafD;
    private RandomAccessFile rafT;

    public CustomRandom(File fileHead, File fileData, File tempData) throws FileNotFoundException, IOException {
        rafH = new RandomAccessFile(fileHead, "rw");
        rafD = new RandomAccessFile(fileData, "rw");
        rafT = new RandomAccessFile(tempData, "rw");

        if (fileHead.length() == 0) {
            rafH.writeInt(0);
            rafH.writeInt(0);
            rafT.writeInt(0);
        }
    }

    public void close() throws IOException{
        if(rafH != null){
            rafH.close();
        }
        
        if(rafD != null){
            rafD.close();
        }
        
        if(rafT != null){
            rafT.close();
        }
        
    }
    
    public RandomAccessFile getRafH() {
        return rafH;
    }

    public RandomAccessFile getRafD() {
        return rafD;
    }

    public RandomAccessFile getRafT() {
        return rafT;
    }
    
    
}
