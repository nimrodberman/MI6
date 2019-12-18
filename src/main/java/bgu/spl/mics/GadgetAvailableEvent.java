package bgu.spl.mics;

public class GadgetAvailableEvent implements Event {

    private String gadget;

    public GadgetAvailableEvent(String gadget) {
        this.gadget = gadget;
    }

    public String getGadget() {
        return gadget;
    }
}
