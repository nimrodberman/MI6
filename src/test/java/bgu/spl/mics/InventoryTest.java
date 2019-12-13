package bgu.spl.mics;

import bgu.spl.mics.application.passiveObjects.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class InventoryTest {
    Inventory check ;

    @BeforeEach
    public void setUp(){
        check = new Inventory();
        String [] list = {"skyhook" , "pistol" , "knife"};
        check.load(list);
    }

    @Test
    public void testGetItem(){
        assertEquals(true,"pistol");
        assertEquals(false, "bomb");
    }

    @Test
    public void testLoad(){
        assertEquals(true,"skyhook");
        assertEquals(true, "pistol");
        assertEquals(true, "knife");
    }

}
