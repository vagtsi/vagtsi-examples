package de.vagtsi.examples.swtexamples.util;

import java.lang.reflect.Constructor;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SwtApp {
	private static final Logger logger = LoggerFactory.getLogger(SwtApp.class);
	
	private SwtApp() {
		//utility class
	}

	public static void run(Class<?> exampleClass) {
		// create the main display with shell
		Display display = new Display();
		final Shell shell = new Shell(display);
		
		//show the window and do the message loop
		shell.open();
		try {
			Constructor<?> constructor = exampleClass.getConstructor(Shell.class);
			constructor.newInstance(shell);

			while (!shell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			logger.error("Failed to run SWT example '{}'", exampleClass.getSimpleName(), e);
		} finally {
			display.dispose();
		}
	}

}
