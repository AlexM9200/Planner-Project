/*
 * 
 * Alex Matthews
 * Software Development Student @ Institute of Technology Carlow, Ireland
 * 2017
 *
 */

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class LoadImage extends JComponent {
	private static final long serialVersionUID = 1L;
	BufferedImage img;
	String selectedImage;

	public LoadImage(String country) {
		try {
			selectedImage = scrapeWeather(country);
			URL url1 = new URL("http:" + selectedImage);
			img = ImageIO.read(url1);
		} catch (IOException e) {
			System.out.println("An unexpected error occured.");
		}

	}
	
	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, null);

	}

	public String scrapeWeather(String country) throws IOException {
		String url = getUrl(country);
		Document document = Jsoup.connect(url).get();
		Element imgURL = document.getElementById("cur-weather");
		String imgURLString = imgURL.toString();
		Pattern pattern = Pattern.compile("src=\"(.*?)\"");
		Matcher matcher = pattern.matcher(imgURLString);
		if (matcher.find()) {
			return matcher.group(1);
		} else {
			return null;
		}
	}

	private String getUrl(String country) {
		if (country == "Dublin") {
			return "http://www.timeanddate.com/weather/ireland/dublin";
			
		} else if (country == "Sofia") {
			return "http://www.timeanddate.com/weather/bulgaria/sofia";
			
		} else if (country == "Dubai") {
			return"http://www.timeanddate.com/weather/united-arab-emirates/dubai";
			
		} else if (country == "Paris") {
			return"http://www.timeanddate.com/weather/france/paris";
			
		} else if (country == "Cayenne") {
			return "http://www.timeanddate.com/weather/france/cayenne";
			
		} else if (country == "Noronha") {
			return "http://www.timeanddate.com/weather/brazil/fernando-de-noronha";
			
		} else if (country == "New York") {
			return "http://www.timeanddate.com/weather/usa/new-york";
			
		} else if (country == "Mexico City") {
			return "http://www.timeanddate.com/weather/mexico/mexico-city";
			
		} else if (country == "Maldives") {
			return "http://www.timeanddate.com/weather/maldives/male";
		}
		return null;
	}

	public Dimension getPreferredSize() {
		if (img == null) {
			return new Dimension(100, 100);
		} else {
			return new Dimension(img.getWidth(null), img.getHeight(null));
		}
	}

}