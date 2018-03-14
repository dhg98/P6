package es.ucm.fdi.model;

import java.util.Comparator;
import java.util.Map;

import es.ucm.fdi.util.MultiTreeMap;

public class LanesRoad extends Road{

	private int lanes;

	public LanesRoad(String id, int size, int maxVel, Junction start, Junction end, int lanes) {
		super(id, size, maxVel, start, end);
		this.lanes = lanes;
	}

	public int getLanes() {
		return lanes;
	}
	
	
	
	@Override
	public int modificarVelBase() {
		return Math.min(getMaxVel(), (getMaxVel() * lanes)/(Math.max(1, getNumVehicles())) + 1);
	}
	
	protected void fillReportDetails(Map<String, String> out) {
		out.put("type", "lanes");
		super.fillReportDetails(out);
	}
	
	@Override
	public void avanza() {
		modificarVelBase();
		MultiTreeMap<Integer, Vehicle> actualizado = new MultiTreeMap<>(Comparator.comparing(Integer::intValue).reversed());
		int  numCochesAveriados = 0;
		int velBase = modificarVelBase();
		for(Vehicle v: getStreet().innerValues()){
			if (v.getTiempoAveria() > 0) {
				numCochesAveriados++;
			} else {
				if (numCochesAveriados < lanes) {
					v.setVelocidadActual(velBase);
				} else {
					v.setVelocidadActual(velBase / 2);
				}
			}
			v.avanza();
			actualizado.putValue(v.getLocation(), v);
		}
		setStreet(actualizado);
	}
}
