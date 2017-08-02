/*
 * 
 * Alex Matthews
 * Software Development Student @ Institute of Technology Carlow, Ireland
 * 2017
 *
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CalculatorPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private final String[] operationButtonStrings = { "C", "←", "√", "x²", "+", "-", "*", "/", ".", "=" };
	private final String[] memoryButtonStrings = { "MC", "MR", "MS", "M+", "M-" };
	private JTextField screen;
	private JPanel buttonPanel;
	private JButton[] numberButtons;
	private JButton[] operationButtons;
	private JButton[] memoryButtons;
	private Calculator calc = new Calculator();
	private boolean screenContentsSelected = false;

	public CalculatorPanel() {
		super(new BorderLayout());
		screenSetup();
		addButtons();
	} 

	private void screenSetup() {
		screen = new JTextField("0");
		screen.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		screen.setEditable(false);
		screen.setFont(new Font("Segoe UI", Font.BOLD, 25));
		screen.setHorizontalAlignment(JTextField.RIGHT);
		screen.setBackground(Color.WHITE);
		screen.setSelectionColor(null);
		this.add(screen, BorderLayout.NORTH);
	}

	private void addButtons() {
		buttonPanel = new JPanel(new GridLayout(5, 5, 2, 2));
		createAndAddMemoryButtons();
		createAndAddNumberButtons();
		createAndAddOperationButtons();
		this.add(buttonPanel, BorderLayout.CENTER);
	}

	private void createAndAddMemoryButtons() {
		MemoryButtonListener memoryButtonListener = new MemoryButtonListener(); 
		memoryButtons = new JButton[5];
		for (int i = 0; i < 5; i++) {
			memoryButtons[i] = new JButton(memoryButtonStrings[i]);
			memoryButtons[i].setMinimumSize(new Dimension(50, 35));
			memoryButtons[i].setFont(new Font("monspaced", Font.PLAIN, 12));
			memoryButtons[i].setBackground(Color.WHITE);
			memoryButtons[i].addActionListener(memoryButtonListener);
			memoryButtons[i].setFocusable(false);
			buttonPanel.add(memoryButtons[i]);
		}
		enableMemoryClearAndRead(false);
	}

	private void createAndAddOperationButtons() {
		OperationButtonListener operatorButtonListener = new OperationButtonListener(); 
		operationButtons = new JButton[12];
		for (int i = 0; i < 10; i++) {
			operationButtons[i] = new JButton(operationButtonStrings[i]);
			operationButtons[i].setMinimumSize(new Dimension(50, 35));
			operationButtons[i].setBackground(Color.WHITE);
			operationButtons[i].addActionListener(operatorButtonListener); 
			operationButtons[i].setFocusable(false);
			buttonPanel.add(operationButtons[i]);
		}
	}

	private void createAndAddNumberButtons() {
		NumberButtonListener numberButtonListener = new NumberButtonListener();
		numberButtons = new JButton[10];
		for (int i = 0; i < 10; i++) {
			numberButtons[i] = new JButton(Integer.toString(i));
			numberButtons[i].setMinimumSize(new Dimension(50, 35)); 
			numberButtons[i].setBackground(Color.WHITE); 
			numberButtons[i].addActionListener(numberButtonListener); 
			numberButtons[i].setFocusable(false);
			buttonPanel.add(numberButtons[i]);
		}
	}

	private class NumberButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if ("0".equals(screen.getText())) {
				selectScreenContents();
			}
			
			for (int i = 0; i < 10; i++) {
				if (e.getSource() == numberButtons[i]) {
					screen.replaceSelection(i + "");
					screenContentsSelected = false;
				}
			}
		}
	}

	private class OperationButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == operationButtons[0]) { // C
				screen.setText("0");
				
			} else if (e.getSource() == operationButtons[1]) { // ←
				backSpace();
				
			} else if (e.getSource() == operationButtons[2]) { // √
				screen.setText(calc.sqrRoot(screen.getText()));
				selectScreenContents();
				
			} else if (e.getSource() == operationButtons[3]) { // x²
				screen.setText(calc.squared(screen.getText()));
				selectScreenContents();
				
			} else if (e.getSource() == operationButtons[4]) { // +
				displaySymbol(operationButtons[4].getText());
				
			} else if (e.getSource() == operationButtons[5]) { // -
				displaySymbol(operationButtons[5].getText());
				
			} else if (e.getSource() == operationButtons[6]) { // *
				displaySymbol(operationButtons[6].getText());
				
			} else if (e.getSource() == operationButtons[7]) { // Divide
				displaySymbol(operationButtons[7].getText());
				
			} else if (e.getSource() == operationButtons[8]) {
				if (screenContentsSelected == false)
					displayDecimal();
				
			} else if (e.getSource() == operationButtons[9]) {	// =
				try {
					screen.setText(calc.eval(screen.getText()));
					selectScreenContents();
				} catch (DivideByZeroException dbze) {
					screen.setText("Cannot divide by zero");
					selectScreenContents();
				}
			}
		}
	}
	
	private void backSpace() {
		if (!screenIsEmpty()) {
			String display = screen.getText();
			if (display.length() == 1)
				screen.setText("0");
			else
				screen.setText(display.substring(0, display.length() - 1));
		}
	}
	
	private boolean screenIsEmpty() {
		String display = screen.getText();
		return "0".equals(display);
	}
	
	private void displayDecimal() {
		boolean okToDisplay = false;
		String display = screen.getText();
		for (int i = display.length() - 1; i >= 0; i--) {
			char currentChar = display.charAt(i);
			if (currentChar == '*' || currentChar == '+' || currentChar == '/' || currentChar == '-') {
				okToDisplay = true;
				break;
			} else if (currentChar == '.') {
				okToDisplay = false;
				break;
			} else {
				okToDisplay = true;
			}
		}
		
		if (okToDisplay)
			displaySymbol("."); 
	}

	private void displaySymbol(String symbol) {
		String display = screen.getText();
		screen.setText(display + symbol);
	}

	private class MemoryButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == memoryButtons[0]) { //MC
				calc.clearMemoryValue();
				enableMemoryClearAndRead(false);
				
			} else if (e.getSource() == memoryButtons[1]) { //MR
				screen.setText(calc.getMemoryValue());
				selectScreenContents();
				
			} else if (e.getSource() == memoryButtons[2]) { //MS
				if (!"Invalid".equals(screen.getText())) { 
					if (calc.memoryStore(screen.getText()) == true) {
						enableMemoryClearAndRead(true);
					} else {
						screen.setText("Invalid");
						screen.selectAll();
					}
				}
				
			} else if (e.getSource() == memoryButtons[3]) { //M+
				if (calc.plusMemoryValue(screen.getText()) == false) { 
					screen.setText("Invalid");
					screen.selectAll();
				}

			} else if (e.getSource() == memoryButtons[4]) { //M-
				if (calc.minusMemoryValue(screen.getText()) == false) {
					screen.setText("Invalid");
					screen.selectAll();
				}
			}
		}
	}
	
	private void enableMemoryClearAndRead(boolean choice) {
		memoryButtons[0].setEnabled(choice); 
		memoryButtons[1].setEnabled(choice);
	}
	
	private void selectScreenContents() {
		screen.selectAll();
		screenContentsSelected = true;
	}

}
