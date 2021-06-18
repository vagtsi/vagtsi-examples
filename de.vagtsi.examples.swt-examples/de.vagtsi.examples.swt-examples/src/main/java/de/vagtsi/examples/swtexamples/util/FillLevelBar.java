package de.vagtsi.examples.swtexamples.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;

/**
 * Instances of this class are controls that display the memory used, the whole
 * memory, and contains a button to perform a GC
 */
public class FillLevelBar extends Composite {
	private Canvas bar;
	private int levelMaxSize;
	private int levelSize;
	private String unit = "MB";
	private Color barBorderColor;
	private Color barInnerColor;
	private Color barTextColor;
	private Color barGradientColorTopStart;
	private Color barGradientColorTopEnd;
	private Color barGradientColorMiddleStart;

	/**
	 * Constructs a new instance of this class given its parent and a style value
	 * describing its behavior and appearance.
	 * <p>
	 * The style value is either one of the style constants defined in class
	 * <code>SWT</code> which is applicable to instances of this class, or must be
	 * built by <em>bitwise OR</em>'ing together (that is, using the
	 * <code>int</code> "|" operator) two or more of those <code>SWT</code> style
	 * constants. The class description lists the style constants that are
	 * applicable to the class. Style bits are also inherited from superclasses.
	 * </p>
	 *
	 * @param parent a widget which will be the parent of the new instance (cannot
	 *            be null)
	 * @param style the style of widget to construct
	 *
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if the parent is null</li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the parent</li>
	 *                </ul>
	 *
	 * @see Composite#Composite(Composite, int)
	 * @see Widget#getStyle
	 */
	public FillLevelBar(final Composite parent, final int style) {
		super(parent, style);
		final GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.horizontalSpacing = gridLayout.verticalSpacing = 0;
		setLayout(gridLayout);

		createBar();
		updateContent();
		createDefaultColors();
	}

	public void setLevelMaxSize(int levelMaxSize) {
		this.levelMaxSize = levelMaxSize;
	}
	
	public void setLevelSize(int levelSize) {
		this.levelSize = levelSize;
	}
	
	/**
	 * Creates the bar that displays the level
	 */
	private void createBar() {
		bar = new Canvas(this, SWT.NONE);
		final GridData gd = new GridData(GridData.FILL, GridData.FILL, true, false);
		gd.minimumWidth = 100;
		gd.heightHint = 30;
		bar.setLayoutData(gd);
		bar.addPaintListener(e -> {
			drawBar(e);
		});
	}

	/**
	 * Draw the bar
	 *
	 * @param e {@link PaintEvent}
	 */
	private void drawBar(final PaintEvent e) {
		final GC gc = e.gc;
		final Rectangle clientArea = bar.getClientArea();

		gc.setForeground(barBorderColor);
		gc.setBackground(barInnerColor);
		gc.fillRectangle(clientArea);
		gc.drawRectangle(clientArea.x, clientArea.y, clientArea.width - 1, clientArea.height - 1);

		final float width = (clientArea.width - 2f) * levelSize / levelMaxSize;

		gc.setForeground(barGradientColorTopStart);
		gc.setBackground(barGradientColorTopEnd);
		gc.fillGradientRectangle(clientArea.x + 1, clientArea.y + 1, (int) width, clientArea.height / 2, true);

		gc.setForeground(barGradientColorMiddleStart);
		gc.setBackground(barBorderColor);
		gc.fillGradientRectangle(clientArea.x + 1, clientArea.height / 2, (int) width, clientArea.height / 2, true);

		final String message = levelSize + " " + unit + "/" + //
				levelMaxSize + " " + unit;
		final Point size = gc.stringExtent(message);

		gc.setForeground(barTextColor);
		gc.setFont(getFont());
		gc.drawText(message, (clientArea.width - size.x) / 2, (clientArea.height - size.y) / 2, true);

		gc.dispose();

	}

	/**
	 * Update the content of the bar
	 */
	private void updateContent() {
		getDisplay().timerExec(500, new Runnable() {
			@Override
			public void run() {
				levelSize = (int) (Runtime.getRuntime().totalMemory() / (1024 * 1024));
				if (!isDisposed()) {
					bar.redraw();
					if (!getDisplay().isDisposed()) {
						getDisplay().timerExec(500, this);
					}
				}
			}
		});
	}

	/**
	 * Creates the default colors
	 */
	private void createDefaultColors() {
		barTextColor = new Color(getDisplay(), 57, 98, 149);
		barInnerColor = new Color(getDisplay(), 219, 230, 243);
		barBorderColor = new Color(getDisplay(), 101, 148, 207);
		barGradientColorTopStart = new Color(getDisplay(), 175, 202, 237);
		barGradientColorTopEnd = new Color(getDisplay(), 136, 177, 229);
		barGradientColorMiddleStart = new Color(getDisplay(), 112, 161, 223);
	}
	
	@Override
	public void dispose() {
		barTextColor.dispose();
		barInnerColor.dispose();
		barBorderColor.dispose();
		barGradientColorTopStart.dispose();
		barGradientColorTopEnd.dispose();
		barGradientColorMiddleStart.dispose();
		super.dispose();
	}

	/**
	 * @return the barBorderColor
	 */
	public Color getBarBorderColor() {
		return barBorderColor;
	}

	/**
	 * @param barBorderColor the barBorderColor to set
	 */
	public void setBarBorderColor(final Color barBorderColor) {
		this.barBorderColor = barBorderColor;
	}

	/**
	 * @return the barInnerColor
	 */
	public Color getBarInnerColor() {
		return barInnerColor;
	}

	/**
	 * @param barInnerColor the barInnerColor to set
	 */
	public void setBarInnerColor(final Color barInnerColor) {
		this.barInnerColor = barInnerColor;
	}

	/**
	 * @return the barTextColor
	 */
	public Color getBarTextColor() {
		return barTextColor;
	}

	/**
	 * @param barTextColor the barTextColor to set
	 */
	public void setBarTextColor(final Color barTextColor) {
		this.barTextColor = barTextColor;
	}

	/**
	 * @return the barGradientColorTopStart
	 */
	public Color getBarGradientColorTopStart() {
		return barGradientColorTopStart;
	}

	/**
	 * @param barGradientColorTopStart the barGradientColorTopStart to set
	 */
	public void setBarGradientColorTopStart(final Color barGradientColorTopStart) {
		this.barGradientColorTopStart = barGradientColorTopStart;
	}

	/**
	 * @return the barGradientColorTopEnd
	 */
	public Color getBarGradientColorTopEnd() {
		return barGradientColorTopEnd;
	}

	/**
	 * @param barGradientColorTopEnd the barGradientColorTopEnd to set
	 */
	public void setBarGradientColorTopEnd(final Color barGradientColorTopEnd) {
		this.barGradientColorTopEnd = barGradientColorTopEnd;
	}

	/**
	 * @return the barGradientColorMiddleStart
	 */
	public Color getBarGradientColorMiddleStart() {
		return barGradientColorMiddleStart;
	}

	/**
	 * @param barGradientColorMiddleStart the barGradientColorMiddleStart to set
	 */
	public void setBarGradientColorMiddleStart(final Color barGradientColorMiddleStart) {
		this.barGradientColorMiddleStart = barGradientColorMiddleStart;
	}
}
