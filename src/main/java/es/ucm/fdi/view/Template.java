package es.ucm.fdi.view;

/**
 * Enum that has all the templates we need in this simulation.
 * 
 * @author Daniel Herranz
 *
 */
public enum Template {
	NewVehicle("New Vehicle","\n[new_vehicle]\ntime = \nid = \nmax_speed = \nitinerary = \n"),
	NewBike("New Bike", "\n[new vehicle]\ntime = \nid = \nmax_speed = \nitinerary = \ntype = bike\n"),
	NewCar("New Car", "\n[new vehicle]\ntime = \nid = \nmax_speed = \nitinerary = \ntype = car\n"),
	NewRoad("New Road", "\n[new_road]\ntime = \nid = \nsrc = \ndest = \nmax_speed = \nlength = \n"),
	NewLanes("New Lanes", "\n[new_road]\ntime = \nid = \nsrc = \ndest = \nmax_speed = \nlength = \ntype = lanes\nlanes = \n"),
	NewDirt("New Dirt", "\n[new_road]\ntime = \nid = \nsrc = \ndest = \nmax_speed = \nlength = \ntype = dirt\n"),
	NewJunction("New Junction", "\n[new_junction]\nid = \ntime = \n"),
	NewRoundRobin("New RR Junction", "\n[new_junction]\nid = \ntime = \ntype = rr\nmax_time_slice = \nmin_time_slice = \n"),
	NewMostCrowded("New MC Junction", "\n[new_junction]\nid = \ntime = \ntype = mc\n"),
	MakeFaulty("Make Vehicle Faulty", "\n[make_vehicle_faulty]\ntime = \nvehicles = \nduration = \n");
	
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
