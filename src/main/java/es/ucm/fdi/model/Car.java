package es.ucm.fdi.model;

import java.util.*;

public class Car extends Vehicle {

	private int resistanceKm;
	private double faultProbability;
	private int kilometrageFaulty;
	private int maxFaultDuration;
	private long seed ;
	private Random rand;
	
	public Car(String id, int velMaxima, Road road, List<Junction> itinerario, int resistanceKm, double faultProbability, int maxFaultDuration) {
		super(id, velMaxima, road, itinerario);
		this.resistanceKm = resistanceKm;
		this.faultProbability = faultProbability;
		this.maxFaultDuration = maxFaultDuration;
		this.seed = System.currentTimeMillis();
		kilometrageFaulty = 0;
		rand = new Random(seed);
	}
	
	public Car(String id, int velMaxima, Road road, List<Junction> itinerario, int resistanceKm, double faultProbability, int maxFaultDuration, long seed) {
		super(id, velMaxima, road, itinerario);
		this.resistanceKm = resistanceKm;
		this.faultProbability = faultProbability;
		this.maxFaultDuration = maxFaultDuration;
		this.seed = seed;
		kilometrageFaulty = 0;
		rand = new Random(seed);
	}

	@Override
	public void avanza() {
		double randNum = rand.nextDouble();
		if (getTiempoAveria() == 0 && kilometrageFaulty >= resistanceKm && randNum < faultProbability) {
			setTiempoAveria(rand.nextInt(maxFaultDuration) + 1);
			int kilometrageB = getKilometrage();
			super.avanza();
			int kilometrageA = getKilometrage();
			kilometrageFaulty += (kilometrageA - kilometrageB);
		} else {
			if (getTiempoAveria() > 0) {
				kilometrageFaulty = 0;
				super.avanza();
			}
		}
	}
	
	@Override
	protected void fillReportDetails(Map<String, String> out) {
		super.fillReportDetails(out);
		out.put("type", "car");
	}
}
