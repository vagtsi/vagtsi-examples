package de.vagtsi.examples.swtexamples;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.vagtsi.examples.swtexamples.util.SwtApp;

/**
 * Showing some images with disabled effect on buttons.
 * Used for figuring out the issues of images not displayed 'disabled' until identified the solution of image style
 *  <code>SWT.IMAGE_DISABLE</code>!.
 * 
 * @author jens
 */
public class ButtonImageExample {
	private static final Logger logger = LoggerFactory.getLogger(ButtonImageExample.class);

	public static void main(String[] args) {
		SwtApp.run(ButtonImageExample.class);
	}

	public ButtonImageExample(Shell shell) {
		shell.setText("SWT Button with Image Example");
		shell.setLayout(new FillLayout(SWT.VERTICAL));
		shell.setSize(400, 400);
		
		Image image = new Image(Display.getCurrent(), SwtApp.class.getResourceAsStream("/Duke_waving.png"));
		
		Button button2Enabled = new Button(shell, SWT.PUSH);
		button2Enabled.setAlignment(SWT.LEFT);
		button2Enabled.setText("Enabled Button with Image");
		button2Enabled.setImage(image);
		ImageData imageDate = button2Enabled.getImage().getImageData();
		logger.info("Image size is {} x {} pixels", imageDate.width, imageDate.height);
		
		Image disabledImage = new Image(Display.getCurrent(), image, SWT.IMAGE_DISABLE);
		Button button2 = new Button(shell, SWT.PUSH);
		button2.setAlignment(SWT.LEFT);
		button2.setText("Disabled Button with same inage but style 'SWT.IMAGE_DISBALE'");
		button2.setImage(disabledImage);
		button2.setEnabled(false);

		Image grayImage = new Image(Display.getCurrent(), SwtApp.class.getResourceAsStream("/Duke_waving_gray.png"));
		Button button3Enabled = new Button(shell, SWT.PUSH);
		button3Enabled.setAlignment(SWT.LEFT);
		button3Enabled.setText("Added gray color to image");
		button3Enabled.setImage(grayImage);

		Image disabledGrayImage  = new Image(Display.getCurrent(), grayImage, SWT.IMAGE_DISABLE);
		Button button3 = new Button(shell, SWT.PUSH);
		button3.setAlignment(SWT.LEFT);
		button3.setText("Disabled button with gray colored image with style 'SWT.IMAGE_DISBALE'");
		button3.setImage(disabledGrayImage);
		button3.setEnabled(false);

		shell.pack();
		
		shell.addDisposeListener(e -> {
			image.dispose();
			disabledImage.dispose();
			grayImage.dispose();
			disabledGrayImage.dispose();
		});
	}

}
