package bgu.spl.mics;

public class TickBroadcast implements Broadcast {
    private int tickNumber;

    public TickBroadcast(int tickNumber) {
        this.tickNumber = tickNumber;
    }

    public int getTickNumber() {
        return tickNumber;
    }

    public void setTickNumber(int tickNumber) {
        this.tickNumber = tickNumber;
    }
}
