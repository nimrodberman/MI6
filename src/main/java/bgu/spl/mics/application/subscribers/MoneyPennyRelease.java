package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.*;
import bgu.spl.mics.application.EndActivities;
import bgu.spl.mics.application.passiveObjects.Report;
import bgu.spl.mics.application.passiveObjects.Squad;

import java.util.List;
import java.util.concurrent.Semaphore;

public class MoneyPennyRelease extends Subscriber {
    private int serial;
    private Squad squad;
    private List<String> agentNumbersInSquad;


    public MoneyPennyRelease(int Serial, List<String> list) {
        super("MoneyPenny" + Serial);
        serial = Serial;
        squad = Squad.getInstance();
        agentNumbersInSquad = list;

    }

    @Override
    protected void initialize() {

        Callback<AgentActiveEvent> activeagent = (AgentActiveEvent t) ->{
            squad.sendAgents(t.getList(),t.getTime());
            complete(t,null);

        };
        Callback<ReleaseAgentsEvent> realseagents = (ReleaseAgentsEvent t) ->{
            squad.releaseAgents(t.getList());
            complete(t,null);
        };
        Callback<EndActivities> endActivitiesCallback = (EndActivities t) -> {
            squad.releaseAgents(agentNumbersInSquad);
            this.terminate();
        };
        this.subscribeBroadcast(EndActivities.class, endActivitiesCallback);
        this.subscribeEvent(ReleaseAgentsEvent.class, realseagents);
        this.subscribeEvent(AgentActiveEvent.class, activeagent);
    }

}

