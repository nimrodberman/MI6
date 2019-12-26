package bgu.spl.mics;
//import javafx.collections.transformation.SortedList;

import bgu.spl.mics.application.EndActivities;
import bgu.spl.mics.application.passiveObjects.roundRubin;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * The {@link MessageBrokerImpl class is the implementation of the MessageBroker interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBrokerImpl implements MessageBroker {
	// ________________ fields ________________________
	private ConcurrentHashMap<Subscriber,BlockingQueue<Message>> subscribersQueue;
	private ConcurrentHashMap<Class<? extends Message>, roundRubin> topics;
	private ConcurrentHashMap<Event, Future> futureAndEvents;


	/**
	 *
	 * Retrieves the single instance of this class.
	 */
	// making a singleton using private constructor
	private static class SingletonHolder {
		private static MessageBrokerImpl mb = new MessageBrokerImpl();
	}

	public static MessageBroker getInstance() {
		return SingletonHolder.mb;
	}

	private MessageBrokerImpl() {
		this.subscribersQueue = new ConcurrentHashMap<>() ;
		this.topics = new ConcurrentHashMap<>() ;
		this.futureAndEvents = new ConcurrentHashMap<>() ;
	}

	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, Subscriber m) {
		// check if input is valid
		if (type != null && m != null){
			// set an atomic clock
			AtomicInteger i = new AtomicInteger(0);

			synchronized (topics){
				// if topic does not exit
				if(topics.get(type) == null){
					roundRubin rr = new roundRubin(new ArrayList<>(),i);
					topics.put(type,rr);
					topics.get(type).getList().add(m);
				}
				// else if topic is already initilazed insert the new subscriber
				else{
					// check if the new subscruiber is not exits already
					if (!topics.get(type).getList().contains(m)){
						topics.get(type).getList().add(m);
					}
				}
			}
		}
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, Subscriber m) {
		// check if input is valid
		if (type != null && m != null){
			// set an atomic clock
			AtomicInteger i = new AtomicInteger(0);

			synchronized (topics){
				// if topic does not exit
				if(topics.get(type) == null){
					roundRubin rr = new roundRubin(new ArrayList<>(),i);
					topics.put(type,rr);
					topics.get(type).getList().add(m);
				}
				// else if topic is already initilazed insert the new subscriber
				else{
					// check if the new subscruiber is not exits already
					if (!topics.get(type).getList().contains(m)){
						topics.get(type).getList().add(m);
					}
				}
			}
		}

	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		if(e != null && result != null){
		    if(futureAndEvents.get(e) == null){
		        Future f = new Future();
		        f.resolve(result);
		        futureAndEvents.put(e,f);
            }
            else {
                futureAndEvents.get(e).resolve(result);
            }

		}
	}

	@Override
	public void sendBroadcast(Broadcast b) {
		if (b != null  && topics.get(b.getClass()) != null && !topics.get(b.getClass()).getList().isEmpty()){
			synchronized (topics) {
				ArrayList<Subscriber> toBroad = topics.get(b.getClass()).getList();
				if(b.getClass() == EndActivities.class || b.getClass() == EndActivityByM.class) {

					for (Subscriber s : toBroad) {
						BlockingQueue<Message> bb = new LinkedBlockingQueue<>();
						bb.add(b);
						for (Message mes : subscribersQueue.get(s)){
							bb.add(mes);
							subscribersQueue.get(s).remove(mes);

						}
						subscribersQueue.get(s).addAll(bb);
					}

				}
				else{
					for (Subscriber s : toBroad) {
						subscribersQueue.get(s).add(b);
					}

				}
			}
		}
	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		Future<T> f = new Future<>();

		// check if is a valid event and if the topic exist
		if (e != null  && topics.get(e.getClass()) != null && !topics.get(e.getClass()).getList().isEmpty()){
			synchronized (topics){
				// get the atomic counter
				AtomicInteger counter = topics.get(e.getClass()).getCounter();
				// insert the bond of those objects
				futureAndEvents.put(e,f);
				//add the mission to one of the subscribers
				System.out.println(topics.get(e.getClass()).getList().get(counter.get()).getName() + " get " + e.toString() + " with index: " + counter.get());
				subscribersQueue.get(topics.get(e.getClass()).getList().get(counter.get())).add(e);
				// maintain the pointer to the next subscriber in line
				if(counter.get() < topics.get(e.getClass()).getList().size()-1){
					topics.get(e.getClass()).setItonext();
				}
				else {
					topics.get(e.getClass()).setItozero();
				}
			}
		}
		else{
			// let the sender know that there are no threads available
			f = null;
		}
		return f;
	}

	@Override
	public void register(Subscriber m) {
		subscribersQueue.put(m,new LinkedBlockingQueue<>());

	}

	@Override
	public void unregister(Subscriber m) {
		if(m != null && subscribersQueue.get(m) != null){
			// delete the subscriber
			subscribersQueue.get(m).clear();
			// delete the subscriber from the topics
			synchronized (topics){
				// collect all topics
				for (roundRubin r : topics.values()){
					if (r.getList().contains(m)){
						r.decrease(r.getList().indexOf(m));
						r.getList().remove(m);
					}
				}
			}
		}
	}

	@Override
	public Message awaitMessage(Subscriber m) throws InterruptedException {
		Message mission = null;
		try {
			mission = subscribersQueue.get(m).take();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		return mission;
	}

	@Override
	public boolean isExist(Subscriber david) {
		return subscribersQueue.get(david) != null;
	}


}
