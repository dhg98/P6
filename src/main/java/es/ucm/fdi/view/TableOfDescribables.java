package es.ucm.fdi.view;

import java.awt.BorderLayout;
import java.util.*;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import es.ucm.fdi.model.Describable;

/**
 * Make up the tables for the interface.
 * 
 * @author Javier Galiana
 */
public class TableOfDescribables extends JPanel {

	private JTable table;
	private ListOfMapsTableModel model;

	protected String[] fieldNames;
	protected List<? extends Describable> elements;

	public void setElements(List<? extends Describable> elements) {
		this.elements = elements;
	}

	public TableOfDescribables(List<? extends Describable> elements, String[] colNames) {
		super(new BorderLayout());
		this.elements = elements;
		this.fieldNames = colNames;
		model = new ListOfMapsTableModel();
		table = new JTable(model);
		add(new JScrollPane(table), BorderLayout.CENTER);
	}

	/**
	 * Updates a table by updating the model
	 */
	public void update() {
		model.update();
	}

	private class ListOfMapsTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 2250745434019390850L;

		@Override
		public int getColumnCount() {
			return fieldNames.length;
		}

		/**
		 * Updates a TableModel
		 */
		private void update() {
			fireTableDataChanged();
		}

		@Override
		public String getColumnName(int colIndex) {
			return fieldNames[colIndex];
		}

		@Override
		public int getRowCount() {
			return elements.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Map<String, String> out = new HashMap<>();
			elements.get(rowIndex).describe(out);
			//Si la columna es #, mostramos el numero de la fila.
			if ("#".equals(fieldNames[columnIndex])) {
				return rowIndex;
			} else {
				return out.get(fieldNames[columnIndex]);
			}
		}
	}
}
