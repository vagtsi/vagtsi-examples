package de.vagtsi.examples.guicejavamodule.greeting.core;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.ProvidesIntoSet;

import de.vagtsi.examples.guicejavamodule.api.SimpleExtensionRegistry;
import de.vagtsi.examples.guicejavamodule.api.ExtensionRegistry;

public class GreetingCoreModule extends AbstractModule {

	@Override
	protected void configure() {
	}

	@ProvidesIntoSet
	@Singleton
	public ExtensionRegistry greetingServiceRegistry() {
		return SimpleExtensionRegistry.create(GreetingService.class);
	}

//  @Provides
//  @Singleton
//  public GreetingServiceManager greetingServiceManager(ExtensionRegistry<GreetingService> greetingServiceRegistry) {
//    return new GreetingServiceManagerImpl(greetingServiceRegistry);
//  }

}