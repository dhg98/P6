package es.ucm.fdi.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import es.ucm.fdi.model.Describable;

public class ListOfMapsTableModel extends AbstractTableModel {

	protected String[] fieldNames;
	protected List<Describable> elements;
	
	@Override
	public int getColumnCount() {
		return fieldNames.length;
	}

	@Override
	public int getRowCount() {
		return elements.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Map <String, String> out = new HashMap<>();
		elements.get(rowIndex).describe(out);
		return out.get(fieldNames[columnIndex]);
	}

}
