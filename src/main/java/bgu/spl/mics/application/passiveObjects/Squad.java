package bgu.spl.mics.application.passiveObjects;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Passive data-object representing a information about an agent in MI6.
 * You must not alter any of the given public methods of this class. 
 * <p>
 * You may add ONLY private fields and methods to this class.
 */
public class Squad {

	//____________fields_____________
	private Map<String, Agent> agents;


	//_________constructors__________
	public Squad (){
		
	}

	//____________methods____________
	/**
	 * Retrieves the single instance of this class.
	 */
	public static Squad getInstance() {
		//TODO: Implement this
		return null;
	}

	/**
	 * Initializes the squad. This method adds all the agents to the squad.
	 * <p>
	 * @param agents 	Data structure containing all data necessary for initialization
	 * 						of the squad.
	 */
	public void load (Agent[] agents) {
		synchronized (this.agents){
			for (Agent a: agents) {
				this.agents.put(a.getName(),a);
			}
		}
	}

	/**
	 * Releases agents.
	 */
	public void releaseAgents(List<String> serials){
		for (String s : serials){
			this.agents.get(s).release();
		}
	}

	/**
	 * simulates executing a mission by calling sleep.
	 * @param time   milliseconds to sleep
	 */
	public void sendAgents(List<String> serials, int time){
		try {
			Thread.sleep(time);
		} catch(InterruptedException ex){
			Thread.currentThread().interrupt();
		}
		for (String s: serials) {
			this.agents.get(s).release();
		}
	}

	/**
	 * acquires an agent, i.e. holds the agent until the caller is done with it
	 * @param serials   the serial numbers of the agents
	 * @return ‘false’ if an agent of serialNumber ‘serial’ is missing, and ‘true’ otherwise
	 */
	public boolean getAgents(List<String> serials) throws InterruptedException {
		serials.sort(String::compareTo);
		for (String s: serials) {
			if (!agents.containsKey(s)){
				return false;
			}
			if (!agents.get(s).isAvailable()) {
				do {
					Thread.currentThread().wait();
				} while (!agents.get(s).isAvailable());
			}
			agents.get(s).acquire();
		}
		return true;
	}


    /**
     * gets the agents names
     * @param serials the serial numbers of the agents
     * @return a list of the names of the agents with the specified serials.
     */
    public List<String> getAgentsNames(List<String> serials){
    	List <String> out = new ArrayList<String> ();
		for (String s: serials) {
			out.add(this.agents.get(s).getName());
		}
		return out;
    }
}
