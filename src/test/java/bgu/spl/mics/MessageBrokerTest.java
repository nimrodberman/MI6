package bgu.spl.mics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class MessageBrokerTest {
    /*
    private MessageBroker mbroker;
    private Subscriber s;
    private SubscriberQ q;
    private MissionRecivedEvent event;
    private EventQ eventq;

    @BeforeEach
    public void setUp(){
       mbroker = new MessageBroker();
    }

    @Test
    public void testRegister(){

        Subscriber david = new SubscriberQ();
        mbroker.register(david);
        assertTrue(mbroker.isExist(david));

    }

    @Test
    public void testSubscriberEvent(){

        Subscriber david = new SubscriberQ();
        mbroker.subscribeEvent(eventq, david);
        mbroker.sendEvent(eventq);
        Message s = mbroker.awaitMessage(eventq);
        assertTrue(s !=null);

    }

    @Test
    public void testSubscriberEvent(){

        Subscriber david = new SubscriberQ();
        mbroker.sendBroadcast(eventq);
        mbroker.sendEvent(eventq);
        Message s = mbroker.awaitMessage(eventq);
        assertTrue(s !=null);

    }
    */


}
