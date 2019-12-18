package bgu.spl.mics;

import bgu.spl.mics.application.passiveObjects.Agent;
import bgu.spl.mics.application.passiveObjects.Report;

import java.io.Reader;
import java.util.List;

public class AgentsAvailableEvent implements Event {
    private List<String> agents;
    private Report report;

    public AgentsAvailableEvent(List<String> agents, Report re) {
        this.agents = agents;
        report = re;
    }

    public List<String> getAgents() {
        return agents;
    }

    public void setAgents(List<String> agents) {
        this.agents = agents;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public Report getReport() {
        return report;
    }
}
