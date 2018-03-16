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

	@Override
	public void execute(RoadMap r) {
		Junction j = new Junction (id);
		r.addJunction(j);
	}

}
