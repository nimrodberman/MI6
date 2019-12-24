package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.*;
import bgu.spl.mics.application.EndActivities;
import bgu.spl.mics.application.passiveObjects.BureaucracyPapers;
import bgu.spl.mics.application.passiveObjects.Inventory;

/**
 * Q is the only Subscriber\Publisher that has access to the {@link bgu.spl.mics.application.passiveObjects.Inventory}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Q extends Subscriber {
	private int serial;
	private int time;
	private Inventory inventory;

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
			BureaucracyPapers report = t.getReport();
			report.setQTime(time);

			report.setGadetIsExist(inventory.getItem(t.getGadget()));
			this.complete(t,report);
		};

		this.subscribeBroadcast(TickBroadcast.class, timecall);
		this.subscribeEvent(GadgetAvailableEvent.class, gadgetavailble);
		Callback<EndActivities> endActivitiesCallback = (EndActivities t) -> {
			this.terminate();
		};
		this.subscribeBroadcast(EndActivities.class, endActivitiesCallback);


	}

}
