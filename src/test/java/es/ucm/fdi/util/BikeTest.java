package es.ucm.fdi.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import es.ucm.fdi.model.Bike;
import es.ucm.fdi.model.Junction;
import es.ucm.fdi.model.Road;

public class BikeTest {

	@Test
	public void AvanzaTest() {
		Junction j1 = new Junction("j1");
		Junction j2 = new Junction("j2");
		List<Junction> it = new ArrayList<>(); it.add(j1); it.add(j2);
		Road r1 = new Road("r1", 50, 10, j1, j2);
		Bike v1 = new Bike("v1", 30, r1, it);
		j2.addIncomingRoad(r1);
		v1.setTiempoAveria(3);
		r1.entraVehiculo(v1);
		r1.avanza();
		assertEquals(v1.getTiempoAveria(), 0);
		assertEquals(v1.getLocation(), 10);
	}
	
	@Test
	public void FaultyTest() {
		Junction j1 = new Junction("j1");
		Junction j2 = new Junction("j2");
		List<Junction> it = new ArrayList<>(); it.add(j1); it.add(j2);
		Road r1 = new Road("r1", 50, 10, j1, j2);
		Bike v1 = new Bike("v1", 15, r1, it);
		j2.addIncomingRoad(r1);
		r1.entraVehiculo(v1);
		r1.avanza();
		v1.setTiempoAveria(2);
		r1.avanza();
		assertEquals(v1.getTiempoAveria(), 1);
		assertEquals(v1.getVelAct(), 0);
	}

}
