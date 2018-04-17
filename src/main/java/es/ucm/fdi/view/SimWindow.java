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

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
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
	
	
	private TextSection textSection;
	private Map<Object, SimulatorAction> actions = new HashMap<>();
	
	public SimWindow() {
		super("Traffic Simulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addBars();
		
		setSize(1000, 1000);		
		setVisible(true);
	}
	
	private JPanel createPanel(Color color) {
		JPanel panel = new JPanel();
		panel.setBackground(color);
		return panel;
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
	    	   
	    	   
	       } catch (IOException e) {
	    	   
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
	
	private void createAction() {
		SimulatorAction salir = new SimulatorAction(
				Command.Exit, "exit.png", "Exit the aplication",
				KeyEvent.VK_C, "control shift C", 
				()-> System.exit(0));
		
		SimulatorAction saveEvent = new SimulatorAction(
				Command.Save, "save.png", "Guardar cosas",
				KeyEvent.VK_S, "control S", 
				()-> System.err.println("guardando..."));
		
		SimulatorAction cargar = new SimulatorAction(
				Command.Load, "open.png", "Cargar un fichero en formato .ini", 
				KeyEvent.VK_L, "control L", 
				()->readIni());
		
		SimulatorAction saveReport = new SimulatorAction(
				Command.SaveReport, "save_report.png", "Save a report", 
				KeyEvent.VK_R, "control R", 
				()->System.out.println("salvando..."));
		
		SimulatorAction flush = new SimulatorAction(
				"Clear", "clear.png", "Clear the text",
				KeyEvent.VK_X, "control X",
				()->System.out.println(""));
		
		
		actions.put(Command.Exit, salir);
		actions.put(Command.Save, saveEvent);
		actions.put(Command.Load, );
		actions.put(Command.Run, );
		actions.put(Command.Stop, );
		actions.put(Command.SaveReport, );
		actions.put(Command.Events, );
		actions.put(Command.DeleteReport, );
		actions.put(Command.Play, );
		actions.put(Command.Clear, );
	}
	
	private void addBars() {
		// instantiate actions
		SimulatorAction salir = new SimulatorAction(
				"Salir", "exit.png", "Salir de la aplicacion",
				KeyEvent.VK_A, "control shift X", 
				()-> System.exit(0));
		
		SimulatorAction saveEvent = new SimulatorAction(
				"Guardar", "save.png", "Guardar cosas",
				KeyEvent.VK_S, "control S", 
				()-> System.err.println("guardando..."));
		
		SimulatorAction cargar = new SimulatorAction(
				"Cargar", "open.png", "Cargar un fichero en formato .ini", 
				KeyEvent.VK_L, "control L", 
				()->readIni());
		
		SimulatorAction saveReport = new SimulatorAction(
				"Save Report", "save_report.png", "Save a report", 
				KeyEvent.VK_R, "control R", 
				()->System.out.println("salvando..."));
		
		SimulatorAction flush = new SimulatorAction(
				"Clear", "clear.png", "Clear the text",
				KeyEvent.VK_X, "control X",
				()->System.out.println(""));
		
		// add actions to toolbar, and bar to window
		JToolBar bar = new JToolBar();
		bar.add(cargar);
		bar.add(saveEvent);
		bar.add(flush);
		bar.addSeparator(new Dimension(5, 5));
		bar.add(saveReport);
		bar.addSeparator();
		bar.add(salir);
		add(bar, BorderLayout.NORTH);

		// add actions to menubar, and bar to window
		JMenu file = new JMenu("File");
		file.add(cargar);
		file.add(saveEvent);
		file.addSeparator();
		file.add(saveReport);
		file.addSeparator();
		file.add(salir);		
		JMenuBar menu = new JMenuBar();
		menu.add(file);
		setJMenuBar(menu);
		
		JMenu reports = new JMenu("Reports");
		reports.add(flush);
		menu.add(reports);
		
		JPanel panelSup = new JPanel(new BorderLayout());
		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(Color.BLUE);
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.GREEN);
		JPanel rightPanel = new JPanel();
		rightPanel.setBackground(Color.RED);
		
		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, centerPanel);
		JSplitPane sp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sp, rightPanel);
		
		panelSup.add(sp2, BorderLayout.CENTER);
		
		add(panelSup);
		JPanel panelInf = new JPanel();
	}
	
	public void addMenuAndToolBar() {
		
		
		
	}
	
	public static void main(String ... args) {
		SwingUtilities.invokeLater(() -> new SimWindow());
	}
	
	private enum Command {
		Exit("Exit"), Clear("Clear"), Save("Save"), Load("Load"), Run("Run"), Stop("Stop"), SaveReport("Save Report"), 
		Events("Events"), DeleteReport("Delete report"), Play("Play");
		
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