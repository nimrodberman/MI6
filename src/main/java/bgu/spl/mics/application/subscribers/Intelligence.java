package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.*;
import bgu.spl.mics.application.EndActivities;
import bgu.spl.mics.application.passiveObjects.MissionInfo;

/**
 * A Publisher only.
 * Holds a list of Info objects and sends them
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Intelligence extends Subscriber {
	private int serial;
	private MissionInfo[]  missions;
	private int time;
	//private SimplePublisher sp;

	public Intelligence(int s, MissionInfo[] m) {
		super("Intelligence" + s);
		missions = m;

	}

	public void load(){

	}

	@Override
	protected void initialize() {

		Callback<TickBroadcast> timecall = (TickBroadcast t) -> {
			time = t.getTickNumber();
			for (MissionInfo m: missions){
				if(m.getTimeIssued() == time){
					Future future = getSimplePublisher().sendEvent(new MissionReceivedEvent(m));
				}
			}
		};
		subscribeBroadcast(TickBroadcast.class,timecall);

		Callback<EndActivities> endActivitiesCallback = (EndActivities t) -> {
			this.terminate();
		};
		this.subscribeBroadcast(EndActivities.class, endActivitiesCallback);
	}

	public MissionInfo[] getMissions() {
		return missions;
	}
}
