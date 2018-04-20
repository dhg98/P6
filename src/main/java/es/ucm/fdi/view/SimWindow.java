package es.ucm.fdi.view;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import es.ucm.fdi.model.Event;
import es.ucm.fdi.model.RoadMap;
import es.ucm.fdi.model.TrafficSimulator.Listener;

/**
 * Esto es sólo para empezar a jugar con las interfaces
 * de la P5. 
 * 
 * El código <i>no</i> está bien organizado, y meter toda
 * la funcionalidad aquí sería un disparate desde un punto
 * de vista de mantenibilidad.
 */

public class SimWindow extends JFrame implements Listener {
	private final static String[] columnNameVehicle = new String[]  {"ID", "Road", "Location", "Speed", "Km", "Faulty Units", "Itinerary"};
	private final static String[] columnNameRoad = new String[] {"ID", "Source", "Target", "Lenght", "Max Speed", "Vehicles"};
	private final static String[] columnNameJunction = new String[] {"ID", "Green", "Red"};
	private final static String[] columnNameEvents = new String[] {"#", "Time", "Type"};
	private static boolean loadedEvents = false;
	
	
	private Controller ctrl;
	private RoadMap map = new RoadMap();
	private int time;
	private List<Event> events = new ArrayList<>();
	private OutputStream reportsOutputStream;
	private File currentInput;
	
	private TableOfDescribables vehiclesTable;
	private ListOfMapsTableModel vehiclesTableModel;
	private TableOfDescribables roadTable;
	private ListOfMapsTableModel roadTableModel;
	private TableOfDescribables junctionTable;
	private ListOfMapsTableModel junctionTableModel;
	private TableOfDescribables eventsTable;
	private ListOfMapsTableModel eventsTableModel;
	
	private TextSection textSection;
	private JPanel eventEditor;
	private JTextArea reportsArea = new JTextArea();
	private JPanel reportsViewer;
	private JPanel supPanel;
	private JPanel infPanel;
	private JPanel infLeftPanel;
	private JSpinner stepsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1)); //new SpinnerNumberModel(CurrentValue, min, max, steps)
	private JTextField timeViewer = new JTextField("1");
	private Map<Object, SimulatorAction> actions = new HashMap<>();
	
	public SimWindow(Controller ctrl, String inFileName) {
		super("Traffic Simulator");
		textSection = new TextSection("");
		if (inFileName != null) {
			currentInput = new File(inFileName);
			String st = "";
			try {
				st = new String(Files.readAllBytes(currentInput.toPath()), "UTF-8");
			} catch (Exception e) {
				
			}
	    	textSection.textArea.setText(st);
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.ctrl = ctrl;
		map = ctrl.getSimulator().getRm();
		createActions();
		addToolBar();
		addMenuBar();
		addEventEditor();
		addEventsView();
		addReportsViewer();
		addSupPanel();
		addVehiclesTablePanel();
		addRoadsTablePanel();
		addJunctionsTablePanel();
		addInfLeftPanel();
		addInfPanel();
		addBars();
		ctrl.getSimulator().addSimulatorListener(this);
		setSize(1000, 1000);		
		setVisible(true);
	}
	
	public void readIni() {
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "Ini files", "ini");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(chooser.getParent());
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       currentInput = chooser.getSelectedFile();
	       System.out.println("You chose to open this file: " +
	            currentInput.getName());
	       try {
	    	   String st = new String(Files.readAllBytes(currentInput.toPath()), "UTF-8");
	    	   ctrl.setIn(new FileInputStream(currentInput));
	    	   textSection.textArea.setText(st);
	       } catch (IOException e) {
	    	   textSection.textArea.setText("");
	       }
	    }		
	}
	
	public void saveIni(JTextArea text) {
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "Ini files", "ini");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showSaveDialog(chooser.getParent());
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	File f = chooser.getSelectedFile();
	    	System.out.println("You chose to open this file: " +
	           f.getName());
	    	try {
	    		String s = text.getText();
	    		Files.write(f.toPath(), s.getBytes("UTF-8"));
	    		
	    	} catch (IOException e) {
	    		
	    	}	   
	    }
	}
	
	public void readText() {
		String st = textSection.textArea.getText();
		ctrl.setIn(new ByteArrayInputStream(st.getBytes(StandardCharsets.UTF_8)));
	}
	
	public void clearText() {
		textSection.textArea.setText("");
	}
	
	public void showReport() {
		Ini ini = new Ini();
		ctrl.getSimulator().fillReport(map.getJunctionsRO(), ini);
		ctrl.getSimulator().fillReport(map.getRoadsRO(), ini);
		ctrl.getSimulator().fillReport(map.getVehiclesRO(), ini);
		reportsArea.setText(ini.toString());
	}
	
	private void createActions() {
		// instantiate actions
		
		SimulatorAction exit = new SimulatorAction(
				Command.Exit, "exit.png", "Exit the aplication",
				KeyEvent.VK_C, "control shift C", 
				()-> System.exit(0));
		
		SimulatorAction saveEvent = new SimulatorAction(
				Command.Save, "save.png", "Save an Event",
				KeyEvent.VK_S, "control S", 
				()-> saveIni(textSection.textArea));
		
		SimulatorAction open = new SimulatorAction(
				Command.Open, "open.png", "Load an ini file", 
				KeyEvent.VK_L, "control L", 
				()->{
					readIni();
					loadedEvents = false;
				});
		
		SimulatorAction saveReport = new SimulatorAction(
				Command.SaveReport, "save_report.png", "Save a report", 
				KeyEvent.VK_R, "control R", 
				()-> saveIni(reportsArea));
		
		SimulatorAction clear = new SimulatorAction(
				Command.Clear, "clear.png", "Clear the text",
				KeyEvent.VK_X, "control X",
				()->clearText());
		
		SimulatorAction play = new SimulatorAction(
				Command.Play, "play.png", "Play the simulation",
				KeyEvent.VK_P, "control P",
				()->play());
		
		SimulatorAction events = new SimulatorAction(
				Command.Events, "events.png", "Add events to the simulation",
				KeyEvent.VK_A, "control A",
				()->{
					try {
						readText();
						ctrl.loadEvents();
						loadedEvents = true;
					} catch (IOException e) {
						ctrl.getSimulator().notifyError(e.getMessage());
					}
				});
		
		SimulatorAction deleteReport = new SimulatorAction(
				Command.DeleteReport, "delete_report.png", "Delete a report",
				KeyEvent.VK_B, "control B",
				()->System.out.println(""));
		
		SimulatorAction stop = new SimulatorAction(
				Command.Stop, "stop.png", "Stop the simulation",
				KeyEvent.VK_K, "control K",
				()->System.out.println(""));
		
		SimulatorAction report = new SimulatorAction(
				Command.Report, "report.png", "Report the simulation",
				KeyEvent.VK_M, "control M",
				()-> showReport());
		
		SimulatorAction reset = new SimulatorAction(
				Command.Reset, "reset.png", "Reset the simulation",
				KeyEvent.VK_Z, "control Z",
				()->ctrl.getSimulator().reset());
		
		actions.put(Command.Exit, exit);
		actions.put(Command.Clear, clear);
		actions.put(Command.Open, open);
		actions.put(Command.SaveReport, saveReport);
		actions.put(Command.Save, saveEvent);
		actions.put(Command.Stop, stop);
		actions.put(Command.Events, events);
		actions.put(Command.DeleteReport, deleteReport);
		actions.put(Command.Play, play);
		actions.put(Command.Report, report);
		actions.put(Command.Reset, reset);
	}
	
	private void addToolBar() {
		// add actions to toolbar, 
		
		JToolBar bar = new JToolBar();
		bar.add(actions.get(Command.Open));
		bar.add(actions.get(Command.Save));
		bar.add(actions.get(Command.Clear));
		
		bar.addSeparator();
		
		bar.add(actions.get(Command.Events));
		bar.add(actions.get(Command.Play));
		bar.add(actions.get(Command.Reset));
		
		//Steps y time...
		JLabel stepsLabel = new JLabel(" Steps: "), timeLabel = new JLabel(" Time: ");
		bar.add(stepsLabel);
		bar.add(stepsSpinner);
		
		//timeViewer.setSize(2, 2);
		timeViewer.setEditable(false);
		bar.add(timeLabel);
		bar.add(timeViewer);
		
		bar.add(actions.get(Command.Report));
		bar.add(actions.get(Command.DeleteReport));
		bar.add(actions.get(Command.SaveReport));
		
		bar.addSeparator();
		
		bar.add(actions.get(Command.Exit));	
		
		//Add bar to window
		add(bar, BorderLayout.NORTH);
	}
	
	private void addMenuBar() {
		JMenuBar menu = new JMenuBar();
		
		JMenu file = new JMenu("File");
	
		file.add(actions.get(Command.Open));
		file.add(actions.get(Command.Save));
		
		file.addSeparator();
		
		file.add(actions.get(Command.SaveReport));
		
		file.addSeparator();
		
		file.add(actions.get(Command.Exit));
		
		JMenu simulator = new JMenu("Simulator");
		
		simulator.add(actions.get(Command.Play));
		simulator.add(actions.get(Command.Reset));
		//Falta redirect Output
		
		JMenu reports = new JMenu("Reports");
		
		reports.add(actions.get(Command.Report));
		reports.add(actions.get(Command.Clear));
		
		menu.add(file);
		menu.add(simulator);
		menu.add(reports);
		
		//Add menu to window
		setJMenuBar(menu);
	}
	
	private void addSupPanel() {
		supPanel = new JPanel();
		supPanel.setLayout(new BoxLayout(supPanel, BoxLayout.X_AXIS));
		supPanel.add(eventEditor);
		supPanel.add(eventsTable);
		supPanel.add(reportsViewer);
	}
	
	private void play() {
		if (loadedEvents) {
			int time = stepsSpinner.get;
			ByteArrayOutputStream str = new ByteArrayOutputStream();
			try {
				ctrl.getSimulator().execute(str, time);
			} catch (IOException e) {
				ctrl.getSimulator().notifyError("MAL");
				e.printStackTrace();
			}
			showReport();
		}
	}
	
	private void addEventEditor() {
		JScrollPane iniInput = new JScrollPane(textSection.textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		eventEditor = new JPanel(new BorderLayout());
		eventEditor.setBorder(javax.swing.BorderFactory.createTitledBorder(" Events Editor "));
		eventEditor.add(iniInput);
	}
	
	private void addEventsView() {
		eventsTable = new TableOfDescribables(events, columnNameEvents);
		eventsTableModel = new ListOfMapsTableModel();
		eventsTable.setBorder(javax.swing.BorderFactory.createTitledBorder(" Events Queue "));
	}
	
	private void addReportsViewer() {
		reportsArea.setEditable(false);
		JScrollPane reportsAreaScroll = new JScrollPane(reportsArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		reportsViewer = new JPanel(new BorderLayout());
		reportsViewer.setBorder(javax.swing.BorderFactory.createTitledBorder(" Reports Area "));
		reportsViewer.add(reportsAreaScroll);
		
	}
	
	private void addVehiclesTablePanel() {
		vehiclesTable = new TableOfDescribables(map.getVehiclesRO(), columnNameVehicle);
		vehiclesTableModel = new ListOfMapsTableModel();
		vehiclesTable.setBorder(javax.swing.BorderFactory.createTitledBorder(" Vehicles Table "));
		
	}
	
	private void addRoadsTablePanel() {
		roadTable = new TableOfDescribables(map.getRoadsRO(), columnNameRoad);
		roadTableModel = new ListOfMapsTableModel();
		roadTable.setBorder(javax.swing.BorderFactory.createTitledBorder(" Road Table "));
	}
	
	private void addJunctionsTablePanel() {
		junctionTable = new TableOfDescribables(map.getJunctionsRO(), columnNameJunction);
		junctionTableModel = new ListOfMapsTableModel();
		junctionTable.setBorder(javax.swing.BorderFactory.createTitledBorder(" Junction Table "));
	}
	
	private void addInfLeftPanel() {
		infLeftPanel = new JPanel();
		infLeftPanel.setLayout(new BoxLayout(infLeftPanel, BoxLayout.Y_AXIS));
		infLeftPanel.add(vehiclesTable);
		infLeftPanel.add(roadTable);
		infLeftPanel.add(junctionTable);	
	}
	
	private void addInfPanel() {
		infPanel = new JPanel();
		infPanel.setLayout(new BorderLayout());
		JPanel rightInfPanel = new JPanel(new BorderLayout()); //Aqui iria el grafo
		JSplitPane bottomSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, infLeftPanel, rightInfPanel);
		infPanel.add(bottomSplit);
		bottomSplit.setVisible(true);
		bottomSplit.setResizeWeight(.5);
	}
	
	private void addBars() {
		
		JSplitPane topSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, supPanel, infPanel); //Division horizontal
		add(topSplit);
		topSplit.setVisible(true);
		topSplit.setResizeWeight(.33);
	}
	
	private enum Command {
		Exit("Exit"), Clear("Clear"), Save("Save"), Stop("Stop"), SaveReport("Save Report"), 
		Events("Events"), DeleteReport("Delete report"), Play("Play"), Open("Open"), Report("Report"), Reset("Reset");
		
		private String text;
		
		Command(String text) {
			this.text = text;
		}
		
		@Override
		public String toString() {
			return text;
		}
		
	}

	@Override
	public void registered(int time, RoadMap map, List<Event> events) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset(int time, RoadMap map, List<Event> events) {
		loadedEvents = false;
		reportsArea.setText("");
		this.events = ctrl.getSimulator().getEvents().valuesList();
		
		junctionTable.setElements(map.getJunctionsRO());
		roadTable.setElements(map.getRoadsRO());
		vehiclesTable.setElements(map.getVehiclesRO());
		junctionTable.update();
		roadTable.update();
		vehiclesTable.update();		
		
		eventsTable.setElements(events);
		eventsTable.update();
	}

	@Override
	public void eventAdded(int time, RoadMap map, List<Event> events) {
		this.events = events;
		eventsTable.setElements(events);
		eventsTable.update();
	}

	@Override
	public void advanced(int time, RoadMap map, List<Event> events) {
		junctionTable.setElements(map.getJunctionsRO());
		roadTable.setElements(map.getRoadsRO());
		vehiclesTable.setElements(map.getVehiclesRO());
		junctionTable.update();
		roadTable.update();
		vehiclesTable.update();		
	}

	@Override
	public void simulatorError(int time, RoadMap map, List<Event> events, String error) {
		
		
	}
}