package de.vagtsi.examples.rap.application;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class RapApplicationPerspective implements IPerspectiveFactory {

	@Override
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);
		IFolderLayout left = layout.createFolder("left", IPageLayout.LEFT,
				0.25f, editorArea);
		left.addView("de.vagtsi.examples.rap.application.view");
	}

}
