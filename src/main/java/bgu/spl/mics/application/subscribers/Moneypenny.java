package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.*;
import bgu.spl.mics.application.EndActivities;
import bgu.spl.mics.application.passiveObjects.BureaucracyPapers;
import bgu.spl.mics.application.passiveObjects.Squad;

import java.util.LinkedList;
import java.util.List;

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
	private List<String> agentNumbersInSquad;
	private boolean isrealising;


	public Moneypenny(int Serial, List<String> list,boolean is) {
		super("MoneyPenny" + Serial);
		serial = Serial;
		squad = Squad.getInstance();
		agentNumbersInSquad = list;
		isrealising = is;

	}

	@Override
	protected void initialize() {
		Callback<AgentsAvailableEvent> agentavailable = (AgentsAvailableEvent t) -> {
			// update the report
			BureaucracyPapers report = t.getReport();
			report.setMP(serial);
			// set the serial number of each agent
			if(squad.getAgentsNames(t.getAgents()) != null)
				report.setAgentsNames(squad.getAgentsNames(t.getAgents()));
			else{report.setAgentsNames(new LinkedList<>());}

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

		Callback<EndActivities> endActivitiesCallback = (EndActivities t) -> {
			squad.releaseAgents(agentNumbersInSquad);
			this.terminate();
		};
		Callback<AgentActiveEvent> activeagent = (AgentActiveEvent t) ->{
			squad.sendAgents(t.getList(),t.getTime());
			complete(t,null);

		};
		Callback<ReleaseAgentsEvent> realseagents = (ReleaseAgentsEvent t) ->{
			squad.releaseAgents(t.getList());
			complete(t,null);
		};

		if(isrealising == false){
			this.subscribeBroadcast(EndActivities.class, endActivitiesCallback);
			this.subscribeEvent(AgentsAvailableEvent.class, agentavailable);
		}
		else {
			this.subscribeBroadcast(EndActivities.class, endActivitiesCallback);
			this.subscribeEvent(ReleaseAgentsEvent.class, realseagents);
			this.subscribeEvent(AgentActiveEvent.class, activeagent);
		}

	}

}
