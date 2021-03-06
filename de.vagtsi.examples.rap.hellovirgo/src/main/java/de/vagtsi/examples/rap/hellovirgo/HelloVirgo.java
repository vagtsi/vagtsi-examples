package de.vagtsi.examples.rap.hellovirgo;

import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class HelloVirgo implements EntryPoint {
	private String buttonText = "+1";

	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public int createUI() {
		Display display = new Display();
		Shell mainShell = new Shell(display, SWT.TITLE);
		mainShell.setText("RAP Hello World!");
		mainShell.setMaximized(true);
		mainShell.setLayout(new FillLayout());

		Composite parent = new Composite(mainShell, SWT.BORDER);
		parent.setLayout(new GridLayout());

		Button button = new Button(parent, SWT.PUSH);
		button.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
		button.setText(buttonText);

		final Label label = new Label(parent, SWT.NONE);
		label.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
		label.setText("No event yet.");
		button.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;
			int counter = 0;

			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				label.setText("Clicked" + " [" + ++counter + "]");
			}
		});

		mainShell.layout();
		mainShell.open();
		return 0;
	}
}
