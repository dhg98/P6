package es.ucm.fdi.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import es.ucm.fdi.model.Junction;
import es.ucm.fdi.model.Road;
import es.ucm.fdi.model.Vehicle;

public class JunctionTest {

	@Test
	public void test() {
		//Test sencillo. Se avanza el coche hasta que llega a una interseccion y se comprueba su semaforo. Segun esta prueba ese hecho se produce
		//en la primera iteracion.
		Junction j1 = new Junction("j1");
		Junction j2 = new Junction("j2");
		Junction j3 = new Junction("j3");
		
		ArrayList<Junction> it = new ArrayList<>();
		it.add(j1); it.add(j2); it.add(j3);
		
		Road r1 = new Road("r1", 20, 20, j1, j2);
		Road r2 = new Road("r2", 40, 15, j2, j3);
		
		Vehicle v1 = new Vehicle("v1", 25, r1, it);
		
		r1.entraVehiculo(v1);
		
		j2.addIncomingRoad(r1);
		j3.addIncomingRoad(r2);
		j1.getOutgoingRoadsList().add(r1);
		j2.getOutgoingRoadsList().add(r2);
		r1.avanza();
		
		Map<String, String> test = new LinkedHashMap<>();
		j2.report(1, test);
		
		Map<String, String> expected = new LinkedHashMap<>();
		
		expected.put("", "junction_report");
		expected.put("id", "j2");
		expected.put("time", "1");
		expected.put("queues", "(r1,green,[v1])"); //Como solo hay una carretera entrante, su semaforo esta siempre a verde.
		
		assertEquals(expected, test);
		
		j2.avanza();
		j2.report(1, test);
		
		expected.put("queues", "(r1,green,[])");
		
		assertEquals(expected, test);
	}

}
