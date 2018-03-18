package es.ucm.fdi.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import es.ucm.fdi.model.*;

public class ReportTest {
	
	@Test
	public void roadAvanzaReportTest() {
		Junction j1 = new Junction("j1");
		Junction j2 = new Junction("j2");
		Road r = new Road("r1", 100, 5, j1, j2);
		List<Junction> it = new ArrayList<>();
		it.add(j1); 
		it.add(j2);
		Vehicle v = new Vehicle("v1" , 5, r, it);
		r.entraVehiculo(v);
		r.avanza();
		
		Map<String, String> rep = new LinkedHashMap<>();
		rep.put("", "road_report");
		rep.put("id", "r1");
		rep.put("time", "1");
		rep.put("state", "(v1,5)");
		
		Map<String, String> test = new LinkedHashMap<>();
		
		r.report(1, test);
		
		assertEquals(rep, test);
	}
	
	@Test
	public void vehicleAvanzaReportTest() {
	
		Junction j1 = new Junction("j1");
		Junction j2 = new Junction("j2");
		List<Junction> it = new ArrayList<>();
		it.add(j1); 
		it.add(j2);
		Road r1 = new Road("r1", 50, 20, j1, j2);
		Junction.IncomingRoads r = new Junction.IncomingRoads(r1);
		j2.addIncomingRoad(r1);
		Vehicle v1 = new Vehicle("v1", 15, r1, it);
		r1.entraVehiculo(v1);
		v1.setVelAct(5);
		r1.avanza(); 
		v1.setVelAct(10);
		r1.avanza();
		
		Map<String, String> rep = new LinkedHashMap<>();
		rep.put("", "vehicle_report");
		rep.put("id", "v1");
		rep.put("time", "1");
		rep.put("speed", "15");
		rep.put("kilometrage", "30");
		rep.put("faulty", "0");
		rep.put("location", "(r1,30)");
		
		Map<String, String> test = new LinkedHashMap<>();
		
		v1.report(1, test);
		
		assertEquals(rep, test);
		
		v1.setTiempoAveria(3);
		r1.avanza();
		r1.avanza();
		
		rep.put("speed", "0");
		rep.put("faulty", "1");
		rep.put("location", "(r1,30)");
		rep.put("kilometrage", "30");
		
		v1.report(1, test);
		
		assertEquals(rep, test);
		
		r1.avanza();
		r1.avanza();
		r1.avanza();
		j2.avanza();
		
		rep.put("speed", "0");
		rep.put("faulty", "0");
		rep.put("location", "arrived");
		rep.put("kilometrage", "50");
		
		v1.report(1, test);
		
		assertEquals(rep, test);
		
	}
}
