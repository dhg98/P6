package es.ucm.fdi.view.extra.popupmenu;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import es.ucm.fdi.view.Template;

public class PopUpMenu {
	private JPanel _mainPanel;
	private JTextArea _editor;

	public PopUpMenu() {
		_mainPanel = new JPanel(new BorderLayout());
		addEditor();
	}
	
	public JTextArea get_editor() {
		return _editor;
	}
	
	private void addEditor() {

		_editor = new JTextArea();

		_mainPanel.add(new JScrollPane(_editor, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);

		// create the events pop-up menu
		JPopupMenu _editorPopupMenu = new JPopupMenu();
		
		JMenuItem clearOption = new JMenuItem("Clear");
		clearOption.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_editor.setText("");
			}
		});

		JMenuItem exitOption = new JMenuItem("Exit");
		exitOption.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JMenu subMenu = new JMenu("Add Template");

		Template[] templates = {Template.NewVehicle,
								Template.NewBike,
								Template.NewCar,
								Template.NewRoad,
								Template.NewLanes,
								Template.NewDirt,
								Template.NewJunction,
								Template.NewRoundRobin,
								Template.NewMostCrowded,
								Template.MakeFaulty };
		for (Template t : templates) {
			JMenuItem menuItem = new JMenuItem(t.toString());
			menuItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					_editor.insert(t.getTemp(), _editor.getCaretPosition());
				}
			});
			subMenu.add(menuItem);
		}

		_editorPopupMenu.add(subMenu);
		_editorPopupMenu.addSeparator();
		_editorPopupMenu.add(clearOption);
		_editorPopupMenu.add(exitOption);

		// connect the popup menu to the text area _editor
		_editor.addMouseListener(new MouseListener() {

			@Override
			public void mousePressed(MouseEvent e) {
				showPopup(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				showPopup(e);
			}

			private void showPopup(MouseEvent e) {
				if (e.isPopupTrigger() && _editorPopupMenu.isEnabled()) {
					_editorPopupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

	}
}
