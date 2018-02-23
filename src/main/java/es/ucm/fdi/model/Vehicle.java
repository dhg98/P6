package es.ucm.fdi.model;

public class Vehicle extends SimObject {

	private int velMax;
	private int velAct;
	private Road road;
	private int location;
	private Junction[] itinerario; 
	private int tiempoAveria;
	private boolean haLlegado;
	
	public Vehicle(int velMaxima, int velActual, Road road, int location, int averia, Junction[] itinerario){
		this.velMax = velMaxima;
		this.velAct = velActual;
		this.location = location;
		this.tiempoAveria = averia;
		this.itinerario = itinerario;
		this.haLlegado = itinerario.length == 0;
		this.road = road;
	}
	
	public void setTiempoAveria(int n){
		tiempoAveria += n;
	}
	
	

	public void setVelocidadActual(int velA) {
		if(velA >= velMax){
			velAct = velMax;
		} else {
			velAct = velA;
		}
	}
	
	public String generaInforme(int time){
		//public IniSection writeReport() 
		//protected abstract void prepareReport(Map<String, String> report)
		//return "[vehicle_report]\nid = " + identificador + "\ntime = " + time + "\nspeed = " + velAct + ;
	}
	
	public void avanza(int longRoad) {
		if(tiempoAveria > 0) {
			tiempoAveria--;
		} else{
			int locationAux = location + velAct;
			if(locationAux >= longRoad) {
				location = longRoad;
			} else {
				
			}
		}
		
	}
}
