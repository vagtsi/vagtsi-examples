package de.vagtsi.ligaplan.spielplan.test;
import junit.framework.TestCase;
import de.vagtsi.ligaplan.spielplan.Spielplan2IcalWriter;
import de.vagtsi.ligaplan.spielplan.SpielplanHtmlParser;
import de.vagtsi.ligaplan.spielplan.model.SpielplanEvent;


public class Html2icsTest extends TestCase {
	public static final String Plan_Herren1_Url = "http://nvv-online.de/nvv-online/tabellen/ligascript2.php?staffelid=2503&link=spielplan&mnr=6";
	public static final String Plan_Herren2_Url = "http://nvv-online.de/nvv-online/tabellen/ligascript2.php?staffelid=3105&link=spielplan&mnr=3";
	public static final String testPlanFile_Herren1 = "/Spielplan Landesliga 3 (Männer).html";
	public static final String testPlanFile_Herren2 = "/Spielplan Bezirksliga 5 (Männer).html"; 	
	public static final String testLocationFile = "/hallen_details.html";

//	public void test_parse_location() throws Exception {
//		URL testUrl = this.getClass().getResource(testLocationFile);
//		String filePath = URLDecoder.decode(testUrl.getFile(), "utf-8");
//		SpielplanLocation location = new SpielplanHtmlParser()
//						.parseLocationFromFile(filePath, "http://nvv-online.de/");
//		assertNotNull("No location", location);
//	}
	
	public void test_create_Herren_1_ics() throws Exception {
		
		//parse events
//		URL testUrl = this.getClass().getResource(testPlanFile_Herren1);
//		String filePath = URLDecoder.decode(testUrl.getFile(), "utf-8");
		SpielplanEvent[] events = new SpielplanHtmlParser()
						.parseEventsFromUrl(Plan_Herren1_Url);
		
		//write to file
		new Spielplan2IcalWriter().createCalendarFile(
				".",
				"Spielplan 1. Herren Volleyball MTV Wilstedt 2012-2013",
				"Der Spielplan der 1. Volleyball Herren des MTV Wilstedt in der Landesliga 3 (Männer).",
				events);
	}
	
	public void test_create_Herren_2_ics() throws Exception {
		
		//parse events
//		URL testUrl = this.getClass().getResource(testPlanFile_Herren2);
//		String filePath = URLDecoder.decode(testUrl.getFile(), "utf-8");
		SpielplanEvent[] events = new SpielplanHtmlParser()
			.parseEventsFromUrl(Plan_Herren2_Url);
		
		//write to file
		new Spielplan2IcalWriter().createCalendarFile(
				".",
				"Spielplan 2. Herren Volleyball MTV Wilstedt 2012-2013",
				"Der Spielplan der 2. Volleyball Herren des MTV Wilstedt in der Bezirksliga 5 (Männer).",
				events);
		
	}
	
}


