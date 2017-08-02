/*
 * 
 * Alex Matthews
 * Software Development Student @ Institute of Technology Carlow, Ireland
 * 2017
 *
 */

import java.awt.GridLayout;
import javax.swing.JFrame;

public class App extends Thread implements Runnable {

	public static void main(String[] args) {
		App app = new App();
		javax.swing.SwingUtilities.invokeLater(app);
	}

	@Override
	public void run() {
		makeGUI();
	}

	private void makeGUI() {
		JFrame frame = new JFrame("My Planner");
		CalculatorPanel calculatorPanel = new CalculatorPanel();
		NotesPanel notesPanel = new NotesPanel();
		ClockPanel clockPanel = new ClockPanel();
		WeatherPanel weatherPanel = new WeatherPanel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 600);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setLayout(new GridLayout(2, 2));
		frame.add(notesPanel);
		frame.add(clockPanel);
		frame.add(calculatorPanel);
		frame.add(weatherPanel);
		frame.setVisible(true);
	}

}
