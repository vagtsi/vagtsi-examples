package de.vagtsi.examples.rap.application;

import org.eclipse.ui.application.WorkbenchAdvisor;


public class RapApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

	@Override
	public String getInitialWindowPerspectiveId() {
		return "de.vagtsi.examples.rap.application.perspective";
	}

}
