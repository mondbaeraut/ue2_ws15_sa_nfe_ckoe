package bean;

import interfaces.IOable;

import java.io.StreamCorruptedException;

/**
 * Created by mod on 11/23/15.
 */
public class LoadImageBean implements IOable {
    private String filename = "";
    private SourceFile sourceFile = null;

    public void setFilename(String filename){
        this.filename = filename;
        System.out.println("Image name set");
        sourceFile = new SourceFile(filename);

    }
    public String getFilename(){
        return filename;
    }

    @Override
    public Object read() throws StreamCorruptedException {
        return null;
    }

    @Override
    public void write(Object value) throws StreamCorruptedException {

    }

}
