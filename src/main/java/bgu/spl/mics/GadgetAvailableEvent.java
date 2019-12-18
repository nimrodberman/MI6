package bgu.spl.mics;

import bgu.spl.mics.application.passiveObjects.Report;

public class GadgetAvailableEvent implements Event {

    private String gadget;
    private Report report;

    public GadgetAvailableEvent(String gadget, Report re)
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

    public void setReport(Report report) {
        this.report = report;
    }

    public Report getReport() {
        return report;
    }
}
