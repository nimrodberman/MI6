package bgu.spl.mics;

import bgu.spl.mics.application.passiveObjects.Agent;
import bgu.spl.mics.application.passiveObjects.BureaucracyPapers;
import bgu.spl.mics.application.passiveObjects.Report;

import java.io.Reader;
import java.util.List;

public class AgentsAvailableEvent implements Event {
    private List<String> agents;
    private BureaucracyPapers report;

    public AgentsAvailableEvent(List<String> agents, BureaucracyPapers re) {
        this.agents = agents;
        report = re;
    }

    public List<String> getAgents() {
        return agents;
    }

    public void setAgents(List<String> agents) {
        this.agents = agents;
    }

    public void setReport(BureaucracyPapers report) {
        this.report = report;
    }

    public BureaucracyPapers getReport() {
        return report;
    }
}
