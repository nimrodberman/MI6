package bgu.spl.mics;

import java.util.List;

public class AgentActiveEvent implements Event {
    private List<String> list;
    private  int time;

    public AgentActiveEvent(List<String> list, int t) {
        this.list = list;
        time = t;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
