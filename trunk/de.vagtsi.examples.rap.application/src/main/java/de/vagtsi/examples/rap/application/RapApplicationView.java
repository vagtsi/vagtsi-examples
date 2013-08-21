package de.vagtsi.examples.rap.application;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

public class RapApplicationView extends ViewPart {

	@Override
	public void createPartControl(Composite parent) {
		Label label = new Label(parent, SWT.NONE);
		label.setText("Hello RAP World");
		label.setSize(80, 20);
	}

	@Override
	public void setFocus() {
	    // do nothing
	}

}
