package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.*;
import bgu.spl.mics.application.passiveObjects.Inventory;
import bgu.spl.mics.application.passiveObjects.Report;

/**
 * Q is the only Subscriber\Publisher that has access to the {@link bgu.spl.mics.application.passiveObjects.Inventory}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Q extends Subscriber {
	int serial;
	int time;
	Inventory inventory;

	public Q(int Serial) {
		super("Q" + Serial);
		serial = Serial;
		inventory = Inventory.getInstance();
	}

	@Override
	protected void initialize() {

		Callback<TickBroadcast> timecall = (TickBroadcast t) -> {
			time = t.getTickNumber();
		};


		Callback<GadgetAvailableEvent> gadgetavailble = (GadgetAvailableEvent t) -> {
			Report report = t.getReport();
			report.setQTime(time);

			report.setGadetIsExist(inventory.getItem(t.getGadget()));
			this.complete(t,report);
		};

		this.subscribeBroadcast(TickBroadcast.class, timecall);
		this.subscribeEvent(GadgetAvailableEvent.class, gadgetavailble);


	}

}
