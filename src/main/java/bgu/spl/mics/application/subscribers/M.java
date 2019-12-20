package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.*;
import bgu.spl.mics.application.EndActivities;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Report;

/**
 * M handles ReadyEvent - fills a report and sends agents to mission.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class M extends Subscriber {
	private int serial;
	private int time;
	private Diary diary;

	public M(int Serial) {
		super("M" + Serial);
		serial = Serial;
		diary = Diary.getInstance();

	}

	@Override
	protected void initialize() {

		Callback<TickBroadcast> timecall = (TickBroadcast t) -> {
			time = t.getTickNumber();
		};
		this.subscribeBroadcast(TickBroadcast.class, timecall);

		Callback<MissionReceivedEvent> missioncall = (MissionReceivedEvent s) ->{
			diary.incrementTotal();
			Report report = new Report();
			report.setTimeCreated(time);


			Future<Report> future = getSimplePublisher().sendEvent(new AgentsAvailableEvent(s.getMissionInfo().getSerialAgentsNumbers(), report));

			// wait until agents are available
			Report report2 = future.get();

			//get the report from Q
			Future<Report> future1 = getSimplePublisher().sendEvent(new GadgetAvailableEvent(s.getMissionInfo().getGadget(),report2));
			Report report3 = future1.get();

			// if all conditions are valid send the mission
			if(report3.isGadetIsExist() && report3.isAgentsExists() && time <= s.getMissionInfo().getTimeExpired()){
				// update the m details
				report3.setM(serial);
				report.setMissionName(s.getMissionInfo().getMissionName());
				report3.setAgentsSerialNumbersNumber(s.getMissionInfo().getSerialAgentsNumbers());
				report3.setGadgetName(s.getMissionInfo().getGadget());
				report3.setTimeIssued(s.getMissionInfo().getTimeIssued());
				report3.setTimeCreated(time);
				diary.addReport(report3);
				Future t = getSimplePublisher().sendEvent(new AgentActiveEvent(s.getMissionInfo().getSerialAgentsNumbers(),s.getMissionInfo().getDuration()));
			}

			else{
				Future t = getSimplePublisher().sendEvent(new ReleaseAgentsEvent(s.getMissionInfo().getSerialAgentsNumbers()));
			}
		};

		this.subscribeEvent(MissionReceivedEvent.class, missioncall);

		Callback<EndActivities> endActivitiesCallback = (EndActivities t) -> {
			this.terminate();
		};
		this.subscribeBroadcast(EndActivities.class, endActivitiesCallback);
	}

}
