package bgu.spl.mics;

import bgu.spl.mics.application.passiveObjects.Agent;

import java.util.List;

public class AgentsAvailableEvent implements Event {
    private List<Agent> agents;

    public AgentsAvailableEvent(List<Agent> agents) {
        this.agents = agents;
    }

    public List<Agent> getAgents() {
        return agents;
    }
}
