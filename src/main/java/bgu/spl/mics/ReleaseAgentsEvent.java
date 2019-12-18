package bgu.spl.mics;

import java.util.List;

public class ReleaseAgentsEvent implements Event {
    List<String> list;

    public ReleaseAgentsEvent(List<String> list) {
        this.list = list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }
}
