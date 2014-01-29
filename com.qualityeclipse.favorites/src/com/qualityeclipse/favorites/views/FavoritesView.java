package com.qualityeclipse.favorites.views;

import java.util.Comparator;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;

import com.qualityeclipse.favorites.model.FavoritesManager;
import com.qualityeclipse.favorites.model.FavoritesViewSorter;
import com.qualityeclipse.favorites.model.IFavoriteItem;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view
 * shows data obtained from the model. The sample creates a dummy model on the
 * fly, but a real implementation would connect to the model available either in
 * this or another plug-in (e.g. the workspace). The view is connected to the
 * model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be
 * presented in the view. Each view can present the same model objects using
 * different labels and icons, if needed. Alternatively, a single label provider
 * can be shared between views in order to ensure that objects of the same type
 * are presented in the same way everywhere.
 * <p>
 */

public class FavoritesView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.qualityeclipse.favorites.views.FavoritesView";

	private TableViewer viewer;
	private TableColumn typeColumn;
	private TableColumn nameColumn;
	private TableColumn locationColumn;
	private FavoritesViewSorter sorter;

	/**
	 * The constructor.
	 */
	public FavoritesView() {
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION);
		final Table table = viewer.getTable();
		typeColumn = new TableColumn(table, SWT.LEFT);
		typeColumn.setText("");
		typeColumn.setWidth(18);

		nameColumn = new TableColumn(table, SWT.LEFT);
		nameColumn.setText("name");
		nameColumn.setWidth(200);

		locationColumn = new TableColumn(table, SWT.LEFT);
		locationColumn.setText("Location");
		locationColumn.setWidth(450);

		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		viewer.setContentProvider(new FavoritesViewContentProvider());
		viewer.setLabelProvider(new FavoritesViewLabelProvider());
		viewer.setInput(FavoritesManager.getManager());
		createTableSorter();

	}

	private void createTableSorter() {
		Comparator<IFavoriteItem> nameComparator = new Comparator<IFavoriteItem>() {

			@Override
			public int compare(IFavoriteItem o1, IFavoriteItem o2) {
				return o1.getName().compareTo(o2.getName());
			}
		};
		Comparator<IFavoriteItem> locationComparator = new Comparator<IFavoriteItem>() {

			@Override
			public int compare(IFavoriteItem o1, IFavoriteItem o2) {
				return o1.getLocation().compareTo(o2.getLocation());
			}
		};
		Comparator<IFavoriteItem> typeComparator = new Comparator<IFavoriteItem>() {

			@Override
			public int compare(IFavoriteItem o1, IFavoriteItem o2) {
				return o1.getType().compareTo(o2.getType());
			}
		};
		sorter = new FavoritesViewSorter(viewer, new TableColumn[] {
				nameColumn, locationColumn, typeColumn }, new Comparator[] {
				nameComparator, locationComparator, typeComparator });
		viewer.setSorter(sorter);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}