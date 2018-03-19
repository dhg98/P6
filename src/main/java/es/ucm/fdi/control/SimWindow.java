package es.ucm.fdi.control;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

/**
 * Esto es sólo para empezar a jugar con las interfaces
 * de la P5. 
 * 
 * El código <i>no</i> está bien organizado, y meter toda
 * la funcionalidad aquí sería un disparate desde un punto
 * de vista de mantenibilidad.
 */

public class SimWindow extends JFrame {
	public SimWindow() {
		super("Traffic Simulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addBars();
		
		setSize(1000, 1000);		
		setVisible(true);
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
	}
	
	public static void main(String ... args) {
		SwingUtilities.invokeLater(() -> new SimWindow());
	}
}