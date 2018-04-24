package es.ucm.fdi.extra.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.model.Junction;
import es.ucm.fdi.model.Road;
import es.ucm.fdi.model.TrafficSimulator;
import es.ucm.fdi.model.Vehicle;

public class DialogWindowLayout extends JFrame{
	private DialogWindow _dialog;
	private List<Vehicle> _vehicles;
	private List<Road> _roads;
	private List<Junction> _junctions;
	private TrafficSimulator simulator;
	private Ini ini = new Ini();;

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

	private void initGUI() {

		JPanel mainPanel = new JPanel();
		mainPanel.add(new JLabel("If you click "));
		
		_dialog = new DialogWindow(this);
		_dialog.setData(_vehicles, _roads, _junctions);
		
		JButton here = new JButton("here");
		here.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int status = _dialog.open();
				if ( status == 0) {
					System.out.println("Canceled");
				} else {
					simulator.fillReport(_dialog.getSelectedVehicles(), ini);
					simulator.fillReport(_dialog.getSelectedRoads(), ini);
					simulator.fillReport(_dialog.getSelectedJunctions(), ini);
				}
			}
		});
		
		mainPanel.add(here);
		mainPanel.add(new JLabel("a dialog window is opened and the main window blocks."));

		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);

	}
}
