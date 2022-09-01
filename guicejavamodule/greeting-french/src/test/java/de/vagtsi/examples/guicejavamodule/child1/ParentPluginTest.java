/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package de.vagtsi.examples.guicejavamodule.child1;

import org.junit.jupiter.api.Test;
import com.google.inject.Binder;
import com.google.inject.Module;

import de.vagtsi.examples.guicejavamodule.greeting.french.FrenchGreetingPlugin;

import static org.junit.jupiter.api.Assertions.*;

class ParentPluginTest {
    @Test
    void pluginTestImplementation() {
      FrenchGreetingPlugin testPlugin = new FrenchGreetingPlugin() {
        @Override
        public String id() {
          return "test-plugin";
        }
        
        @Override
        public Module module() {
          return new Module() {
            @Override
            public void configure(Binder binder) {
            }
          };
        }
      };
      
      assertNotNull(testPlugin.id(), "Plugin id is mandatory");
      assertNotNull(testPlugin.module(), "Plugin guice module is mandatory");
    }
}