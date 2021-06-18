package de.vagtsi.examples.swtexamples;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import de.vagtsi.examples.swtexamples.util.SwtApp;

public class CTablFolderExample {
	public static void main(String[] args) {
		SwtApp.run(CTablFolderExample.class);
	}

	public CTablFolderExample(Shell shell) {
		shell.setText("SWT CTablFolder Example");
		shell.setLayout(new FillLayout());

		CTabFolder folder = new CTabFolder(shell, SWT.FLAT);

		CTabItem item1 = new CTabItem(folder, SWT.CLOSE);
		item1.setText("Item 1");
		Text text1 = new Text(folder, SWT.MULTI);
		text1.setText("Content for Item 1");
		item1.setControl(text1);
		folder.setSelection(0);

		CTabItem item2 = new CTabItem(folder, SWT.NONE);
		item2.setText("Item 2");
		Text text2 = new Text(folder, SWT.MULTI);
		text2.setText("Content for Item 2");
		item2.setControl(text2);

	}

}
