package es.ucm.fdi.view;

public enum Command {
	Exit("Exit", "You have exited the aplication."),
	Clear("Clear", "The Events Editor Panel has been cleared."), 
	Save("Save", "Events succesfully saved into an ini file."),
	SaveReport("Save Report", "Report succesfully saved into an ini file."), 
	Events("Events", "The events have been succesfully loaded into de Events Queue."), 
	DeleteReport("Delete report", "The report has been succesfully deleted."), 
	Play("Play", "Simulation played succesfully."), 
	Open("Open", "The ini file has been succesfully loaded."), 
	Report("Report", "The report has been succesfully generated"), 
	Reset("Reset", "The reset has been succesfully done");
	
	protected String text;
	protected String message;
	
	Command(String text, String message) {
		this.text = text;
		this.message = message;
	}
	
	@Override
	public String toString() {
		return text;
	}
}
