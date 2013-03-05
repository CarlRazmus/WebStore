package tddd24.project.widgets;

import tddd24.project.client.Product;
import tddd24.project.client.WebStore;
import tddd24.project.controllers.ProductDragController;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class ProductWidget extends DockPanel {
	private Label nameLabel;
	private ClickHandler clickHandler;

	public ProductWidget(final Product product, final WebStore webStore) {
		
		String name = product.getName();
		String price = Integer.toString(product.getPrice());
		int inventory = product.getInventory();
		String invString;
		if(inventory > 1000){
			invString = "1000+";
		}else{
			invString = Integer.toString(inventory);
		}
		
		//Name
		nameLabel = new Label(name);
		add(nameLabel, DockPanel.NORTH);
		
		//Price and inventory
		HorizontalPanel pricePanel = new HorizontalPanel();
		pricePanel.add(new Label(price + " kr"));
		
		Label inventoryLabel = new Label(invString + " in stock");
		inventoryLabel.addStyleName("inventoryLabel");
		pricePanel.add(inventoryLabel);
		
		add(pricePanel, DockPanel.SOUTH);
		Label descriptionLabel = new Label("No description");
		descriptionLabel.setWidth("100px");
		add(descriptionLabel, DockPanel.EAST);

		//Image
		Label imageLabel = new Label();
		imageLabel.setSize("100px", "100px");
		imageLabel.setText("NO IMAGE");
		imageLabel.addStyleName("imageLabel");
		add(imageLabel, DockPanel.WEST);
		setStylePrimaryName("somestyle");

		nameLabel.addMouseDownHandler(new MouseDownHandler() {
			
			@Override
			public void onMouseDown(MouseDownEvent event) {
				webStore.setCurrentProduct(product);
			}
		});
	}

	public void setDraggable(ProductDragController productDragController) {
		productDragController.makeDraggable(nameLabel);
	}
}
