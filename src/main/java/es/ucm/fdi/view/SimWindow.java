package es.ucm.fdi.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import es.ucm.fdi.control.Controller;
import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.model.RoadMap;
import es.ucm.fdi.model.TrafficSimulator.Listener;
import es.ucm.fdi.model.events.Event;
import es.ucm.fdi.util.MultiTreeMap;
import es.ucm.fdi.view.extra.dialog.DialogWindowLayout;
import es.ucm.fdi.view.extra.graph.GraphLayout;
import es.ucm.fdi.view.extra.popupmenu.PopUpMenu;

public class SimWindow extends JFrame implements Listener {
	private final static String[] columnNameVehicle = new String[] { "ID", "Road", "Location", "Speed", "Km",
			"Faulty Units", "Itinerary" };
	private final static String[] columnNameRoad = new String[] { "ID", "Source", "Target", "Lenght", "Max Speed",
			"Vehicles" };
	private final static String[] columnNameJunction = new String[] { "ID", "Green", "Red" };
	private final static String[] columnNameEvents = new String[] { "#", "Time", "Type" };

	private Controller ctrl;
	private RoadMap map = new RoadMap();
	private List<Event> events = new ArrayList<>();
	private File currentInput;

	private TableOfDescribables vehiclesTable;
	private TableOfDescribables roadTable;
	private TableOfDescribables junctionTable;
	private TableOfDescribables eventsTable;

	private GraphLayout graph;
	private PopUpMenu textSection = new PopUpMenu();
	private JTextArea reportsArea = new JTextArea();
	private JSplitPane bottomSplit;
	private JSplitPane topSplit;
	private JPanel eventEditor;
	private JPanel reportsViewer;
	private JPanel supPanel;
	private JPanel infPanel;
	private JPanel infLeftPanel;
	private JPanel rightInfPanel;
	private JLabel information;

	// new SpinnerNumberModel(CurrentValue, min, max, steps)
	private static JSpinner stepsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
	private JTextField timeViewer = new JTextField("0");
	private Map<Command, SimulatorAction> actionsCommand = new HashMap<>();

	public SimWindow(Controller ctrl, String inFileName) {
		super("Traffic Simulator");
		createActionsCommand();
		if (inFileName != null) {
			currentInput = new File(inFileName);
			String st = "";
			try {
				st = new String(Files.readAllBytes(currentInput.toPath()), "UTF-8");
			} catch (Exception e) {
				ctrl.getSimulator().notifyError("There has been an error loading the file.");
			}
			textSection.get_editor().setText(st);
		} else {
			ableActions(false, actionsCommand.get(Command.Events), actionsCommand.get(Command.Clear),
					actionsCommand.get(Command.Save));
		}
		ableActions(false, actionsCommand.get(Command.SaveReport), actionsCommand.get(Command.Play),
				actionsCommand.get(Command.Reset), actionsCommand.get(Command.Report),
				actionsCommand.get(Command.DeleteReport));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.ctrl = ctrl;
		map = ctrl.getSimulator().getRm();
		addToolBar();
		addGraph();
		addMenuBar();
		addEventEditor();
		addEventsView();
		addReportsViewer();
		addSupPanel();
		addVehiclesTablePanel();
		addRoadsTablePanel();
		addJunctionsTablePanel();
		addInfLeftPanel();
		addInfRightPanel();
		addInfPanel();
		addInformation();
		addBars();
		ctrl.getSimulator().addSimulatorListener(this);
		setSize(1000, 1000);
		setVisible(true);
		topSplit.setDividerLocation(.33);
		bottomSplit.setDividerLocation(.5);
	}

	private void ableActions(boolean state, SimulatorAction... actions) {
		for (SimulatorAction ac : actions) {
			ac.setEnabled(state);
		}
	}

	private void createActionsCommand() {
		// instantiate actions
		SimulatorAction exit = new SimulatorAction(Command.Exit, "exit.png", "Exit the aplication", 
				KeyEvent.VK_C, "control shift C", () -> System.exit(0));

		SimulatorAction saveEvent = new SimulatorAction(Command.Save, "save.png", "Save an Event", 
				KeyEvent.VK_S, "control S", () -> {
					boolean couldSave = saveIni(textSection.get_editor());
					enableOrDisableActions(actionsCommand, Command.Save);
					if (couldSave) {
						information.setText(Command.Save.message);
					}
				});

		SimulatorAction play = new SimulatorAction(Command.Play, "play.png", "Play the simulation", 
				KeyEvent.VK_P, "control P", () -> {
					play();
					enableOrDisableActions(actionsCommand, Command.Play);
					information.setText(Command.Play.message);
				});

		SimulatorAction open = new SimulatorAction(Command.Open, "open.png", "Load an ini file", 
				KeyEvent.VK_L, "control L", () -> {
					boolean couldOpen = readIni();
					if (couldOpen) {
						ctrl.getSimulator().setTimeCounter(0);
						timeViewer.setText("0");
						enableOrDisableActions(actionsCommand, Command.Open);
						information.setText(Command.Open.message);
						eventEditor.setBorder(javax.swing.BorderFactory
								.createTitledBorder(" Events Editor: " + currentInput.getName()));
					} else {
						eventEditor.setBorder(javax.swing.BorderFactory.createTitledBorder(" Events Editor: "));
					}
				});

		SimulatorAction saveReport = new SimulatorAction(Command.SaveReport, "save_report.png", 
				"Save a report", KeyEvent.VK_R, "control R", () -> {
					boolean couldSave = saveIni(reportsArea);
					if (couldSave) {
						information.setText(Command.SaveReport.message);
					}
				});

		SimulatorAction clear = new SimulatorAction(Command.Clear, "clear.png", "Clear the text", 
				KeyEvent.VK_X, "control X", () -> {
					clear(textSection.get_editor());
					enableOrDisableActions(actionsCommand, Command.Clear);
					information.setText(Command.Clear.message);
				});

		SimulatorAction events = new SimulatorAction(Command.Events, "events.png", "Add events to the simulation",
				KeyEvent.VK_A, "control A", () -> {
					try {
						readText();
						reset(0, new RoadMap(), new ArrayList<Event>());
						ctrl.loadEvents();
						enableOrDisableActions(actionsCommand, Command.Events);
						information.setText(Command.Events.message);
					} catch (IOException e) {
						ctrl.getSimulator().notifyError(e.getMessage());
					}
				});

		SimulatorAction deleteReport = new SimulatorAction(Command.DeleteReport, "delete_report.png", 
				"Delete a report", KeyEvent.VK_B, "control B", () -> {
					clear(reportsArea);
					enableOrDisableActions(actionsCommand, Command.DeleteReport);
					information.setText(Command.DeleteReport.message);
				});

		SimulatorAction report = new SimulatorAction(Command.Report, "report.png", "Report the simulation",
				KeyEvent.VK_M, "control M", () -> {
					DialogWindowLayout selection = new DialogWindowLayout(map.getVehiclesRO(), map.getRoadsRO(),
							map.getJunctionsRO(), ctrl.getSimulator());
					Ini in = selection.getIni();
					reportsArea.setText(in.toString());
					if (selection.getStatus() != 0) {
						enableOrDisableActions(actionsCommand, Command.Report);
						information.setText(Command.Report.message);
					}
				});

		SimulatorAction reset = new SimulatorAction(Command.Reset, "reset.png", "Reset the simulation", 
				KeyEvent.VK_Z, "control Z", () -> {
					ctrl.getSimulator().reset();
					enableOrDisableActions(actionsCommand, Command.Reset);
					information.setText(Command.Reset.message);
				});

		actionsCommand.put(Command.Exit, exit);
		actionsCommand.put(Command.Clear, clear);
		actionsCommand.put(Command.Open, open);
		actionsCommand.put(Command.SaveReport, saveReport);
		actionsCommand.put(Command.Save, saveEvent);
		actionsCommand.put(Command.Events, events);
		actionsCommand.put(Command.DeleteReport, deleteReport);
		actionsCommand.put(Command.Play, play);
		actionsCommand.put(Command.Report, report);
		actionsCommand.put(Command.Reset, reset);
	}

	private void enableOrDisableActions(Map<Command, SimulatorAction> actions, Command command) {
		switch (command) {
		case Clear: {
			actions.get(Command.Save).setEnabled(false);
			actions.get(Command.Events).setEnabled(false);
			actions.get(Command.Clear).setEnabled(true);
		}
			break;
		case Open: {
			actions.get(Command.Clear).setEnabled(true);
			actions.get(Command.Events).setEnabled(true);
			actions.get(Command.Save).setEnabled(true);
			actions.get(Command.Report).setEnabled(false);
			actions.get(Command.SaveReport).setEnabled(false);
			actions.get(Command.DeleteReport).setEnabled(false);
			actions.get(Command.Play).setEnabled(false);
			actions.get(Command.Reset).setEnabled(false);

		}
			break;
		case Events: {
			actions.get(Command.Play).setEnabled(true);
			actions.get(Command.Reset).setEnabled(true);
		}
			break;
		case DeleteReport: {
			actions.get(Command.Report).setEnabled(true);
			actions.get(Command.SaveReport).setEnabled(false);
		}
			break;
		case Play: {
			actions.get(Command.Events).setEnabled(false);
			actions.get(Command.Report).setEnabled(true);
		}
			break;
		case Report: {
			actions.get(Command.DeleteReport).setEnabled(true);
			actions.get(Command.SaveReport).setEnabled(true);
		}
			break;
		case Reset: {
			actions.get(Command.Report).setEnabled(false);
			actions.get(Command.SaveReport).setEnabled(false);
			actions.get(Command.DeleteReport).setEnabled(false);
			actions.get(Command.Play).setEnabled(false);
			actions.get(Command.Save).setEnabled(false);
			actions.get(Command.Reset).setEnabled(false);
			actions.get(Command.Events).setEnabled(true);
		}
			break;
		default:
			break;
		}
	}

	public boolean readIni() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Ini files", "ini");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(chooser.getParent());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			currentInput = chooser.getSelectedFile();
			System.out.println("You chose to open this file: " + currentInput.getName());
			try {
				String st = new String(Files.readAllBytes(currentInput.toPath()), "UTF-8");
				ctrl.setIn(new FileInputStream(currentInput));
				textSection.get_editor().setText(st);
			} catch (IOException e) {
				textSection.get_editor().setText("");
			}
			return true;
		} else {
			return false;
		}
	}

	public boolean saveIni(JTextArea text) {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Ini files", "ini");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showSaveDialog(chooser.getParent());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File f = chooser.getSelectedFile();
			System.out.println("You chose to open this file: " + f.getName());
			try {
				String s = text.getText();
				Files.write(f.toPath(), s.getBytes("UTF-8"));

			} catch (IOException e) {

			}
			return true;
		} else {
			return false;
		}
	}

	public void readText() {
		String st = textSection.get_editor().getText();
		ctrl.setIn(new ByteArrayInputStream(st.getBytes(StandardCharsets.UTF_8)));
	}

	public void clear(JTextArea text) {
		text.setText("");
	}

	/*
	 * public void showReport() { Ini ini = new Ini();
	 * ctrl.getSimulator().fillReport(map.getJunctionsRO(), ini);
	 * ctrl.getSimulator().fillReport(map.getRoadsRO(), ini);
	 * ctrl.getSimulator().fillReport(map.getVehiclesRO(), ini);
	 * reportsArea.setText(ini.toString()); }
	 */

	private void play() {
		int time = (Integer) stepsSpinner.getValue();
		ByteArrayOutputStream str = new ByteArrayOutputStream();
		try {
			ctrl.getSimulator().execute(str, time);
			timeViewer.setText("" + ctrl.getSimulator().getTimeCounter());
		} catch (IOException e) {
			ctrl.getSimulator().notifyError("Something went wrong with the simulation.");
			e.printStackTrace();
		}
	}

	private void addToolBar() {
		// add actions to toolbar,
		JToolBar bar = new JToolBar();
		bar.add(actionsCommand.get(Command.Open));
		bar.add(actionsCommand.get(Command.Save));
		bar.add(actionsCommand.get(Command.Clear));

		bar.addSeparator();

		bar.add(actionsCommand.get(Command.Events));
		bar.add(actionsCommand.get(Command.Play));
		bar.add(actionsCommand.get(Command.Reset));

		// Steps y time...
		JLabel stepsLabel = new JLabel(" Steps: "), timeLabel = new JLabel(" Time: ");
		bar.add(stepsLabel);
		bar.add(stepsSpinner);
		timeViewer.setMinimumSize(new Dimension(70, 1));
		timeViewer.setEditable(false);
		bar.add(timeLabel);
		bar.add(timeViewer);

		bar.addSeparator();

		bar.add(actionsCommand.get(Command.Report));
		bar.add(actionsCommand.get(Command.DeleteReport));
		bar.add(actionsCommand.get(Command.SaveReport));

		bar.addSeparator();

		bar.add(actionsCommand.get(Command.Exit));

		JPanel jp = new JPanel();
		jp.setPreferredSize(new Dimension(100000, 1));
		bar.add(jp);

		// Add bar to window
		add(bar, BorderLayout.NORTH);
	}

	private void addMenuBar() {
		JMenuBar menu = new JMenuBar();
		JMenu file = new JMenu("File");

		file.add(actionsCommand.get(Command.Open));
		file.add(actionsCommand.get(Command.Save));

		file.addSeparator();

		file.add(actionsCommand.get(Command.SaveReport));

		file.addSeparator();

		file.add(actionsCommand.get(Command.Exit));

		JMenu simulator = new JMenu("Simulator");
		simulator.add(actionsCommand.get(Command.Play));
		simulator.add(actionsCommand.get(Command.Reset));

		JMenu reports = new JMenu("Reports");
		reports.add(actionsCommand.get(Command.Report));
		reports.add(actionsCommand.get(Command.Clear));

		menu.add(file);
		menu.add(simulator);
		menu.add(reports);

		// Add menu to window
		setJMenuBar(menu);
	}

	private void addSupPanel() {
		supPanel = new JPanel();
		supPanel.setLayout(new GridLayout(1, 3));
		supPanel.add(eventEditor);
		supPanel.add(eventsTable);
		supPanel.add(reportsViewer);
	}

	private void addEventEditor() {
		JScrollPane iniInput = new JScrollPane(textSection.get_editor());
		eventEditor = new JPanel(new BorderLayout());
		if (currentInput != null) {
			eventEditor.setBorder(
					javax.swing.BorderFactory.createTitledBorder(" Events Editor: " + currentInput.getName()));
		} else {
			eventEditor.setBorder(javax.swing.BorderFactory.createTitledBorder(" Events Editor: "));
		}
		eventEditor.add(iniInput);
	}

	private void addEventsView() {
		eventsTable = new TableOfDescribables(events, columnNameEvents);
		eventsTable.setBorder(javax.swing.BorderFactory.createTitledBorder(" Events Queue "));
	}

	private void addReportsViewer() {
		reportsArea.setEditable(false);
		JScrollPane reportsAreaScroll = new JScrollPane(reportsArea);
		reportsViewer = new JPanel(new BorderLayout());
		reportsViewer.setBorder(javax.swing.BorderFactory.createTitledBorder(" Reports Area "));
		reportsViewer.add(reportsAreaScroll);
	}

	private void addVehiclesTablePanel() {
		vehiclesTable = new TableOfDescribables(map.getVehiclesRO(), columnNameVehicle);
		vehiclesTable.setBorder(javax.swing.BorderFactory.createTitledBorder(" Vehicles Table "));
	}

	private void addRoadsTablePanel() {
		roadTable = new TableOfDescribables(map.getRoadsRO(), columnNameRoad);
		roadTable.setBorder(javax.swing.BorderFactory.createTitledBorder(" Road Table "));
	}

	private void addJunctionsTablePanel() {
		junctionTable = new TableOfDescribables(map.getJunctionsRO(), columnNameJunction);
		junctionTable.setBorder(javax.swing.BorderFactory.createTitledBorder(" Junction Table "));
	}

	private void addInfLeftPanel() {
		infLeftPanel = new JPanel();
		infLeftPanel.setLayout(new BoxLayout(infLeftPanel, BoxLayout.Y_AXIS));
		infLeftPanel.add(vehiclesTable);
		infLeftPanel.add(roadTable);
		infLeftPanel.add(junctionTable);
	}

	private void addInfRightPanel() {
		rightInfPanel = new JPanel();
		rightInfPanel.setLayout(new BorderLayout());
		rightInfPanel.add(graph.get_graphComp());
	}

	private void addGraph() {
		graph = new GraphLayout(map);
		graph.generateGraph();
	}

	private void addInformation() {
		information = new JLabel("Welcome to the best Traffic Simulator ever! :)");
		add(information, BorderLayout.AFTER_LAST_LINE);
	}

	private void addInfPanel() {
		infPanel = new JPanel();
		infPanel.setLayout(new BorderLayout());
		bottomSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, infLeftPanel, rightInfPanel);
		bottomSplit.setResizeWeight(.5);
		infPanel.add(bottomSplit);
	}

	private void addBars() {
		topSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, supPanel, infPanel);
		topSplit.setResizeWeight(.33);
		add(topSplit);
	}

	@Override
	public void registered(int time, RoadMap map, List<Event> events) {
	}

	@Override
	public void reset(int time, RoadMap map, List<Event> events) {
		reportsArea.setText("");
		timeViewer.setText("" + 0);
		this.events = events;
		this.map = map;
		ctrl.getSimulator().setRm(map);
		ctrl.getSimulator().setEvents(new MultiTreeMap<>());

		junctionTable.setElements(map.getJunctionsRO());
		roadTable.setElements(map.getRoadsRO());
		vehiclesTable.setElements(map.getVehiclesRO());
		junctionTable.update();
		roadTable.update();
		vehiclesTable.update();

		eventsTable.setElements(events);
		eventsTable.update();

		graph.setRm(map);
		graph.generateGraph();
	}

	@Override
	public void eventAdded(int time, RoadMap map, List<Event> events) {
		this.events = events;
		eventsTable.setElements(events);
		eventsTable.update();
		graph.setRm(map);
		graph.generateGraph();
	}

	@Override
	public void advanced(int time, RoadMap map, List<Event> events) {
		junctionTable.setElements(map.getJunctionsRO());
		roadTable.setElements(map.getRoadsRO());
		vehiclesTable.setElements(map.getVehiclesRO());
		junctionTable.update();
		roadTable.update();
		vehiclesTable.update();
		graph.setRm(map);
		graph.generateGraph();
	}

	@Override
	public void simulatorError(int time, RoadMap map, List<Event> events, String error) {
		JOptionPane.showMessageDialog(this, error);
		ctrl.getSimulator().reset();
	}
}