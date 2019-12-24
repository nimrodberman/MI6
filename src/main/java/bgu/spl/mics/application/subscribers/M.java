package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.*;
import bgu.spl.mics.application.EndActivities;
import bgu.spl.mics.application.passiveObjects.BureaucracyPapers;
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
			System.out.println("M " + serial + " running the mission");
			diary.incrementTotal();
			Report report = new Report();
			// M update her part in the report
			report.setTimeCreated(time);
			report.setM(serial);
			report.setMissionName(s.getMissionInfo().getMissionName());
			report.setAgentsSerialNumbersNumber(s.getMissionInfo().getSerialAgentsNumbers());
			report.setGadgetName(s.getMissionInfo().getGadget());
			report.setTimeIssued(s.getMissionInfo().getTimeIssued());

			BureaucracyPapers b = new BureaucracyPapers();
			Future<BureaucracyPapers> future = getSimplePublisher().sendEvent(new AgentsAvailableEvent(s.getMissionInfo().getSerialAgentsNumbers(), b));

			// wait until agents are available
			BureaucracyPapers report2 = new BureaucracyPapers();
			// check if there is available MP to manage the agents
			if(future != null)
				report2 = future.get();

			//get the report from Q
			BureaucracyPapers report3 = new BureaucracyPapers();
			//check if agents exist and if there is available MP before create new future
			if (future != null && report2.isAgentsExists()) {
				Future<BureaucracyPapers> future1 = getSimplePublisher().sendEvent(new GadgetAvailableEvent(s.getMissionInfo().getGadget(), report2));
				// check if there is Q available
				if(future1 != null)
					report3 = future1.get();
			}

			// if all conditions are valid send the mission
			if(report3.isGadetIsExist() && report3.isAgentsExists() && report3.getQTime() <= s.getMissionInfo().getTimeExpired()){
				// update MP and Q details
				report.setAgentsNames(report3.getAgentsNames());
				report.setMoneypenny(report3.getMP());
				report.setQTime(report3.getQTime());
				diary.addReport(report);
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
