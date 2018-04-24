package es.ucm.fdi.view.extra.graph;

import java.util.ArrayList;
import java.util.List;

public class Edge {
	private String _id;
	private Node _source;
	private Node _target;
	private int _length;
	private boolean green;
	private List<Dot> _dots;
	
	public Edge(String id, Node source, Node target, int length, boolean green) {
		_source = source;
		_target = target;
		_id = id;
		_length = length;
		_dots = new ArrayList<>();
		this.green = green;
	}
	
	public void addDot(Dot e) {
		_dots.add(e);
	}
	public String getId() {
		return _id;
	}
	
	public Node getSource() {
		return _source;
	}
	
	public Node getTarget() {
		return _target;
	}

	public int getLength() {
		return _length;
	}
	
	public List<Dot> getDots() {
		return _dots;
	}

	public boolean isGreen() {
		return green;
	}
	
}
