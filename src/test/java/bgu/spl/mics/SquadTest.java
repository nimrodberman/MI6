package bgu.spl.mics;

import bgu.spl.mics.application.passiveObjects.Agent;
import bgu.spl.mics.application.passiveObjects.Squad;
import com.sun.source.tree.AssertTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.LinkedList;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

public class SquadTest {
    Squad check;

    @BeforeEach
    public void setUp(){
        check = new Squad();
        Agent nimrod = new Agent();
        nimrod.setName("nimrod");
        nimrod.setSerialNumber("007");
        Agent oron = new Agent();
        oron.setName("oron");
        oron.setSerialNumber("001");
        oron.setAvilability(false);
        check.load(new Agent[]{nimrod, oron});


    }

    @Test
    public void testGetAgent(){
        List serials = new List();
        serials.add("007");
        assertTrue(check.getAgents((java.util.List<String>) serials));
        serials.add("002");
        assertFalse(check.getAgents((java.util.List<String>) serials));
    }

    @Test
    public void testReleaseAgent(){
        List serials = new List();
        serials.add("001");
        check.releaseAgents((java.util.List<String>) serials);
        assertTrue(check.getAgents((java.util.List<String>) serials));
    }

    @Test
    public void testGetAgentNames(){
        List serials = new List();
        serials.add("001");
        serials.add("007");
        List result = (List) check.getAgentsNames((java.util.List<String>) serials);
        List names = new List();
        names.add("oron");
        names.add("nimrod");
        assertEquals(result,names);

    }

    @Test
    public void testSendAgents(){
        List serials = new List();
        serials.add("001");
        check.sendAgents((java.util.List<String>) serials,1);
        assertTrue(check.getAgents((java.util.List<String>) serials));


    }

    @Test
    public void testLoad(){
        List serials = new List();
        serials.add("007");
        serials.add("001");
        assertTrue(check.getAgents((java.util.List<String>) serials));
        assertFalse(check.getAgents((java.util.List<String>) serials));

    }
}
