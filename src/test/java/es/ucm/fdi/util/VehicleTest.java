package es.ucm.fdi.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import es.ucm.fdi.model.Junction;
import es.ucm.fdi.model.Road;
import es.ucm.fdi.model.Vehicle;

public class VehicleTest {

	@Test
	public void testAvanza() {
		Junction j1 = new Junction("j1");
		Junction j2 = new Junction("j2");
		List<Junction> it = new ArrayList<>(); it.add(j1); it.add(j2);
		Road r1 = new Road("r1", 50, 20, j1, j2);
		Vehicle v1 = new Vehicle("v1", 15, r1, it);
		
		v1.setVelAct(5); //Establecemos la velocidad actual del vehiculo a su maxima velocidad.
		v1.avanza(); //Avanzamos el vehiculo a una velocidad de 5.
		
		assertEquals(v1.getLocation(), 5); //La posicion del vehiculo debe ser 5
		
		v1.setVelAct(10); //Establecemos la velocidad actual del vehiculo a su maxima velocidad.
		v1.avanza(); //Avanzamos el vehiculo a una velocidad 10.
		
		assertEquals(v1.getKilometrage(), 15); //El kilometraje debe ser el mismo que la localizacion
		//porque no se ha cambiado de carretera
		assertEquals(v1.getLocation(), 15);
	}
	
	@Test
	public void testFaulty() {
		Junction j1 = new Junction("j1");
		Junction j2 = new Junction("j2");
		List<Junction> it = new ArrayList<>(); it.add(j1); it.add(j2);
		Road r1 = new Road("r1", 50, 20, j1, j2);
		Vehicle v1 = new Vehicle("v1", 15, r1, it);
		
		r1.entraVehiculo(v1);
		r1.avanza();
		v1.setTiempoAveria(3);
		r1.avanza();
		r1.avanza();
		
		assertEquals(v1.getLocation(), 15);
		assertEquals(v1.getTiempoAveria(), 1);
		assertEquals(v1.getVelAct(), 0);
	}
	
	@Test
	public void arrivedTest() {
		Junction j1 = new Junction("j1");
		Junction j2 = new Junction("j2");
		List<Junction> it = new ArrayList<>(); 
		it.add(j1); 
		it.add(j2);
		Road r1 = new Road("r1", 30, 20, j1, j2);
		j2.addIncomingRoad(r1);
		Vehicle v1 = new Vehicle("v1", 15, r1, it);
		
		r1.entraVehiculo(v1);
		r1.avanza();
		r1.avanza();
		j2.avanza();
		
		assertEquals(v1.getHaLlegado(), true);
		
	}
	
}
