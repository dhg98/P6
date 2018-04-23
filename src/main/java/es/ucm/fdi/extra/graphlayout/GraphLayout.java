package es.ucm.fdi.extra.graphlayout;

import java.util.HashMap;
import java.util.Map;

import es.ucm.fdi.model.Junction;
import es.ucm.fdi.model.Road;
import es.ucm.fdi.model.RoadMap;
import es.ucm.fdi.model.Vehicle;

public class GraphLayout {
	public GraphComponent _graphComp;
	private RoadMap rm;
	
	public GraphLayout(RoadMap rm) {
		super();
		this._graphComp = new GraphComponent();
		this.rm = rm;
	}

	public void generateGraph() {
		Graph g = new Graph();
		Map<Junction, Node> js = new HashMap<>();
		for (Junction j : rm.getJunctions()) {
			Node n = new Node(j.getId());
			js.put(j, n); // <-- para convertir Junction a Node en aristas
			g.addNode(n);
		}
		for (Road r : rm.getRoads()) {
			Edge e = new Edge(r.getId(), js.get(r.getStart()), js.get(r.getEnd()), r.getSize());
			
			for(Vehicle v : r.getStreet().innerValues()) {
				e.addDot(new Dot(v.getId(), v.getLocation()));
			}
			g.addEdge(e);
		}
	}
	
	
}
