package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.*;

/**
 * M handles ReadyEvent - fills a report and sends agents to mission.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class M extends Subscriber {
	int serial;
	int time;

	public M(int Serial) {
		super("M" + Serial);
		serial = Serial;

	}

	@Override
	protected void initialize() {
		this.subscribeEvent(MissionReceivedEvent,MissionHandleCallback);
		this.subscribeBroadcast(TickBroadcast, TimeCallback);

}
