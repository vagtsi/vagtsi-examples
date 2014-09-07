package de.vagtsi.ligaplan.spielplan.test;

import static org.junit.Assert.assertNotNull;

import java.net.URL;
import java.net.URLDecoder;

import org.junit.Test;

import de.vagtsi.ligaplan.spielplan.Spielplan2IcalWriter;
import de.vagtsi.ligaplan.spielplan.SpielplanSamsHtmlParser;
import de.vagtsi.ligaplan.spielplan.model.SpielplanEvent;
import de.vagtsi.ligaplan.spielplan.model.SpielplanLocation;


public class Html2icsTest {
	public static final String Plan_Herren1_Url = "https://sams.nvv-online.de/SamsNvv/popup/matchSeries/teamDetails.xhtml?teamId=2805625";
	public static final String Plan_Herren2_Url = "https://sams.nvv-online.de/SamsNvv/popup/matchSeries/teamDetails.xhtml?teamId=2804682";
	public static final String testPlanFile_Herren1 = "/SAMS-Example-Mannschaftsdetails.xhtml";
	public static final String testLocationDetailsFile = "/locationDetails.xhtml";
	public static final String testLocationBaseUrl = "https://sams.nvv-online.de/SamsNvv";

	@Test
	public void create_Herren_1_ics() throws Exception {
		
		//parse events
//		URL testUrl = this.getClass().getResource(testPlanFile_Herren1);
//		String filePath = URLDecoder.decode(testUrl.getFile(), "utf-8");
		SpielplanEvent[] events = new SpielplanSamsHtmlParser()
						.parseEventsFromUrl(Plan_Herren1_Url);
//						.parseEventsFromFile(filePath, testLocationBaseUrl);
		
		//write to file
		new Spielplan2IcalWriter().createCalendarFile(
				".",
				"Spielplan 1. Herren Volleyball MTV Wilstedt 2014-2015",
				"Der Spielplan der 1. Volleyball Herren des MTV Wilstedt in der Landesliga 3 (Männer).",
				events);
	}
	
	@Test
	public void create_Herren_2_ics() throws Exception {
		
		//parse events
		SpielplanEvent[] events = new SpielplanSamsHtmlParser()
			.parseEventsFromUrl(Plan_Herren2_Url);
		
		//write to file
		new Spielplan2IcalWriter().createCalendarFile(
				".",
				"Spielplan 2. Herren Volleyball MTV Wilstedt 2014-2015",
				"Der Spielplan der 2. Volleyball Herren des MTV Wilstedt in der Bezirksliga 5 (Männer).",
				events);
		
	}

	@Test
	public void parse_locationDetails() throws Exception {
		URL testUrl = this.getClass().getResource(testLocationDetailsFile);
		String filePath = URLDecoder.decode(testUrl.getFile(), "utf-8");
		SpielplanLocation location = new SpielplanSamsHtmlParser()
						.parseLocationDetailsFromFile(filePath, "http://sams.nvv-online.de/");
		assertNotNull("No location", location);
	}
	
}


