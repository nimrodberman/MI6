package bgu.spl.mics;

import bgu.spl.mics.application.passiveObjects.BureaucracyPapers;
import bgu.spl.mics.application.passiveObjects.Report;

public class GadgetAvailableEvent implements Event {

    private String gadget;
    private BureaucracyPapers report;

    public GadgetAvailableEvent(String gadget, BureaucracyPapers re)
    {
        report = re;
        this.gadget = gadget;
    }

    public String getGadget() {
        return gadget;
    }

    public void setGadget(String gadget) {
        this.gadget = gadget;
    }

    public void setReport(BureaucracyPapers report) {
        this.report = report;
    }

    public BureaucracyPapers getReport() {
        return report;
    }
}
