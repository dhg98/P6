package es.ucm.fdi.view.extra.dialog;

import java.util.List;

import javax.swing.JFrame;

import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.model.Junction;
import es.ucm.fdi.model.Road;
import es.ucm.fdi.model.TrafficSimulator;
import es.ucm.fdi.model.Vehicle;

public class DialogWindowLayout extends JFrame {
	private DialogWindow _dialog;
	private List<Vehicle> _vehicles;
	private List<Road> _roads;
	private List<Junction> _junctions;
	private TrafficSimulator simulator;
	private Ini ini = new Ini();
	private int status;

	public DialogWindowLayout(List<Vehicle> _vehicles, 
							  List<Road> _roads, 
							  List<Junction> _junctions, 
							  TrafficSimulator simulator) {
		
		this._vehicles = _vehicles;
		this._roads = _roads;
		this._junctions = _junctions;
		this.simulator = simulator;
		initGUI();
	}
	
	public Ini getIni() {
		return ini;
	}

	public int getStatus() {
        return status;
    }

    private void initGUI() {
		_dialog = new DialogWindow(this);
		_dialog.setData(_vehicles, _roads, _junctions);
		status = _dialog.open();
        if (status != 0) {
            simulator.fillReport(_dialog.getSelectedVehicles(), ini);
            simulator.fillReport(_dialog.getSelectedRoads(), ini);
            simulator.fillReport(_dialog.getSelectedJunctions(), ini);
        }
		
        //this.pack();
		
	}
}
