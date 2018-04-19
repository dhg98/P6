package es.ucm.fdi.view;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class TextSection extends JPanel {

	protected JTextArea textArea;
	public TextSection(String ini) {
		super();
		textArea = new JTextArea(ini);
	}
	
	public void setText(String text) {
		textArea.setText(text);
	}	
}