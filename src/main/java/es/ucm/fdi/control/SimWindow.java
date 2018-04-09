package es.ucm.fdi.control;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import es.ucm.fdi.view.JunctionTable;
import es.ucm.fdi.view.RoadTable;
import es.ucm.fdi.view.VehicleTable;

/**
 * Esto es sólo para empezar a jugar con las interfaces
 * de la P5. 
 * 
 * El código <i>no</i> está bien organizado, y meter toda
 * la funcionalidad aquí sería un disparate desde un punto
 * de vista de mantenibilidad.
 */

public class SimWindow extends JFrame {
	private static final Object[] COLUMN_NAME = {"ID", "Source", "Target", "Lenght", "Max Speed", "Vehicles"};
	
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
				"Cargar", "open.png", "Cargar un fichero en formato .ini", KeyEvent.VK_L, "control L", ()->System.out.println("cargando..."));
		SimulatorAction saveReport = new SimulatorAction("Save Report", "save_report.png", "Save a report", KeyEvent.VK_R, "control R", ()->System.out.println("salvando..."));
		
		// add actions to toolbar, and bar to window
		JToolBar bar = new JToolBar();
		bar.add(cargar);
		bar.add(saveEvent);
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
	
	public static void main(String ... args) {
		SwingUtilities.invokeLater(() -> new SimWindow());
	}
}