package es.ucm.fdi.view;

public enum Template {
	NewVehicle("New Vehicle","[new_vehicle]\ntime = \nid = \nmax_speed = \nitinerary = \n"),
	NewBike("New Bike", "[new vehicle]\ntime = \nid = \nmax_speed = \nitinerary = \ntype = \n"),
	NewCar("New Car", "[new vehicle]\ntime = \nid = \nmax_speed = \nitinerary = \ntype = \n"),
	NewRoad("New Road", "[new_road]\ntime = \nid = \nsrc = \ndest = \nmax_speed = \nlength = \n"),
	NewLanes("New Lanes", "[new_road]\ntime = \nid = \nsrc = \ndest = \nmax_speed = \nlength = \ntype =\nlanes = \n"),
	NewDirt("New Dirt", "[new_road]\ntime = \nid = \nsrc = \ndest = \nmax_speed = \nlength = \ntype = \n"),
	NewJunction("New Junction", "[new_junction]\nid = \ntime = \n"),
	NewRoundRobin("New RR Junction", "[new_junction]\nid = \ntime = \ntype = \nmax_time_slice = \nmin_time_slice = \n"),
	NewMostCrowded("New MC Junction", "[new_junction]\nid = \ntime = \ntype = \n"),
	MakeFaulty("Make Vehicle Faulty", "[make_vehicle_faulty]\ntime = \nvehicles = \nduration = \n");
	
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
