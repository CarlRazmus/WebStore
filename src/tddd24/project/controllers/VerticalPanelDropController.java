package tddd24.project.controllers;

import tddd24.project.client.Product;
import tddd24.project.client.WebStore;
import tddd24.project.widgets.ShoppingCartWidget;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.drop.AbstractDropController;
import com.google.gwt.user.client.ui.Widget;

public class VerticalPanelDropController extends AbstractDropController{

	private ShoppingCartWidget shoppingCart;
	private WebStore webStore;
	
	public VerticalPanelDropController(Widget dropTarget, WebStore webStore) {
		super(dropTarget);
		this.webStore = webStore;
		shoppingCart = (ShoppingCartWidget) dropTarget;
	}
	
	@Override
	public void onDrop(DragContext context) {
		shoppingCart.addProductToCart(webStore.getDraggedProduct());
	}
}
