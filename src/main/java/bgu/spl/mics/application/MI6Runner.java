package bgu.spl.mics.application;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.passiveObjects.*;
import bgu.spl.mics.application.publishers.TimeService;
import bgu.spl.mics.application.subscribers.*;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
    public static void main(String[] args) throws InterruptedException {
        Gson gson = new Gson();
        List<Thread> threadList = new ArrayList<>();

        // Upload all files
        try {
            Reader reader = new FileReader("/users/studs/bsc/2020/bermann/Desktop/version work 2/23.12/MI6-nimrod_23_12 _new_ver/src/test/java/bgu/spl/mics/info.json");
            JsonParse file = gson.fromJson(reader,JsonParse.class);
            //create a reference

            Inventory inventory = Inventory.getInstance();
            Squad squad = Squad.getInstance();

            // upload passive objects
            inventory.load(file.getInventory());
            squad.load(file.getSquad());

            // create a list of agent serials for MoneyPenny
            List<String> agentsformp = new LinkedList<>();
            for (Agent a: file.getSquad()){
                agentsformp.add(a.getSerialNumber());
            }

            // upload M instances
            for (int i = 0; i < file.getServices().getM(); i++){
                M tmp = new M(i+1);
                Thread toAdd = new Thread(tmp);
                threadList.add(toAdd);
            }
            // upload MoneyPenny instances
            for (int i = 0; i < file.getServices().getMp(); i++){
                if (i < file.getServices().getMp()/2) {
                    Moneypenny tmp = new Moneypenny(i + 1,agentsformp);
                    Thread toAdd = new Thread(tmp);
                    threadList.add(toAdd);
                }
                else {
                    MoneyPennyRelease tmp = new MoneyPennyRelease(i + 1,agentsformp);
                    Thread toAdd = new Thread(tmp);
                    threadList.add(toAdd);
                }
            }

            // upload intellegence
            int counter = 1;
            for (Intelligence i : file.getServices().getIntelligences()){
                Intelligence tmp = new Intelligence(counter, file.getServices().getIntelligences()[counter-1].getMissions());
                Thread toAdd = new Thread(tmp);
                threadList.add(toAdd);
                counter++;
            }

            //upload Q Instance
            Q tmp = new Q(0);
            Thread toAdd = new Thread(tmp);
            threadList.add(toAdd);

            //upload time service
            TimeService tmp1 = new TimeService(file.getServices().getTime());
            Thread toAdd1 = new Thread(tmp1);
            threadList.add(toAdd1);

            reader.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("All OK");

        for (Thread s:threadList){
            s.start();
        }

        for (Thread s:threadList){
            s.join();
        }

        Diary toPrint = Diary.getInstance();
        toPrint.printToFile("Missions");

        Inventory toPrint1 = Inventory.getInstance();
        toPrint1.printToFile("Inventory");



    }
}
