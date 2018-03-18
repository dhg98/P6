package es.ucm.fdi.util;

import static org.junit.Assert.assertEquals;

import java.util.*;

import org.junit.Test;

import es.ucm.fdi.model.*;

public class LanesRoadTest {
	
	@Test
	public void testAvanza() {
		Junction j1 = new Junction("j1");
		Junction j2 = new Junction("j2");
		LanesRoad lr = new LanesRoad("r1", 100, 25, j1, j2, 2);
		List<Junction> it = new ArrayList<>();
		it.add(j1); it.add(j2);
		Vehicle v1 = new Vehicle("v1" , 50, lr, it);
		Vehicle v2 = new Vehicle("v2" , 25, lr, it);
		Vehicle v3 = new Vehicle("v3" , 20, lr, it);
		lr.entraVehiculo(v1);
		lr.entraVehiculo(v2);
		lr.entraVehiculo(v3);
		v1.setVelAct(40);
		v2.setVelAct(10);
		v3.setVelAct(5);
		lr.avanza();
		v1.setTiempoAveria(3);
		lr.avanza();
		assertEquals(v2.getVelAct(), 17);
		v2.setTiempoAveria(2);
		lr.avanza();
		assertEquals(v3.getVelAct(), 17);
	}
}
