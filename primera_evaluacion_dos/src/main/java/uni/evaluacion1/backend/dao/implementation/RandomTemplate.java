package uni.evaluacion1.backend.dao.implementation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class RandomTemplate {

    private File fileHead;
    private File fileData;
    private File tempData;
    private CustomRandom customRandom;   

    public RandomTemplate(File fileHead, File fileData, File TempData) {
        this.fileHead = fileHead;
        this.fileData = fileData;
        this.tempData = TempData;
    }

    protected CustomRandom getCustomRandom() throws FileNotFoundException, IOException {
        if (customRandom == null) {
            customRandom = new CustomRandom(fileHead, fileData, tempData);
            return customRandom;
        }
        
        return customRandom;
    }
    
    protected void close() throws IOException{
        customRandom.close();
        customRandom = null;
    }

    public File getFileData() {
        return fileData;
    }

    public File getTempData() {
        return tempData;
    }
    
    
}
