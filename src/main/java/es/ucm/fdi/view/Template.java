package es.ucm.fdi.view;

/**
 * Enum that has all the templates we need in this simulation.
 * 
 * @author Daniel Herranz
 *
 */
public enum Template {
	NewVehicle("New Vehicle",
		"\n[new_vehicle]\n"
	    + "time = \n" 
		+ "id = \n" 
	    + "max_speed = \n" 
		+ "itinerary = \n"
	),
	NewBike("New Bike", 
		NewVehicle.getTemp()
		+ "type = bike\n"
	),
	NewCar("New Car", 
		NewVehicle.getTemp()
		+ "type = car\n"
	),
	NewRoad("New Road", 
		"\n[new_road]\n" 
		+ "time = \n" 
		+ "id = \n" 
		+ "src = \n" 
		+ "dest = \n" 
		+ "max_speed = \n" 
		+ "length = \n"
	),
	NewLanes("New Lanes", 
		NewRoad.getTemp() 
		+ "type = lanes\n" 
		+ "lanes = \n"
	),
	NewDirt("New Dirt", 
		NewRoad.getTemp() 
		+ "type = dirt\n"
	),
	NewJunction("New Junction", 
		"\n[new_junction]\n" 
		+ "id = \n" 
		+ "time = \n"
	),
	NewRoundRobin("New RR Junction", 
		NewJunction.getTemp() 
		+ "type = rr\n" 
		+ "max_time_slice = \n" 
		+ "min_time_slice = \n"
	),
	NewMostCrowded("New MC Junction", 
		NewJunction.getTemp() 
		+ "id = \n" 
		+ "time = \n" 
		+ "type = mc\n"
	),
	MakeFaulty("Make Vehicle Faulty",
		"\n[make_vehicle_faulty]\n" 
		+ "time = \n" 
		+ "vehicles = \n" 
		+ "duration = \n"
	);
	
	//Tenemos un texto para mostrar al pulsar "add template" y lo que de verdad
	//se va a introducir en el texto.
	protected String text;
	protected String temp;
	Template (String text, String temp) {
		this.text = text;
		this.temp = temp;
	}
	
	@Override
	public String toString() {
		return text;
	}

	public String getTemp() {
		return temp;
	}
	
}
