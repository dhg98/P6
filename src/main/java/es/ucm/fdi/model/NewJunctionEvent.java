package es.ucm.fdi.model;

public class NewJunctionEvent extends Event {
	private String id;
	
	public NewJunctionEvent(int time, String id) {
		super(time);
		this.id = id;
	}

	public String getId() {
		return id;
	}

	/**
	 * Executes a NewJunctionEvent and adds the NewBike to the RoadMap
	 */
	@Override
	public void execute(RoadMap r) {
		Junction j = new Junction (id);
		r.addJunction(j);
	}

	@Override
	public String getType() {
		return "New Junction " + id;
	}
}