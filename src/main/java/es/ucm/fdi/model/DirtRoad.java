package es.ucm.fdi.model;

public class DirtRoad extends Road{

	public DirtRoad(String id, int size, int maxVel, Junction start, Junction end) {
		super(id, size, maxVel, start, end);
	}
	
	@Override
	public void modificarVelBase() {
		
	}
}
