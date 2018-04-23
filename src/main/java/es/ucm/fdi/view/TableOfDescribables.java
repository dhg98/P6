package es.ucm.fdi.view;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import es.ucm.fdi.model.Describable;

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
	
	public void update() {
		model.update();
	}
	
	private class ListOfMapsTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 2250745434019390850L;

		@Override
		public int getColumnCount() {
			return fieldNames.length;
		}
		
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
			Map <String,String> out = new HashMap<>();
			elements.get(rowIndex).describe(out);
			if ("#".equals(fieldNames[columnIndex])) {
				return rowIndex;
			} else {
				return out.get(fieldNames[columnIndex]);
			}
		}
	}
	
	/*public static void main(String ...args) {
		SwingUtilities.invokeLater(() -> {
			JFrame jf = new JFrame("test");
			jf.setLayout(new BorderLayout());
			TableOfDescribables tod = new TableOfDescribables(Arrays.asList(new Zuncho[]{
					new Zuncho(),
					new Zuncho(),
					new Zuncho()
			}), new String[] {"a", "b", "c", "d"});
			jf.add(tod, BorderLayout.CENTER);
			JButton jb = new JButton("repinta");
			jb.addActionListener((ae) ->tod.update()); 
			jf.add(jb, BorderLayout.SOUTH);
			jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
			jf.setSize(300, 300);
			jf.setVisible(true);
		});
	}*/
}
