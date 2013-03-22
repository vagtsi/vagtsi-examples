package de.vagtsi.examples.rap.hellovirgo;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.rap.rwt.application.Application;
import org.eclipse.rap.rwt.application.ApplicationConfiguration;
import org.eclipse.rap.rwt.client.WebClient;

public class HelloWorldConfiguration implements ApplicationConfiguration {
	@Override
	public void configure(Application application) {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put(WebClient.PAGE_TITLE, "Hello Virgo, here is RAP");
		application.addEntryPoint("/hello", HelloVirgo.class, properties);
	}
}
