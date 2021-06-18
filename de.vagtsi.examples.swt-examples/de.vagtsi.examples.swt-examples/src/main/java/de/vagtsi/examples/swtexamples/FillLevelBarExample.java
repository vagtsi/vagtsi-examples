package de.vagtsi.examples.swtexamples;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import de.vagtsi.examples.swtexamples.util.FillLevelBar;
import de.vagtsi.examples.swtexamples.util.SwtApp;

public class FillLevelBarExample {
	public static void main(String[] args) {
		SwtApp.run(FillLevelBarExample.class);
	}

	public FillLevelBarExample(Shell shell) {
		shell.setText("SWT Fill Level Bar Example");
		shell.setLayout(new GridLayout(2, false));

		Label label = new Label(shell, SWT.NONE);
		label.setText("Memory");
		label.setLayoutData(new GridData(SWT.CENTER,  SWT.CENTER, false, false));
		FillLevelBar memoryBar = new FillLevelBar(shell, SWT.NONE);
		int totalMemory = (int) (Runtime.getRuntime().maxMemory() / (1024 * 1024));
		int freeMemory = (int) (Runtime.getRuntime().freeMemory() / (1024 * 1024));
		memoryBar.setLevelSize(totalMemory - freeMemory);
		memoryBar.setLevelMaxSize(totalMemory);
		memoryBar.setLayoutData(new GridData(SWT.FILL,  SWT.FILL, true, true));
		
//		memoryBar.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_CYAN));
		shell.pack();
	}

}
