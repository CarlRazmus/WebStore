package tddd24.project.controllers;

import tddd24.project.client.WebStore;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ProductDragController extends PickupDragController{

	private WebStore webStore;
	
	public ProductDragController(AbsolutePanel boundaryPanel,
			boolean allowDroppingOnBoundaryPanel, WebStore webStore) {
		super(boundaryPanel, allowDroppingOnBoundaryPanel);
		this.webStore = webStore;
	}

	@Override
	protected Widget newDragProxy(DragContext context) {
		String name = webStore.getDraggedProduct().getName();
		return new Label(name);
	}
}
