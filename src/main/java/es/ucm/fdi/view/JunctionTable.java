package es.ucm.fdi.view;

import java.util.List;

import javax.swing.JTable;

import es.ucm.fdi.model.Junction;

public class JunctionTable {
	private static final String[] COLUMN_NAME = {"ID", "Green", "Red"};
	
	private JTable table;
	
	public JunctionTable(List<Junction> junctionList) {
		Object [][] rowData = new Object[junctionList.size()][COLUMN_NAME.length];
		int row = 0;
		for (Junction junction : junctionList) {
			rowData[row][0] = junction.getId();
			String red = "", green = "";
			junction.getColorLights(red, green);
			rowData[row][1] = green;
			rowData[row][2] = red;
			row++;
		}
		table = new JTable(rowData, COLUMN_NAME);
	}
	
	public JunctionTable() {
		table = new JTable(null, COLUMN_NAME);
	}

	public JTable getTable() {
		return table;
	}
	
}
