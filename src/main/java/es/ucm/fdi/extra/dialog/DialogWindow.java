package es.ucm.fdi.extra.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import es.ucm.fdi.model.Junction;
import es.ucm.fdi.model.Road;
import es.ucm.fdi.model.Vehicle;

class DialogWindow extends JDialog {

	private static final long serialVersionUID = 1L;

	private MyListModel<Vehicle> _vehicleListModel;
	private MyListModel<Road> _roadListModel;
	private MyListModel<Junction> _junctionListModel;

	private int _status;
	private JList<Vehicle> _vehicleList;
	private JList<Road> _roadList;
	private JList<Junction> _junctionList;

	static final private char _clearSelectionKey = 'c';
	private Border _defaultBorder = BorderFactory.createLineBorder(Color.black, 2);

	public DialogWindow(Frame parent) {
		super(parent, true);
		initGUI();
	}

	private void initGUI() {

		_status = 0;

		setTitle("Some Dialog");
		JPanel mainPanel = new JPanel(new BorderLayout());

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		mainPanel.add(contentPanel, BorderLayout.CENTER);

		JPanel vehiclePanel = new JPanel(new BorderLayout());
		JPanel roadPanel = new JPanel(new BorderLayout());
		JPanel junctionPanel = new JPanel(new BorderLayout());

		contentPanel.add(vehiclePanel);
		contentPanel.add(roadPanel);
		contentPanel.add(junctionPanel);

		vehiclePanel.setBorder(
				BorderFactory.createTitledBorder(_defaultBorder, "Vehicles", TitledBorder.LEFT, TitledBorder.TOP));
		roadPanel.setBorder(
				BorderFactory.createTitledBorder(_defaultBorder, "Road", TitledBorder.LEFT, TitledBorder.TOP));
		junctionPanel.setBorder(
				BorderFactory.createTitledBorder(_defaultBorder, "Junction", TitledBorder.LEFT, TitledBorder.TOP));

		vehiclePanel.setMinimumSize(new Dimension(100, 100));
		roadPanel.setMinimumSize(new Dimension(100, 100));
		junctionPanel.setMinimumSize(new Dimension(100, 100));

		_vehicleListModel = new MyListModel<>();
		_roadListModel = new MyListModel<>();
		_junctionListModel = new MyListModel<>();

		_vehicleList = new JList<>(_vehicleListModel);
		_roadList = new JList<>(_roadListModel);
		_junctionList = new JList<>(_junctionListModel);

		addCleanSelectionListner(_vehicleList);
		addCleanSelectionListner(_roadList);
		addCleanSelectionListner(_junctionList);

		vehiclePanel.add(new JScrollPane(_vehicleList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);

		roadPanel.add(new JScrollPane(_roadList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
		
		junctionPanel.add(new JScrollPane(_junctionList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);


		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 0;
				DialogWindow.this.setVisible(false);
			}
		});
		buttonsPanel.add(cancelButton);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 1;
				DialogWindow.this.setVisible(false);
			}
		});
		buttonsPanel.add(okButton);

		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		mainPanel.add(infoPanel, BorderLayout.PAGE_START);

		infoPanel.add(new JLabel("Select items for which you want to process."));
		infoPanel.add(new JLabel("Use '" + _clearSelectionKey + "' to deselect all."));
		infoPanel.add(new JLabel("Use Ctrl+A to select all"));
		infoPanel.add(new JLabel(" "));

		setContentPane(mainPanel);
		setMinimumSize(new Dimension(100, 100));
		setVisible(false);
	}

	private void addCleanSelectionListner(JList<?> list) {
		list.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == _clearSelectionKey) {
					list.clearSelection();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

	}

	public void setData(List<Vehicle> vehicles, List<Road> roads, List<Junction> junctions) {
		_vehicleListModel.setList(vehicles);
		_roadListModel.setList(roads);
		_junctionListModel.setList(junctions);
	}

	public List<Vehicle> getSelectedVehicles() {
		int[] indices = _vehicleList.getSelectedIndices();
		List<Vehicle> vehicle = new ArrayList<>();
		for(int i=0; i<vehicle.size(); i++) {
			vehicle.add(_vehicleListModel.getElementAt(indices[i]));
		}
		return vehicle;
	}

	public List<Road> getSelectedRoads() {
		int[] indices = _roadList.getSelectedIndices();
		List<Road> road = new ArrayList<>();
		for(int i=0; i<road.size(); i++) {
			road.add(_roadListModel.getElementAt(indices[i]));
		}
		return road;
	}
	
	public List<Junction> getSelectedJunctions() {
		int[] indices = _vehicleList.getSelectedIndices();
		List<Junction> junctions = new ArrayList<>();
		for(int i=0; i<junctions.size(); i++) {
			junctions.add(_junctionListModel.getElementAt(indices[i]));
		}
		return junctions;
	}


	public int open() {
		setLocation(getParent().getLocation().x + 50, getParent().getLocation().y + 50);
		pack();
		setVisible(true);
		return _status;
	}
}
