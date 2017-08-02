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
import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WeatherPanel extends JPanel {

	String[] countries = { "Mexico City", "New York", "Cayenne", "Noronha", "Dublin", "Paris", "Sofia", "Dubai",
			"Maldives" };
	private static final long serialVersionUID = 1L;
	private String selectedCountry;
	private JLabel selectedCountryLabel;
	private JPanel mainPanel;
	private JPanel imagePanel;
	private JLabel degreesLabel;
	GridBagConstraints gbConstraints;

	public WeatherPanel() {
		super(new BorderLayout());
		selectedCountry = "Dublin";
		createToolbar();
		createMainContent();
	}

	private void createMainContent() {
		gbConstraints = new GridBagConstraints();
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		setupAndAddSelectedCountryLabel();
		setupAndAddDegreesLabel();
		setupAndAddImagePanel();
		this.add(mainPanel, BorderLayout.CENTER);
	}

	private void setupAndAddSelectedCountryLabel() {
		selectedCountryLabel = new JLabel();
		selectedCountryLabel.setText(selectedCountry);
		selectedCountryLabel.setFont(new Font("Serif", Font.BOLD, 32));
		gbConstraints.gridy = 0;
		mainPanel.add(selectedCountryLabel, gbConstraints);
	}

	private void setupAndAddDegreesLabel() {
		degreesLabel = new JLabel(scrapeWeatherDegrees());
		degreesLabel.setFont(new Font("Serif", Font.BOLD, 32));
		gbConstraints.gridy = 1;
		mainPanel.add(degreesLabel, gbConstraints);
	}

	private void setupAndAddImagePanel() {
		imagePanel = new JPanel();
		gbConstraints.gridy = 2;
		imagePanel.add(new LoadImage(selectedCountry));
		mainPanel.add(imagePanel, gbConstraints);
	}

	private void createToolbar() {
		JToolBar weatherTools = new JToolBar();
		JLabel selectCountryLabel = new JLabel("Select Country:          ");
		weatherTools.add(selectCountryLabel);

		JComboBox<String> countrySelectionCombo = new JComboBox<String>(countries);
		countrySelectionCombo.setSelectedIndex(4);
		countrySelectionCombo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				JComboBox<String> cb = (JComboBox<String>) e.getSource();
				selectedCountry = (String) cb.getSelectedItem();
				imagePanel.removeAll();
				imagePanel.add(new LoadImage(selectedCountry));
				degreesLabel.setText(scrapeWeatherDegrees());
				selectedCountryLabel.setText(selectedCountry);
			}

		});
		weatherTools.add(countrySelectionCombo);
		this.add(weatherTools, BorderLayout.PAGE_START);

	}

	public String scrapeWeatherDegrees() {
		String url = null;
		if (selectedCountry == "Dublin") {
			url = "http://www.timeanddate.com/weather/ireland/dublin";
		} else if (selectedCountry == "Sofia") {
			url = "http://www.timeanddate.com/weather/bulgaria/sofia";
		} else if (selectedCountry == "Dubai") {
			url = "http://www.timeanddate.com/weather/united-arab-emirates/dubai";
		} else if (selectedCountry == "Paris") {
			url = "http://www.timeanddate.com/weather/france/paris";
		} else if (selectedCountry == "Cayenne") {
			url = "http://www.timeanddate.com/weather/france/cayenne";
		} else if (selectedCountry == "Noronha") {
			url = "http://www.timeanddate.com/weather/brazil/fernando-de-noronha";
		} else if (selectedCountry == "New York") {
			url = "http://www.timeanddate.com/weather/usa/new-york";
		} else if (selectedCountry == "Mexico City") {
			url = "http://www.timeanddate.com/weather/mexico/mexico-city";
		} else if (selectedCountry == "Maldives") {
			url = "http://www.timeanddate.com/weather/maldives/male";
		}
		try {
			Document document = Jsoup.connect(url).get();
			String degree = document.select(".h2").text();
			return degree.substring(0, 5);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
