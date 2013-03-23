package de.vagtsi.examples.json.springmvc;


public interface SystemInformationService {
	
	/**
	 * Retrieve some information about the (server) system.
	 * @return some information about (this) server
	 */
	SystemInfo getSystemInfo();

	String getSystemVersion();

}
