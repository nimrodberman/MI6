package bgu.spl.mics.application.passiveObjects;

import java.util.LinkedList;
import java.util.List;

/**
 * Passive object representing the diary where all reports are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private fields and methods to this class as you see fit.
 */
public class Diary {

	//___________fields_____________
	private static Diary instance = new Diary();
	private List <Report> reports;
	private int total;

	/**
	 * Retrieves the single instance of this class.
	 */
	private Diary(){
		reports = new LinkedList<Report>();
		total = 0;
	}

	public static Diary getInstance() {
		return instance;
	}

	public List<Report> getReports() {
		return this.reports;
	}

	/**
	 * adds a report to the diary
	 * @param reportToAdd - the report to add
	 */
	public void addReport(Report reportToAdd){
		synchronized (this.reports){
			this.reports.add(reportToAdd);
		}
	}

	/**
	 *
	 * <p>
	 * Prints to a file name @filename a serialized object List<Report> which is a
	 * List of all the reports in the diary.
	 * This method is called by the main method in order to generate the output.
	 *
	 *
	 */
	public void printToFile(String filename){
		PrintFile output = new PrintFile(filename , this.reports);
		output.print();
	}

	public  void print(){
		System.out.println(total);
		for(Report r: reports){
			System.out.println(r.getM());
			System.out.println(r.getAgentsNames());
			System.out.println(r.getTimeCreated());
			System.out.println(r.getTimeIssued());
			System.out.println(r.getQTime());
		}
	}

	/**
	 * Gets the total number of received missions (executed / aborted) be all the M-instances.
	 * @return the total number of received missions (executed / aborted) be all the M-instances.
	 */
	public int getTotal(){
		return this.total;
	}

	public void incrementTotal() {
		this.total+=1;
	}
}
