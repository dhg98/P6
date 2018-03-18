package es.ucm.fdi.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import es.ucm.fdi.model.*;

public class CarTest {

	@Test
	public void testAvanza() {
		Junction j1 = new Junction("j1");
		Junction j2 = new Junction("j2");
		List<Junction> it = new ArrayList<>(); it.add(j1); it.add(j2);
		Road r1 = new Road("r1", 50, 20, j1, j2);
		Car c1 = new Car("v1", 15, r1, it, 10, 0.4, 6, 4246);
		c1.setVelAct(15);
		c1.avanza(); 
		assertEquals(c1.getLocation(), 15); 
		c1.avanza(); 
		if(c1.getTiempoAveria() > 0) {
			assertEquals(c1.getLocation(), 15);
		} else {
			assertEquals(c1.getLocation(), 30);
		}
	}

}
