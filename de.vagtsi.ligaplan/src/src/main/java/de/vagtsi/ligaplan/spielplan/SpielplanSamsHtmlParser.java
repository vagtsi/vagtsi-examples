package de.vagtsi.ligaplan.spielplan;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.vagtsi.ligaplan.spielplan.model.SpielplanEvent;
import de.vagtsi.ligaplan.spielplan.model.SpielplanLocation;

/**
 * Convert 'Spielplan' calendar html from <a href="nvv-online.de">NVV online SAMS website</a> to ics text file to be used for
 * imports into private calendars as of google calendar etc.
 * 
 * @author Jens Vagts
 * @since 0.0.2
 */
public class SpielplanSamsHtmlParser {
	final static Logger logger = LoggerFactory.getLogger(SpielplanSamsHtmlParser.class);

	/** the date format to use for parsing the dates (e.g. 30.09.12) */
	private static final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy hh:mm");

	/** map of locations identified by it's url to parse each location only once */
	private Map<String, SpielplanLocation> locationMap = new HashMap<String, SpielplanLocation>();

	/** true for running unit test and to skip some url lookups */
	private boolean testing = false;
	
	// public static void main(String[] args) {
	//
	// if (args.length < 1) {table
	// logger.error("Please provide a source html ulr filename.");
	// return;
	// }
	// new Html2ics("").readHtmlFromSource();
	// }

	public SpielplanSamsHtmlParser() {
	}
	public SpielplanSamsHtmlParser(boolean testing) {
		this.testing  = testing;
	}

	/**
	 * Parse all events from given HTML url.
	 * @param fileUrl the full url pointing to the html file
	 * 
	 * @return all events having found within given html 'Spielplan' file url
	 */
	public SpielplanEvent[] parseEventsFromUrl(String fileUrl) {
		logger.info("Parsing 'Spielplan' html file from url '{}'", fileUrl);
		try  {
			//open and read the file into document
			Document doc = Jsoup.connect(fileUrl).get();
			
			//do parse the html document
			return parseEvents(doc);
		} catch (Exception e) {
			logger.error("Failed parse events from html url '{}': {}",
					fileUrl, e.getMessage());
			return null;
		}
	}
	
	/**
	 * Parse all events from given html file.
	 * 
	 * @return all events having found within given html 'Spielplan' file
	 */
	public SpielplanEvent[] parseEventsFromFile(String fileName, String baseUrl) {
		// read given (html) source file
		logger.info("Parsing 'Spielplan' html file from '{}'", fileName);
		try {
			File calendarFile = new File(fileName);
			if (!calendarFile.exists()) {
				throw new Exception(String.format("Event file '%s' not found!", fileName));
			}
			Document doc = Jsoup.parse(calendarFile, "UTF-8", baseUrl);
			
			//do parse the html document
			return parseEvents(doc);
		} catch (Exception e) {
			logger.error("Failed parse events from html '{}': {}",
					fileName, e.getMessage());
			return null;
		}

	}

	/**
	 * Parse the location (the file is a 'matchDetails.xhtml' file containing just another url pointing
	 * to the 'locationDetails.xhtml url containin the desired address. 
	 */
	public SpielplanLocation parseLocationFromUrl(String locationUrl) {
		logger.info("Parsing location html url from '{}'", locationUrl);
		try  {
				
			Document doc = Jsoup.connect(locationUrl).get();
			return parseLocation(doc);
		} catch (Exception e) {
			logger.error("Failed parse location from html url '{}': {}",
					locationUrl, e.getMessage());
			return null;
		}
	}

	/**
	 * used for unit testing only
	 */
	public SpielplanLocation parseLocationFromFile(String fileName, String baseUrl) {
		logger.info("Parsing location html file from '{}'", fileName);
		try {
			File locationFile = new File(fileName);
			if (!locationFile.exists()) {
				throw new Exception(String.format("Location file '%s' not found!", fileName));
			}
			Document doc = Jsoup.parse(locationFile, "UTF-8", baseUrl);
			
			return parseLocation(doc);
		} catch (Exception e) {
			logger.error("Failed parse location from html '{}': {}",
					fileName, e.getMessage());
			return null;
		}
	}

	/**
	 * used for unit testing only
	 */
	public SpielplanLocation parseLocationDetailsFromFile(String fileName, String baseUrl) {
		logger.info("Parsing location *details* html file from '{}'", fileName);
		try {
			File locationFile = new File(fileName);
			if (!locationFile.exists()) {
				throw new Exception(String.format("Location details file '%s' not found!", fileName));
			}
			Document doc = Jsoup.parse(locationFile, "UTF-8", baseUrl);
			
			return parseLocationDetails(doc);
		} catch (Exception e) {
			logger.error("Failed parse location from html '{}': {}",
					fileName, e.getMessage());
			return null;
		}
	}
	
	// ~~~~~~~~~~~~~~~~~ private ~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/**
	 * Parse the events from from the html document already opened 
	 */
	private SpielplanEvent[] parseEvents(Document doc) throws Exception {
		ArrayList<SpielplanEvent> events = new ArrayList<SpielplanEvent>();
		
		//TODO: retrieve the name of the plan
		
		//search for the table containing events
		//-> as of the table has no real 'identifier' we have to look for the
		// 1. div tag having content 'Spielverlauf'
		// 2. look for the next sibling div tag and get the tabe with class 'samsDataTable'
		Elements eventTables = doc.body().select("div:contains(Spielverlauf) > div.samsContentBoxContent > table.samsDataTable");
		if (eventTables.isEmpty()) {
			throw new Exception("Failed to find the event table, please check the parser rule or input file.");
		}
		
		Element eventTable = eventTables.first();
		logger.trace("- found event table");
		Elements eventRows = eventTable.select("tr");
		logger.trace("- found {} event rows in table", eventRows.size());
		for (Element row : eventRows) {
			SpielplanEvent event = new SpielplanEvent();
			
			//ignore header row (having only th tags)
			//or any 'linebreak' row (having just one column/td with colspan)
			Elements cols = row.select("td");
			if (cols.size() <= 1) {
				continue;
			}
			
			//col1: get the date (day and time) of the event
			Iterator<Element> colIt = cols.iterator();
			Element col = colIt.next();
			String text = col.text().trim();
			logger.trace("- date = '{}'", text);
			event.setGameTime(dateFormat.parse(text));
			logger.trace("- event date = {}", event.getGameTime().toString());

			//col2: game number
			text = colIt.next().text();
			logger.trace("- game no. = '{}'", text);
			event.setGameNumber(Integer.parseInt(text));

			//col3: game *day* number
			text = colIt.next().text();
			//ignore
			
			//col4: home game flag (contains image if it's a home game, nothing if not)
			text = colIt.next().text();
			event.setHomeGame(text != null && !text.isEmpty());
			logger.trace("- game is{} a home game", (event.isHomeGame() ? "" : " *not*"));

			//col5: home team
			text = colIt.next().text();
			logger.trace("- home team = '{}'", text);
			event.setHomeTeamName(text);

			//col6: guest team
			text = colIt.next().text();
			logger.trace("- guest team = '{}'", text);
			event.setGuestTeamName(text);
			
			//col7: location (gym)
			//retrieve the location from the link
			col = colIt.next();
			Element gymLink = col.select("a").first();
			String gymUrl = gymLink.attr("abs:href");
			logger.trace("- gymUrl = '{}'", gymUrl);
			event.setLocation(getLocation(gymUrl));
			
			//add the complete event to list
			events.add(event);
		}
		
		//writer.close();
		logger.info("Finished parsing of {} events", events.size());
		
		return events.toArray(new SpielplanEvent[events.size()]);
	}
	
	/**
	 * Retrieve location from url or the cached map identified by it's url 
	 */
	private SpielplanLocation getLocation(String locationUrl) {
		//look if we already parsed  the location
		SpielplanLocation location = locationMap.get(locationUrl);
		if (location == null) {
			//parse the location
			location = parseLocationFromUrl(locationUrl);
			locationMap.put(locationUrl, location);
		}
		return location;
	}
	
	/**
	 * Parse the location from the html document already opened 
	 */
	private SpielplanLocation parseLocation(Document doc) throws Exception {
		//search for the table containing the location (just another url pointing to the location details)
		//-> as of the table has no real 'identifier' we just look for the
		//child table of the second table in the body
		Elements gameDetails = doc.body().select("div.samsContentBoxHeader:contains(Spiel) ~ div.samsContentBoxContent");
		if (gameDetails.isEmpty()) {
			throw new Exception("Failed to find the game details tag within matchDetails file ," +
					" please check the parser rule or input file.");
		}
		
		Elements locationAnchor = gameDetails.first().select("p:eq(2) > a");
		if (locationAnchor.isEmpty()) {
			throw new Exception("Failed to find location anchor within matchDetails file ," +
					" please check the parser rule or input file.");
		}
		String locationUrl = locationAnchor.first().attr("abs:href");
		logger.trace("- location details URL = {}", locationUrl);;
		
		if (testing) {
			logger.info("Skip parsing location *details* html url from '{}' as of running unit test", locationUrl);
			return new SpielplanLocation(); //return just an empty object
		}
		
		logger.info("Parsing location *details* html url from '{}'", locationUrl);
		Document locationDoc = Jsoup.connect(locationUrl).get();
		return parseLocationDetails(locationDoc);
	}

	/**
	 * Parse the location details file from given already opened document
	 */
	public SpielplanLocation parseLocationDetails(Document locationDoc) throws Exception {
		Elements locationDetails = locationDoc.body().select(
				"div.popupContentContainer > div.content > table > tbody > tr > td:eq(0) > div.samsContentBox > div.samsContentBoxContent");
		if (locationDetails.isEmpty()) {
			throw new Exception("Failed to find the location details tag in locationDetails file," +
					" please check the parser rule or input file.");
		}
		Element locationTag = locationDetails.first();
		logger.trace("- found location tag");
		SpielplanLocation location = new SpielplanLocation();

		//location (gym) name
		String text = locationTag.select("h1").first().text();
		logger.trace("- location name  = '{}'", text);
		location.setName(text);

		//address: we retrieve 'uncleaned' text via TextNode.getWholeText() to get the linebreak
		//between street and postcode
		String unformattedAddress = locationTag.select("p").get(1).textNodes().iterator().next().getWholeText();
		String[] addressLines = unformattedAddress.split("\n");
		if (addressLines.length == 0) {
			throw new Exception(String.format("Address without any newlines found in locationDetails file: adress = {}",
					unformattedAddress));
		}
		
		logger.trace("- address consists of {} lines", addressLines.length);
		StringBuilder address = new StringBuilder(addressLines[0]);
		for (int i = 1; i < addressLines.length; i++) {
			address.append(", ");
			address.append(addressLines[i].trim());
		}
		text = address.toString();
		logger.trace("- adress = '{}'", text);
		location.setAddress(text);

		//telephone number (seems to be not supported in<p> </p> SAMS anymore...) 
//		row = locationRows.get(6);
//		text = row.select("td").first().text();
//		logger.trace("- telephone number = '{}'", text);
//		location.setTelephoneNumber(text);
		
		logger.info("Finished parsing of location '{}'", location.getName());
		
		return location;
	}
}
