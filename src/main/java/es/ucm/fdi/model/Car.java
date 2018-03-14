package es.ucm.fdi.model;

import java.util.*;

public class Car extends Vehicle {

	private int resistanceKm;
	private double faultProbability;
	private int kilometrageFaulty = 0;
	private int maxFaultDuration;
	private long seed;
	private Random rand;
	
	public Car(String id, int velMaxima, Road road, List<Junction> itinerario, int resistanceKm, double faultProbability, int maxFaultDuration, long seed) {
		super(id, velMaxima, road, itinerario);
		this.resistanceKm = resistanceKm;
		this.faultProbability = faultProbability;
		this.maxFaultDuration = maxFaultDuration;
		if (seed == System.currentTimeMillis()) {
			this.seed = new Random().nextInt(1000);
		} else {
			this.seed = seed;
		}
		this.rand = new Random(this.seed);
	}

	@Override
	public void avanza() {
		if (getTiempoAveria() > 0) {
			kilometrageFaulty = 0;
			super.avanza();
		} else if (getTiempoAveria() == 0 && kilometrageFaulty >= resistanceKm && rand.nextDouble() < faultProbability) {
			setTiempoAveria(rand.nextInt(maxFaultDuration) + 1);
			kilometrageFaulty = 0;
			super.avanza();
		} else {
			int kilometrageB = getKilometrage();
			super.avanza();
			int kilometrageA = getKilometrage();
			kilometrageFaulty += (kilometrageA - kilometrageB);
		}
	}
	
	@Override
	protected void fillReportDetails(Map<String, String> out) {
		out.put("type", "car");
		super.fillReportDetails(out);
	}
}
