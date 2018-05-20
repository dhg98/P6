package es.ucm.fdi.view;

/**
 * Launches the threads of the simulation.
 * 
 * @author Javier Galiana
 */
public class Stepper {
    private Runnable before;
    private Runnable during;
    private Runnable after;
    
    private int steps;
    
    public Stepper(Runnable before, Runnable during, Runnable after) {
        this.before = before;
        this.during = during;
        this.after = after;
    }
    
    /**
	 * 
	 */
    public Thread start (int steps, int delay) {
        this.steps = steps;
        
        Thread t = new Thread(()->{
            boolean stopRequested = false;
            try {
                before.run();
                while (!stopRequested && Stepper.this.steps > 0) {
                    during.run();
                    try {
                        Thread.sleep(delay);
                    } catch(InterruptedException ie) {
                    	stopRequested = true;
                    }
                    Stepper.this.steps --;
                }
            } catch(Exception e) {
                //log.warn("Exception while stepping, "
                    //    + this.steps + " remaining: "+ e, e);
            }finally {
                after.run();
            }
        });
        t.start();
        return t;
    }
}

