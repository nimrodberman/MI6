package bgu.spl.mics.application.passiveObjects;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


// Help function for printing
public class PrintFile {

    private String file;
    private Object object;


    public PrintFile(String filename, Object object){
        this.file = filename;
        this.object = object; // if we have a problem - it may be here
    }

    public void print(){
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(object);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
