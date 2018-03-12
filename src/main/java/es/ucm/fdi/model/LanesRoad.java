package es.ucm.fdi.model;

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
	public void modificarVelBase() {
		setVelBase();
	}
}
