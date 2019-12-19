package bgu.spl.mics.application.publishers;

import bgu.spl.mics.*;
import bgu.spl.mics.application.passiveObjects.Diary;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * TimeService is the global system timer There is only one instance of this Publisher.
 * It keeps track of the amount of ticks passed since initialization and notifies
 * all other subscribers about the current time tick using {@link Tick Broadcast}.
 * This class may not hold references for objects which it is not responsible for.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class TimeService extends Publisher {

	//____fields____
	private static TimeService instance = new TimeService();
	private Timer timer;
	private SimplePublisher simplePublisher;
	private int ticknumber;
	private int terminate;
	/**
	 * Retrieves the single instance of this class.
	 */

	/// **************** do we need to change to private?? because it is a singleton???????????????????
	public TimeService(String name, int terminate) {
		super("TimeService");
		this.timer = new Timer();
		this.simplePublisher = new SimplePublisher();
		ticknumber =0;
		this.terminate = terminate;
	}

	@Override
	protected void initialize() {

	}

	@Override
	public void run() {
		class Helper extends TimerTask
		{
			public static int i = 0;
			private Broadcast b = new TickBroadcast(i);

			public void run()
			{
				getSimplePublisher().sendBroadcast(b);
				i++;
			}
		}
		TimerTask task = new Helper();

		this.timer.scheduleAtFixedRate(task,300,terminate);

	}


}