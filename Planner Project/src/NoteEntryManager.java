/*
 * 
 * Alex Matthews
 * Software Development Student @ Institute of Technology Carlow, Ireland
 * 2017
 *
 */

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class NoteEntryManager {

	private JFrame entriesFrame;
	private JPanel entriesContainer;
	private JScrollPane entryScrollPane;
	private int entryCount;

	public void saveEntry(String text) {
		if ("".equals(text)) {
			saveErrorMessage();
			return;
		}
		Object[] options = { "Yes", "Cancel" };
		int n = JOptionPane.showOptionDialog(null, "Are you sure you want to save this entry?", "Confirm Save",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		if (n == 0) {
			save(text);
			JOptionPane.showMessageDialog(null, "Entry has been saved!", "Success", JOptionPane.INFORMATION_MESSAGE);

		}

	}

	private void save(String text) {
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT);
		String formattedDateAndTime = LocalDateTime.now().format(formatter);
		entryCount = getEntryCountFromFile();
		try (FileWriter fw = new FileWriter("Entry" + (entryCount + 1) + ".txt");
				BufferedWriter bw = new BufferedWriter(fw)) {

			bw.write("Entry " + (entryCount + 1 + ":\n"));
			bw.write("\n");
			bw.write(text);
			bw.write("\n");
			bw.write("\nTime Stamp: " + "\n" + formattedDateAndTime);
			entryCount++;
			updateEntryCount();
			bw.close();
		} catch (IOException e) {
			unexpectedErrorMessage();
		}

	}

	private void updateEntryCount() {
		try (FileWriter fw = new FileWriter("entryCount.txt", false); BufferedWriter bw = new BufferedWriter(fw)) {
			bw.write(Integer.toString(entryCount));
			bw.close();
		} catch (IOException e) {
			unexpectedErrorMessage();
		}
	}

	public void createAndShowPastEntriesFrame() {
		entryCount = getEntryCountFromFile();
		pastEntriesFrameSetup();
		if (entryCount != 0) {
			populateEntryContainer();
		} else {
			JLabel noEntries = new JLabel("Nothing to show...");
			noEntries.setFont(new Font("Serif", Font.BOLD, 25));
			entriesContainer.setLayout(new GridBagLayout());
			entriesContainer.add(noEntries);
		}

		entriesFrame.setVisible(true);
	}

	private void pastEntriesFrameSetup() {
		entriesFrame = new JFrame("Journal Entries");
		entriesFrame.setSize(343, 239);
		entriesFrame.setLocationRelativeTo(null);
		entriesContainer = new JPanel();
		entriesContainer.setLayout(new GridLayout(entryCount, 1));
		entryScrollPane = new JScrollPane(entriesContainer);
		entryScrollPane.getVerticalScrollBar().setUnitIncrement(15);
		entriesFrame.add(entryScrollPane);
	}

	private void populateEntryContainer() {
		JTextPane entryPane;
		String fileName;
		for (int currentEntry = 1; currentEntry <= entryCount; currentEntry++) {
			entryPane = new JTextPane();
			fileName = "Entry" + currentEntry + ".txt";
			entryPane.setText(readFile(fileName));
			entryPane.setEditable(false);
			entryPane.setBorder(BorderFactory.createBevelBorder(1));
			entriesContainer.add(entryPane);
		}
	}

	private String readFile(String fileName) {
		StringBuilder sb = new StringBuilder();
		try (FileReader fr = new FileReader(fileName); BufferedReader br = new BufferedReader(fr)) {
			String currentLine;
			while ((currentLine = br.readLine()) != null) {
				sb.append(currentLine + "\n");
			}
			br.close();
		} catch (FileNotFoundException e) {
			unexpectedErrorMessage();
		} catch (IOException e) {
			unexpectedErrorMessage();
		}
		return sb.toString();
	}

	private int getEntryCountFromFile() {
		String number = readFile("entryCount.txt");
		return Integer.parseInt(number.trim());
	}

	private void saveErrorMessage() {
		JOptionPane.showMessageDialog(null, "In order to save, enter some text!", "Error", JOptionPane.ERROR_MESSAGE);

	}

	private void unexpectedErrorMessage() {
		JOptionPane.showMessageDialog(null, "There was an unexpected error, try again!", "Unexpected error",
				JOptionPane.ERROR_MESSAGE);
	}
}
