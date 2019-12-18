package bgu.spl.mics.application;
import bgu.spl.mics.application.passiveObjects.*;
import com.google.gson.Gson;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonStreamParser;

import javax.print.DocFlavor;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;


/** This is the Main class of the application. You should parse the input file, 
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */
public class MI6Runner {
    public static void main(String[] args) {
        Gson gson = new Gson();

        try {
            Reader reader = new FileReader("C:\\Users\\Nimrod\\Documents\\GitHub\\MI6\\src\\test\\java\\bgu\\spl\\mics\\info.json");
            JsonParse file = gson.fromJson(reader,JsonParse.class);
            //create a reference

            Inventory inventory = Inventory.getInstance();
            Squad squad = Squad.getInstance();

            // upload passive objects
            inventory.load(file.getInventory());
            squad.load(file.getSquad());

            // upload active objects
            //ExecutorService e = Executors.newFixedThreadPool(5);




            reader.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }



    }
}
