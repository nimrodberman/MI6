package bgu.spl.mics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class FutureTest {
    Future <Integer> check;

    @BeforeEach
    public void setUp(){
        check = new Future<Integer>();

    }

    @Test
    public void testGet(){
        check.resolve(12);
        assertEquals((Integer)12, check.get());
    }

    @Test
    public void testResolve(){
        check.resolve(12);
        assertEquals((Integer)12, check.get());
        assertTrue(check.isDone());
    }

    @Test
    public void testIsDone(){
        assertFalse(check.isDone());
        check.resolve(12);
        assertTrue(check.isDone());
    }

    @Test
    public void testGet2(){
        assertNull(check.get(1, TimeUnit.SECONDS));
        check.resolve(12);
        assertEquals((Integer)12 , check.get(1,TimeUnit.SECONDS));
    }
}
