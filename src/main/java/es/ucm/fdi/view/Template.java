package es.ucm.fdi.view;

public enum Template {
	NewVehicle("New Vehicle","\n[new_vehicle]\ntime = \nid = \nmax_speed = \nitinerary = \n"),
	NewBike("New Bike", "\n[new vehicle]\ntime = \nid = \nmax_speed = \nitinerary = \ntype = \n"),
	NewCar("New Car", "\n[new vehicle]\ntime = \nid = \nmax_speed = \nitinerary = \ntype = \n"),
	NewRoad("New Road", "\n[new_road]\ntime = \nid = \nsrc = \ndest = \nmax_speed = \nlength = \n"),
	NewLanes("New Lanes", "\n[new_road]\ntime = \nid = \nsrc = \ndest = \nmax_speed = \nlength = \ntype =\nlanes = \n"),
	NewDirt("New Dirt", "\n[new_road]\ntime = \nid = \nsrc = \ndest = \nmax_speed = \nlength = \ntype = \n"),
	NewJunction("New Junction", "\n[new_junction]\nid = \ntime = \n"),
	NewRoundRobin("New RR Junction", "\n[new_junction]\nid = \ntime = \ntype = \nmax_time_slice = \nmin_time_slice = \n"),
	NewMostCrowded("New MC Junction", "\n[new_junction]\nid = \ntime = \ntype = \n"),
	MakeFaulty("Make Vehicle Faulty", "\n[make_vehicle_faulty]\ntime = \nvehicles = \nduration = \n");
	
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
