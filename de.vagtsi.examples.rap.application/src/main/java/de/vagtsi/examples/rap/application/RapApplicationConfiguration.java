package de.vagtsi.examples.rap.application;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.rap.rwt.application.Application;
import org.eclipse.rap.rwt.application.ApplicationConfiguration;
import org.eclipse.rap.rwt.client.WebClient;

public class RapApplicationConfiguration implements ApplicationConfiguration {
	@Override
	public void configure(Application application) {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put(WebClient.PAGE_TITLE, "The first RAP Application");
		application.addEntryPoint("/app", RapApplicationWorkbench.class, properties);
	}
}
