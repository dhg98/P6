package es.ucm.fdi.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Esto es sólo para empezar a jugar con las interfaces
 * de la P5. 
 * 
 * El código <i>no</i> está bien organizado, y meter toda
 * la funcionalidad aquí sería un disparate desde un punto
 * de vista de mantenibilidad.
 */

public class SimWindow extends JFrame {
	
	
	private TextSection textSection = new TextSection("");
	private JPanel eventEditor;
	private JPanel vehicleTablePanel;
	private JPanel roadsTablePanel;
	private JPanel junctionsTablePanel;
	private JPanel reportsViewer;
	private JPanel eventsView;
	private JPanel supPanel;
	private JPanel infPanel;
	private JPanel infLeftPanel;
	private JSpinner stepsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1)); //new SpinnerNumberModel(CurrentValue, min, max, steps)
	private JTextField timeViewer = new JTextField("1");
	private Map<Object, SimulatorAction> actions = new HashMap<>();
	
	public SimWindow() {
		super("Traffic Simulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
	       File f = chooser.getSelectedFile();
	       System.out.println("You chose to open this file: " +
	            f.getName());
	       try {
	    	   String st = new String(Files.readAllBytes(f.toPath()), "UTF-8");
	    	   textSection.textArea.setText(st);
	       } catch (IOException e) {
	    	   textSection.textArea.setText("");
	       }
	    }		
	}
	
	public void saveIni() {
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
	    		String s = textSection.textArea.getText();
	    		Files.write(f.toPath(), textSection.textArea.getText().getBytes("UTF-8"));
	    		
	    	} catch (IOException e) {
	    		
	    	}	   
	    }
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
				()-> saveIni());
		
		SimulatorAction open = new SimulatorAction(
				Command.Open, "open.png", "Load an ini file", 
				KeyEvent.VK_L, "control L", 
				()->readIni());
		
		SimulatorAction saveReport = new SimulatorAction(
				Command.SaveReport, "save_report.png", "Save a report", 
				KeyEvent.VK_R, "control R", 
				()->System.out.println("salvando..."));
		
		SimulatorAction clear = new SimulatorAction(
				Command.Clear, "clear.png", "Clear the text",
				KeyEvent.VK_X, "control X",
				()->System.out.println(""));
		
		SimulatorAction play = new SimulatorAction(
				Command.Play, "play.png", "Play the simulation",
				KeyEvent.VK_P, "control P",
				()->System.out.println(""));
		
		SimulatorAction events = new SimulatorAction(
				Command.Events, "events.png", "Add events to the simulation",
				KeyEvent.VK_A, "control A",
				()->System.out.println(""));
		
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
				()->System.out.println(""));
		
		SimulatorAction reset = new SimulatorAction(
				Command.Reset, "reset.png", "Reset the simulation",
				KeyEvent.VK_Z, "control Z",
				()->System.out.println(""));
		
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
		bar.add(stepsSpinner);
		
		//timeViewer.setSize(2, 2);
		timeViewer.setEditable(false);
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
		supPanel.add(eventsView);
		supPanel.add(reportsViewer);
	}
	
	private void addEventEditor() {
		JScrollPane iniInput = new JScrollPane(textSection.textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		eventEditor = new JPanel(new BorderLayout());
		eventEditor.add(iniInput);
	}
	
	private void addEventsView() {
		eventsView = new JPanel(new BorderLayout());
	}
	
	private void addReportsViewer() {
		JScrollPane reportsAreaScroll = new JScrollPane(new JTextArea(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		reportsViewer = new JPanel(new BorderLayout());
		reportsViewer.add(reportsAreaScroll);
		
	}
	
	private void addVehiclesTablePanel() {
		vehicleTablePanel = new JPanel(new BorderLayout());
	}
	
	private void addRoadsTablePanel() {
		roadsTablePanel = new JPanel(new BorderLayout());
	}
	
	private void addJunctionsTablePanel() {
		junctionsTablePanel = new JPanel(new BorderLayout());
	}
	
	private void addInfLeftPanel() {
		infLeftPanel = new JPanel();
		infLeftPanel.setLayout(new BoxLayout(infLeftPanel, BoxLayout.Y_AXIS));
		infLeftPanel.add(vehicleTablePanel);
		infLeftPanel.add(roadsTablePanel);
		infLeftPanel.add(junctionsTablePanel);	
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
	
	public static void main(String ... args) {
		SwingUtilities.invokeLater(() -> new SimWindow());
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
}