package es.ucm.fdi.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

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
