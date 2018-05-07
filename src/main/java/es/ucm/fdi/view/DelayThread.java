package es.ucm.fdi.view;

import java.io.IOException;
import java.io.OutputStream;

import es.ucm.fdi.model.TrafficSimulator;

public class DelayThread implements Runnable {
	private int n;
	private TrafficSimulator simulator;
	private int delay;
	private OutputStream out;
	
	public DelayThread(int n, TrafficSimulator simulator, int delay, OutputStream out) {
		this.n = n;
		this.simulator = simulator;
		this.delay = delay;
		this.out = out;
	}
	
	public void run() {
		for (int i = 0; i < n; ++i) {
			try {
				simulator.execute(out, 1);
			} catch (IOException e) {
				simulator.notifyError(e.getMessage());
			}
			
		}
		
	}
}
