package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.*;
import bgu.spl.mics.application.EndActivities;
import bgu.spl.mics.application.passiveObjects.Report;
import bgu.spl.mics.application.passiveObjects.Squad;

/**
 * Only this type of Subscriber can access the squad.
 * Three are several Moneypenny-instances - each of them holds a unique serial number that will later be printed on the report.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Moneypenny extends Subscriber {
	private int serial;
	private Squad squad;


	public Moneypenny(int Serial) {
		super("MoneyPenny" + Serial);
		serial = Serial;
		squad = Squad.getInstance();
	}

	@Override
	protected void initialize() {


		Callback<AgentsAvailableEvent> agentavailable = (AgentsAvailableEvent t) -> {
			// update the report
			Report report = t.getReport();
			report.setMoneypenny(serial);
			// set the serial number of each agent
			report.setAgentsNames(squad.getAgentsNames(t.getAgents()));

			//check for agents
			try{
				boolean ok = squad.getAgents(t.getAgents());
				if(ok){
					report.setAgentsExists(ok);
					this.complete(t,report);
				}

				else {
					report.setAgentsExists(ok);
					this.complete(t,report);
				}
			}
			catch (InterruptedException e){
				e.printStackTrace();
			}
		};

		Callback<AgentActiveEvent> activeagent = (AgentActiveEvent t) ->{
			squad.sendAgents(t.getList(),t.getTime());
			complete(t,null);

		};

		Callback<ReleaseAgentsEvent> realseagents = (ReleaseAgentsEvent t) ->{
			squad.releaseAgents(t.getList());
			complete(t,null);
		};

		Callback<EndActivities> endActivitiesCallback = (EndActivities t) -> {
			this.terminate();
		};
		this.subscribeBroadcast(EndActivities.class, endActivitiesCallback);
		this.subscribeEvent(ReleaseAgentsEvent.class, realseagents);
		this.subscribeEvent(AgentActiveEvent.class, activeagent);
		this.subscribeEvent(AgentsAvailableEvent.class, agentavailable);
	}

}
