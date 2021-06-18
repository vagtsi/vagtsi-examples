package de.vagtsi.examples.swtexamples;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.vagtsi.examples.swtexamples.util.SwtApp;

public class TableAndExpandBarExample {
	private static final Logger logger = LoggerFactory.getLogger(TableAndExpandBarExample.class);

	public static void main(String[] args) {
		SwtApp.run(TableAndExpandBarExample.class);
	}

	public TableAndExpandBarExample(Shell shell) throws IOException, URISyntaxException {
		shell.setText("SWT Table and ExpandBar Example");
		Display display = shell.getDisplay();
		Image images[] = new Image[] { display.getSystemImage(SWT.ICON_INFORMATION),
				display.getSystemImage(SWT.ICON_ERROR), display.getSystemImage(SWT.ICON_QUESTION),
				display.getSystemImage(SWT.ICON_WARNING), };
		String[] titles = { "Information", "Error", "Question", "Warning" };
		String[] questions = { "who?", "what?", "where?", "when?", "why?" };
		shell.setLayout(new GridLayout());
		Table table = new Table(shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 200;
		table.setLayoutData(data);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(titles[i]);
			column.setImage(images[i]);
		}
		int count = 128;
		for (int i = 0; i < count; i++) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, "some info");
			item.setText(1, "error #" + i);
			item.setText(2, questions[i % questions.length]);
			item.setText(3, "look out!");
		}
		for (int i = 0; i < titles.length; i++) {
			table.getColumn(i).pack();
		}
		
		ExpandBar bar = new ExpandBar(shell, SWT.NONE);
		bar.setToolTipText("Click for showing the application log");
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
//		gridData.heightHint = 100;
		bar.setLayoutData(gridData);
		ExpandItem logItem = new ExpandItem (bar, SWT.NONE, 0);
		StyledText logview = new StyledText(bar, SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | SWT.BORDER);
		final String lineDelimiter = logview.getLineDelimiter();
		final Font mono = new Font(display, "Consolas", 10, SWT.NONE);
		logview.setFont(mono);
		logview.setEditable(false);
		logview.addDisposeListener(e -> mono.dispose());
		logItem.setText("Log");
		logItem.setHeight(200);
		logItem.setControl(logview);
		final int headerHeight = logItem.getHeaderHeight() + 4;
		bar.addListener(SWT.Expand, e ->  {
			logger.info("Item expanded!!");
			((GridData) bar.getLayoutData()).heightHint = logItem.getHeight() + headerHeight;
			bar.getParent().layout(true);
			bar.setToolTipText("Click for hiding the application log");
		});
		bar.addListener(SWT.Collapse, e ->  {
			logger.info("Item collapsed!!");
			((GridData) bar.getLayoutData()).heightHint = headerHeight;
			bar.getParent().layout(true);
			bar.setToolTipText("Click for showing the application log");
		});
	
//		byte[] logdata = Files.readAllBytes(Paths.get(this.getClass().getResource("/example.log").toURI()));
//		logview.append(new String(logdata, StandardCharsets.UTF_8));
		List<String> logdata = Files.readAllLines(Paths.get(this.getClass().getResource("/example.log").toURI()));
		for (String line : logdata) {
			logview.append(line + lineDelimiter);
		}
		
		shell.pack();
	}

}
