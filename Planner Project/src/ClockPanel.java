/*
 * 
 * Alex Matthews
 * Software Development Student @ Institute of Technology Carlow, Ireland
 * 2017
 *
 */

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class ClockPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final String[] timeZones = { "Mexico City -06:00", "New York -05:00", "Cayenne -03:00", "Noronha -02:00",
			"Dublin +00:00", "Paris +01:00", "Sofia +02:00", "Dubai +04:00", "Maldives +05:00" };
	private final int MAX_ROW_COUNT = 13;
	private final int INITIAL_COMBO_INDEX = 4;
	private JToolBar clockMenu;
	private JPanel subClockPanel;
	private JLabel time = new JLabel();
	private JLabel date = new JLabel();
	private JLabel selectedTimeZoneLabel;
	private String selectedTimeZone;
	private GridBagConstraints gbConstraints;

	public ClockPanel() {
		super(new BorderLayout());
		selectedTimeZone = "Dublin +00:00";
		clockMenuSetup();
		subClockPanelSetup();
		startClock();
	}

	private void clockMenuSetup() {
		clockMenu = new JToolBar();
		JLabel chooseTZLabel = new JLabel("Choose Time Zone:   ");
		clockMenu.add(chooseTZLabel);
		setupAndAddTimezoneCombo();
		this.add(clockMenu, BorderLayout.PAGE_START);
	}

	private void setupAndAddTimezoneCombo() {
		JComboBox<Object> timezoneComboBox = new JComboBox<Object>(timeZones);
		timezoneComboBox.setSelectedIndex(INITIAL_COMBO_INDEX);
		timezoneComboBox.setMaximumRowCount(MAX_ROW_COUNT);
		timezoneComboBox.setFocusable(false);
		clockMenu.add(timezoneComboBox);
		timezoneComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<?> cb = (JComboBox<?>) e.getSource();
				selectedTimeZone = (String) cb.getSelectedItem();
				changeClock();
			}
		});
		
	}

	private void subClockPanelSetup() {
		gbConstraints = new GridBagConstraints();
		subClockPanel = new JPanel();
		subClockPanel.setLayout(new GridBagLayout());
		setupAndAddSelectedTimezoneLabel();
		setupAndAddClock();	
		this.add(subClockPanel, BorderLayout.CENTER);
	}
	
	private void setupAndAddClock() {
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		time.setFont(new Font("Serif", Font.BOLD, 32));
		time.setText(timeFormat.format(Calendar.getInstance().getTime()));
		gbConstraints.gridy = 1;
		subClockPanel.add(time, gbConstraints);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		date.setFont(new Font("Serif", Font.BOLD, 32));
		date.setText(dateFormat.format(Calendar.getInstance().getTime()));
		gbConstraints.gridy = 2;
		subClockPanel.add(date, gbConstraints);
	}

	private void setupAndAddSelectedTimezoneLabel() {
		selectedTimeZoneLabel = new JLabel();
		selectedTimeZoneLabel.setText("Dublin");
		selectedTimeZoneLabel.setFont(new Font("Serif", Font.BOLD, 32));
		gbConstraints.gridy = 0;
		subClockPanel.add(selectedTimeZoneLabel, gbConstraints);
	}

	private void startClock() {
		Thread clockThread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					updateTime(selectedTimeZone);

				}
			}
		});
		clockThread.start();
	}

	private void changeClock() {
		if (selectedTimeZone == "Dublin +00:00") {
			selectedTimeZoneLabel.setText("Dublin");
			
		} else if (selectedTimeZone == "Paris +01:00") {
			selectedTimeZoneLabel.setText("Paris");
			
		} else if (selectedTimeZone == "Sofia +02:00") {
			selectedTimeZoneLabel.setText("Sofia");
			
		} else if (selectedTimeZone == "Dubai +04:00") {
			selectedTimeZoneLabel.setText("Dubai");
			
		} else if (selectedTimeZone == "Maldives +05:00") {
			selectedTimeZoneLabel.setText("Maldives");
			
		} else if (selectedTimeZone == "Mexico City -06:00") {
			selectedTimeZoneLabel.setText("Mexico City");
			
		} else if (selectedTimeZone == "New York -05:00") {
			selectedTimeZoneLabel.setText("New York");
			
		} else if (selectedTimeZone == "Cayenne -03:00") {
			selectedTimeZoneLabel.setText("Cayenne");
			
		} else if (selectedTimeZone == "Noronha -02:00") {
			selectedTimeZoneLabel.setText("Noranha");
			
		}
	}

	private void updateTime(String selectedTimeZone) {
		DateTime dt = new DateTime();
		if (selectedTimeZone == "Dublin +00:00") {
			DateTime dtDublin = dt.withZone(DateTimeZone.forID("Europe/Dublin"));
			updateTime(dtDublin);

		} else if (selectedTimeZone == "Paris +01:00") {
			DateTime dtParis = dt.withZone(DateTimeZone.forID("Europe/Paris"));
			updateTime(dtParis);

		} else if (selectedTimeZone == "Sofia +02:00") {
			DateTime dtSofia = dt.withZone(DateTimeZone.forID("Europe/Sofia"));
			updateTime(dtSofia);

		} else if (selectedTimeZone == "Dubai +04:00") {
			DateTime dtDubai = dt.withZone(DateTimeZone.forID("Asia/Dubai"));
			updateTime(dtDubai);

		} else if (selectedTimeZone == "Maldives +05:00") {
			DateTime dtMaldives = dt.withZone(DateTimeZone.forID("Indian/Maldives"));
			updateTime(dtMaldives);

		} else if (selectedTimeZone == "Mexico City -06:00") {
			DateTime dtMexico = dt.withZone(DateTimeZone.forID("America/Mexico_City"));
			updateTime(dtMexico);

		} else if (selectedTimeZone == "New York -05:00") {
			DateTime dtNewYork = dt.withZone(DateTimeZone.forID("America/New_York"));
			updateTime(dtNewYork);

		} else if (selectedTimeZone == "Cayenne -03:00") {
			DateTime dtCayenne = dt.withZone(DateTimeZone.forID("America/Cayenne"));
			updateTime(dtCayenne);

		} else if (selectedTimeZone == "Noronha -02:00") {
			DateTime dtNoronha = dt.withZone(DateTimeZone.forID("America/Noronha"));
			updateTime(dtNoronha);
		}
	}

	private void updateTime(DateTime dt) {
		time.setText(dt.toLocalTime().toString("HH:mm:ss"));
		date.setText(dt.toLocalDate().toString("dd/MM/yyyy"));
	}

}
