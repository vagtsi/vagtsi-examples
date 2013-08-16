package de.vagtsi.ligaplan.spielplan;

import java.io.File;
import java.io.FileOutputStream;
import java.net.SocketException;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.model.property.XProperty;
import net.fortuna.ical4j.util.UidGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.vagtsi.ligaplan.spielplan.model.SpielplanEvent;

/**
 * Exporting {@link SpielplanEvent} into a ical (ics) file.
 * 
 * @author Jens Vagts
 */
public class Spielplan2IcalWriter {
	final static Logger logger = LoggerFactory.getLogger(Spielplan2IcalWriter.class);
	
	/** the time zone for Germany (as of dates retrieved from the 'Spielplan') */
	private static final String TIMEZONE_ID_GERMANY = "Europe/Berlin";

	public Spielplan2IcalWriter() {
	}
	
	public void createCalendarFile(String filename, String calendarName, String calendarDescription, SpielplanEvent[] events)
			throws Exception {
		File calendarFile = new File(filename);
		if (calendarFile.isDirectory()) {
			calendarFile = new File(filename, calendarName + ".ics");
		}
		logger.trace("Creating calendar file '{}' from {} events", 
				calendarFile.getAbsolutePath(), events.length);

		//create the calendar
		Calendar calendar = createCalendar(calendarName, calendarDescription, events);
		
		//write it to file
		FileOutputStream fout = new FileOutputStream(calendarFile.getAbsolutePath());
		CalendarOutputter outputter = new CalendarOutputter();
		outputter.output(calendar, fout);
		
		logger.info("Successfully created new calendar file '{}' with {} events", 
				calendarFile.getAbsolutePath(), events.length);
	}
	
	public Calendar createCalendar(String calendarName, String calendarDescription, SpielplanEvent[] events) throws Exception {
		logger.trace("Writing {} events to new ical calendar {}", events.length, calendarName);
		
		//create the calendar with some properties
		Calendar calendar = new Calendar();
		calendar.getProperties().add(new ProdId("-//Jens Vagts//Volleyball Spielplan 1.0//DE"));
		calendar.getProperties().add(Version.VERSION_2_0);
		calendar.getProperties().add(CalScale.GREGORIAN);
		if (calendarName != null) {
			calendar.getProperties().add(new XProperty("X-WR-CALNAME", calendarName));
		}
		calendar.getProperties().add(new XProperty("X-WR-TIMEZONE", "Europe/Berlin"));
		if (calendarName != null) {
			calendar.getProperties().add(new XProperty("X-WR-CALDESC", calendarDescription));
		}
		
		//create UUID generator
		UidGenerator ug;
		try {
			ug = new UidGenerator("1");
		} catch (SocketException e) {
			throw new Exception(String.format("Failed to create UID generator: %s", e.getMessage()));
		}
		
		//create a TimeZone to be added to each time and meeting
		TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
		TimeZone timezone = registry.getTimeZone(TIMEZONE_ID_GERMANY);
		if (timezone == null) {
			throw new Exception(String.format("Timezone '%s' not found in registry", TIMEZONE_ID_GERMANY));
		}
		VTimeZone tz = timezone.getVTimeZone();
		//don't adding time zone to calendar
		//as of seems not necessary and blows up the resulting calendar file
		//calendar.getComponents().add(tz);
		
		//add all events to the calendar
		for (SpielplanEvent event : events) {
			DateTime start = new DateTime(event.getGameTime());
			start.setTimeZone(timezone);
			//DateTime end = new DateTime(endDate.getTime());
			VEvent meeting = new VEvent(start, new Dur(0, 4, 0, 0),
					String.format("%s - %s (#%s)",
							event.getHomeTeamName(),
							event.getGuestTeamName(),
							event.getGameNumber()));
			meeting.getProperties().add(tz.getTimeZoneId());
			
			//add the location
			if (event.getLocation() != null) {
				meeting.getProperties().add(new Location(event.getLocation().toString()));
			}
			
			// Generate a UID for the event..
			meeting.getProperties().add(ug.generateUid());

			//add meeting/event to he calendar
			calendar.getComponents().add(meeting);
		}
		
		return calendar;
	}
	
}
