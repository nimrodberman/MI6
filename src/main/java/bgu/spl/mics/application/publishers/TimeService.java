package bgu.spl.mics.application.publishers;

import bgu.spl.mics.*;
import bgu.spl.mics.application.EndActivities;
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
	private final int update = 100;
	private Timer timer;
	private SimplePublisher simplePublisher;
	private int ticknumber;
	private int duration;
	/**
	 * Retrieves the single instance of this class.
	 */

	/// **************** do we need to change to private?? because it is a singleton???????????????????
	public TimeService(int duration) {
		super("TimeService");
		this.timer = new Timer();
		this.simplePublisher = new SimplePublisher();
		this.ticknumber =0;
		this.duration = duration;
	}

	@Override
	protected void initialize() {
		this.run();
	}

	@Override
	public void run() {
		this.timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (ticknumber>duration){
					simplePublisher.sendBroadcast(new EndActivities());
					timer.cancel();
					timer.purge();
				}
				else{
					Broadcast b = new TickBroadcast(ticknumber);
					simplePublisher.sendBroadcast(b);
					ticknumber++;
				}
			}
		} , update);

	}


}