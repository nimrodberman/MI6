package bgu.spl.mics.application.passiveObjects;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Passive data-object representing a information about an agent in MI6.
 * You must not alter any of the given public methods of this class. 
 * <p>
 * You may add ONLY private fields and methods to this class.
 */
public class Squad {
	private static Squad instance = new Squad();


	//____________fields_____________
	private Map<String, Agent> agents;


	//_________constructors__________

	private Squad() {
		this.agents = new HashMap<>();
	}


	//____________methods____________
	/**
	 * Retrieves the single instance of this class.
	 */
	public static Squad getInstance() {
		return instance;
	}

	/**
	 * Initializes the squad. This method adds all the agents to the squad.
	 * <p>
	 * @param agents 	Data structure containing all data necessary for initialization
	 * 						of the squad.
	 */
	public void load (Agent[] agents) {
		synchronized (this){
			for (Agent a: agents) {
				a.setAvilability(true);
				this.agents.put(a.getSerialNumber(),a);

			}
		}
	}

	/**
	 * Releases agents.
	 */
	public void releaseAgents(List<String> serials){
		System.out.println("before syn");
		synchronized (this) {
			for (String s : serials) {
				if (this.agents.containsKey(s)) {
					this.agents.get(s).release();
					System.out.println("agent" + s + "free");
				}
			}
			try {
				this.notifyAll();
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * simulates executing a mission by calling sleep.
	 * @param time   milliseconds to sleep
	 */
	public void sendAgents(List<String> serials, int time){
		synchronized (this) {
			try {
				Thread.sleep(time);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			this.releaseAgents(serials);
		}
	}

	/**
	 * acquires an agent, i.e. holds the agent until the caller is done with it
	 * @param serials   the serial numbers of the agents
	 * @return ‘false’ if an agent of serialNumber ‘serial’ is missing, and ‘true’ otherwise
	 */
	public boolean getAgents(List<String> serials) throws InterruptedException {
		System.out.println("before syn");

		synchronized(this) {
			serials.sort(String::compareTo);
			for (String s : serials) {
				if (!agents.containsKey(s)) {
					return false;
				}
				while(!agents.get(s).isAvailable()){this.wait();}
				agents.get(s).acquire();
				System.out.println("agent" + s + "aquiered");
			}
			return true;
		}
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
