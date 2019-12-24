package bgu.spl.mics.application.passiveObjects;

import bgu.spl.mics.Subscriber;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class roundRubin {

    private ArrayList<Subscriber> list;
    private AtomicInteger i;

    public roundRubin(ArrayList<Subscriber> List, AtomicInteger x){
        this.list = List;
        this.i = x;
    }

    public ArrayList<Subscriber> getList() {
        return list;
    }

    public void setList(ArrayList<Subscriber> List) {
        this.list = List;
    }

    public AtomicInteger getCounter() {
        return i;
    }


    public void setI(int x) {
        this.i.set(x);
    }


    public void setItonext() {
        this.i.incrementAndGet();
    }


    public void setItozero() {
        this.i.set(0);
    }

    public void decrease(int m) {
        if (m < i.get()){
            i.decrementAndGet();
        }
    }


}
