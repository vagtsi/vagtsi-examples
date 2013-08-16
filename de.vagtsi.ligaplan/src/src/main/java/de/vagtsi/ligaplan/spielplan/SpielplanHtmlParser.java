package de.vagtsi.ligaplan.spielplan;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
 * Convert 'Spielplan' calendar html website to ics text file to be used for
 * imports into private calendars as of google calendar etc.
 * 
 * @author Jens Vagts
 */
public class SpielplanHtmlParser {
	final static Logger logger = LoggerFactory.getLogger(SpielplanHtmlParser.class);

	/** the date format to use for parsing the dates (e.g. 30.09.12) */
	private static final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
	private static final DateFormat timeFormat = new SimpleDateFormat("hh:mm");

	/** map of locations identified by it's url to parse each location only once */
	private Map<String, SpielplanLocation> locationMap = new HashMap<String, SpielplanLocation>();
	
	// public static void main(String[] args) {
	//
	// if (args.length < 1) {
	// logger.error("Please provide a source html ulr filename.");
	// return;
	// }
	// new Html2ics("").readHtmlFromSource();
	// }

	public SpielplanHtmlParser() {
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

	// ~~~~~~~~~~~~~~~~~ private ~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/**
	 * Parse the events from from the html document already opened 
	 */
	private SpielplanEvent[] parseEvents(Document doc) throws Exception {
		ArrayList<SpielplanEvent> events = new ArrayList<SpielplanEvent>();
		
		//TODO: retrieve the name of the plan
		
		//search for the table containing events
		//-> as of the table has no real 'identifier' we just look for the only
		//table having a attribute 'align' with the value 'center'
		Elements eventTables = doc.body().select("table[align=center]");
		if (eventTables.isEmpty()) {
			throw new Exception("Failed to find the event table, please check the parser rule or input file.");
		}
		Element eventTable = eventTables.first();
		logger.trace("- found event table");
		Elements eventRows = eventTable.select("tr");
		logger.trace("- found {} event rows in table", eventRows.size());
		Calendar eventTime = Calendar.getInstance(); //current (last) event date
		for (Element row : eventRows) {
			SpielplanEvent event = new SpielplanEvent();
			
			//ignore 'linebreak' row (having just one column/td with colspan)
			Elements cols = row.select("td");
			if (cols.size() <= 1) {
				continue;
			}
			
			//col1: get the date (day) of the event
			Iterator<Element> colIt = cols.iterator();
			Element col = colIt.next();
			String text = col.text().trim();
			if (text.length() <= 1) {
				//just use the date from previous row as it is a additional/following game
			} else {
				logger.trace("- date = '{}'", text);
				eventTime.setTime(dateFormat.parse(text));
				//set/initialize the time part to zero
				setTimeToCalendar(eventTime, null);
			}
			
			//col2: time
			text = colIt.next().text();
			logger.trace("- time = '{}'", text);
			if (text.equals("anschl.")) {
				//just assume one additional hour
				eventTime.set(Calendar.HOUR_OF_DAY, eventTime.get(Calendar.HOUR_OF_DAY)+1);
			} else {
				setTimeToCalendar(eventTime, timeFormat.parse(text));
			}
			logger.trace("- event date = {}", eventTime.getTime().toString());
			event.setGameTime(eventTime.getTime());
			
			//col3: location (gym)
			//retrieve the location from the link
			col = colIt.next();
			Element gymLink = col.select("a").first();
			String gymUrl = gymLink.attr("abs:href");
			logger.trace("- gymUrl = '{}'", gymUrl);
			event.setLocation(getLocation(gymUrl));
			
			//text = colIt.next().text();
			//logger.trace("- gym = '{}'", text);
			//event.setGymnasium(text);
			
			//col4: game number
			text = colIt.next().text();
			logger.trace("- game no. = '{}'", text);
			event.setGameNumber(Integer.parseInt(text));
			
			//col5: home team
			text = colIt.next().text();
			logger.trace("- home team = '{}'", text);
			event.setHomeTeamName(text);

			//col6: formatting (minus sign, ignore) 
			text = colIt.next().text();
			
			//col7: guest team
			text = colIt.next().text();
			logger.trace("- guest team = '{}'", text);
			event.setGuestTeamName(text);
			
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
		//search for the table containing the location
		//-> as of the table has no real 'identifier' we just look for the
		//child table of the second table in the body
		Elements globalTables = doc.body().select("table");
		if (globalTables.isEmpty()) {
			throw new Exception("Failed to find the tables in the body tag," +
					" please check the parser rule or input file.");
		}
		Element parentTable = globalTables.get(1);			
		Elements locationTables = parentTable.select("table");
		if (locationTables.isEmpty()) {
			throw new Exception("Failed to find the location table," +
					" please check the parser rule or input file.");
		}
		Element locationTable = locationTables.first();
		logger.trace("- found location table");
		Elements locationRows = locationTable.select("tr");
		final int minRowCount = 5;
		if (locationRows.size() < minRowCount) {
			throw new Exception(String.format(
					"Found only %s rows but need at least %s in the location table," +
					" please check the parser rule or input file.",
					locationRows.size(), minRowCount));
			
		}
		SpielplanLocation location = new SpielplanLocation();

		//location (gym) name
		Element row = locationRows.get(4);
		String text = row.select("td").first().text();
		logger.trace("- location name  = '{}'", text);
		location.setName(text);

		//address 
		row = locationRows.get(5);
		text = row.select("td").first().text();
		logger.trace("- adress = '{}'", text);
		location.setAddress(text);

		//telephone number 
		row = locationRows.get(6);
		text = row.select("td").first().text();
		logger.trace("- telephone number = '{}'", text);
		location.setTelephoneNumber(text);
		
		logger.info("Finished parsing of location '{}'", location.getName());
		
		return location;
	}

	
	/**
	 * Set the time part of given calendar
	 */
	private void setTimeToCalendar(Calendar calendar, Date time) {
		if (time == null) {
			//zero the time part of the calendar (ugly calendar api :() 
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0); 
		} else {
			Calendar timeCal = Calendar.getInstance();
			timeCal.setTime(time);
			calendar.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
			calendar.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
			calendar.set(Calendar.SECOND, timeCal.get(Calendar.SECOND));
			calendar.set(Calendar.MILLISECOND, timeCal.get(Calendar.MILLISECOND)); 
		}
	}
}
