package es.ucm.fdi.util;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

import es.ucm.fdi.model.*;

public class RoadTest {

	@Test
	public void testEntraVehiculo() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaleVehiculo() {
		fail("Not yet implemented");
	}

	@Test
	public void testAvanza() {
		Junction j1 = new Junction("j1");
		Junction j2 = new Junction("j2");
		Road r = new Road("r1", 100, 5, j1, j2);
		List<Junction> it = new ArrayList<>();
		it.add(j1); it.add(j2);
		Vehicle v = new Vehicle("v1" , 5, r, it);
		r.avanza();
		assertEquals(5, v.getLocation());
	}

}
