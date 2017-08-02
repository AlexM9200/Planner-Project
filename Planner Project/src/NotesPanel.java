/*
 * 
 * Alex Matthews
 * Software Development Student @ Institute of Technology Carlow, Ireland
 * 2017
 *
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

public class NotesPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private NoteEntryManager entryManager;
	private JEditorPane textArea;
	private JFontChooser fontChooser;

	public NotesPanel() {
		super(new BorderLayout());
		entryManager = new NoteEntryManager();
		fontChooser = new JFontChooser();
		createTextEditor();
		createToolbar();
	}

	private void createTextEditor() {
		textArea = new JEditorPane();
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(343, 239));
		this.add(scrollPane);
		textArea.setText("Make an entry for future reference...");
		textArea.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (textArea.getText().equals("Make an entry for future reference...")) {
					textArea.setText("");
				}

			}

		});
	}

	private void createToolbar() {
		JToolBar tools = new JToolBar();
		createAndAddSaveBtn(tools);
		createAndAddClearBtn(tools);
		createAndAddFontBtn(tools);
		createAndAddPastEntriesBtn(tools);
		this.add(tools, BorderLayout.PAGE_START);
	}

	private void createAndAddSaveBtn(JToolBar tools) {
		JButton saveBtn = new JButton();
		saveBtn.setIcon(new ImageIcon("iconsave.gif"));
		tools.add(saveBtn);
		saveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				entryManager.saveEntry(textArea.getText());
				textArea.setText("");

			}

		});
	}

	private void createAndAddClearBtn(JToolBar tools) {
		JButton clearBtn = new JButton();
		clearBtn.setIcon(new ImageIcon("closeIcon.png"));
		tools.add(clearBtn);
		clearBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				textArea.selectAll();
				textArea.replaceSelection("");
			}

		});
	}

	private void createAndAddFontBtn(JToolBar tools) {
		JButton fontBtn = new JButton("Font");
		tools.add(fontBtn);
		fontBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFont();
			}

		});
	}

	private void createAndAddPastEntriesBtn(JToolBar tools) {
		JButton pastEntriesBtn = new JButton("Past Entries");
		tools.add(pastEntriesBtn);
		pastEntriesBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				entryManager.createAndShowPastEntriesFrame();

			}

		});
	}

	private void setFont() {

		int result = fontChooser.showDialog(this);
		if (result == JFontChooser.OK_OPTION) {
			Font fontChoice = fontChooser.getSelectedFont();
			textArea.setFont(fontChoice);
		}
	}

}
