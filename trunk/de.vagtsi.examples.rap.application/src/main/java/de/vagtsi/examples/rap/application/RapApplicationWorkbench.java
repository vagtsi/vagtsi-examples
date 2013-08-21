package de.vagtsi.examples.rap.application;

import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.WorkbenchAdvisor;

public class RapApplicationWorkbench implements EntryPoint {
	/**
	 * @wbp.parser.entryPoint
	 */
	public int createUI() {
		Display display = PlatformUI.createDisplay();
		WorkbenchAdvisor advisor = new RapApplicationWorkbenchAdvisor();
		int result = PlatformUI.createAndRunWorkbench(display, advisor);
		return result;
	}
}
