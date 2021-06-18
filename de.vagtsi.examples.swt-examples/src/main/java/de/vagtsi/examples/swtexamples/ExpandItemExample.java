package de.vagtsi.examples.swtexamples;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.ToolTip;

import de.vagtsi.examples.swtexamples.util.SwtApp;

public class ExpandItemExample {
	int targetIndex = -1;
	ToolTip tip;
	
	public static void main(String[] args) {
		SwtApp.run(ExpandItemExample.class);
	}

	public ExpandItemExample(Shell shell) {
		Display display = shell.getDisplay();
		shell.setText("ExpandBar Example");
		shell.setLayout(new FillLayout());
		ExpandBar bar = new ExpandBar (shell, SWT.V_SCROLL);
		Image image = display.getSystemImage(SWT.ICON_QUESTION);

		ArrayList<Control> targets = new ArrayList<>();
		
		// First item
		Composite composite = new Composite (bar, SWT.NONE);
		GridLayout layout = new GridLayout ();
		//layout.marginLeft = layout.marginTop = layout.marginRight = layout.marginBottom = 10;
		//layout.verticalSpacing = 10;
		composite.setLayout(layout);
		Button button = new Button (composite, SWT.PUSH);
		button.setText("SWT.PUSH");
		button = new Button (composite, SWT.RADIO);
		button.setText("SWT.RADIO");
		button = new Button (composite, SWT.CHECK);
		button.setText("SWT.CHECK");
		button = new Button (composite, SWT.TOGGLE);
		button.setText("SWT.TOGGLE");
		ExpandItem item0 = new ExpandItem (bar, SWT.NONE, 0);
		item0.setText("What is your favorite button");
		item0.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		item0.setControl(composite);
		//item0.setImage(image);
		
		targets.addAll(Arrays.asList(composite.getChildren()));

		// Second item
		composite = new Composite (bar, SWT.NONE);
		layout = new GridLayout (2, false);
		layout.marginLeft = layout.marginTop = layout.marginRight = layout.marginBottom = 10;
		layout.verticalSpacing = 10;
		composite.setLayout(layout);
		Label label = new Label (composite, SWT.NONE);
		label.setImage(display.getSystemImage(SWT.ICON_ERROR));
		label = new Label (composite, SWT.NONE);
		label.setText("SWT.ICON_ERROR");
		label = new Label (composite, SWT.NONE);
		label.setImage(display.getSystemImage(SWT.ICON_INFORMATION));
		label = new Label (composite, SWT.NONE);
		label.setText("SWT.ICON_INFORMATION");
		label = new Label (composite, SWT.NONE);
		label.setImage(display.getSystemImage(SWT.ICON_WARNING));
		label = new Label (composite, SWT.NONE);
		label.setText("SWT.ICON_WARNING");
		label = new Label (composite, SWT.NONE);
		label.setImage(display.getSystemImage(SWT.ICON_QUESTION));
		label = new Label (composite, SWT.NONE);
		label.setText("SWT.ICON_QUESTION");
		label = new Label (composite, SWT.NONE);
		label.setImage(display.getSystemImage(SWT.ICON_WORKING));
		label = new Label (composite, SWT.NONE);
		label.setText("SWT.ICON_WORKING");
		ExpandItem item1 = new ExpandItem (bar, SWT.NONE, 1);
		item1.setText("What is your favorite icon");
		item1.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		item1.setControl(composite);
		item1.setImage(image);
		
		targets.addAll(Arrays.asList(composite.getChildren()));

		// Third item
		composite = new Composite (bar, SWT.NONE);
		layout = new GridLayout (2, true);
		layout.marginLeft = layout.marginTop = layout.marginRight = layout.marginBottom = 10;
		layout.verticalSpacing = 10;
		composite.setLayout(layout);
		label = new Label (composite, SWT.NONE);
		label.setText("Scale");
		new Scale (composite, SWT.NONE);
		label = new Label (composite, SWT.NONE);
		label.setText("Spinner");
		new Spinner (composite, SWT.BORDER);
		label = new Label (composite, SWT.NONE);
		label.setText("Slider");
		new Slider (composite, SWT.NONE);
		ExpandItem item2 = new ExpandItem (bar, SWT.NONE, 2);
		item2.setText("What is your favorite range widget");
		item2.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		item2.setControl(composite);
		item2.setImage(image);

		targets.addAll(Arrays.asList(composite.getChildren()));

		item1.setExpanded(true);
		bar.setSpacing(8);
		shell.setSize(400, 350);
		
		shell.addDisposeListener(e -> {
			image.dispose();
			display.dispose();
		});
		
//		final DefaultToolTip tip = new DefaultToolTip(shell, SWT.BALLOON, true);
//        tip.setText("Here is a message for the user. When the message is too long it wraps."
//        		+ " I should say something cool but nothing comes to my mind.");
//    	final ToolTip tip = new ToolTip(shell, SWT.BALLOON);
//        tip.setMessage("Here is a message for the user. When the message is too long it wraps."
//        		+ " I should say something cool but nothing comes to my mind.");

        shell.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.character == 't') {
					tip.dispose();
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.character == 't') {
					targetIndex++;
					if (targetIndex > targets.size()) {
						targetIndex = 0;
					}
					Control target = targets.get(targetIndex);
	                Point loc = target.toDisplay((target).getLocation());
					System.out.println("Set tooltip to control index " + targetIndex + " at " + loc);
//	                tip.setLocation(loc.x + target.getSize().x - target.getBorderWidth(), loc.y);
					ToolTip tip = new ToolTip(shell, SWT.BALLOON);
			        tip.setMessage("Here is a message for the user. When the message is too long it wraps."
			        		+ " I should say something cool but nothing comes to my mind.");
					tip.setLocation(loc);
					tip.setVisible(true);
//					tip.show(loc);
				}
			}
		});
		
	}
	
}
