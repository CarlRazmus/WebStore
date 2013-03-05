package tddd24.project.widgets;

import tddd24.project.client.Product;
import tddd24.project.client.WebStore;
import tddd24.project.controllers.ProductDragController;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class ProductWidget extends DockPanel {
	private Label nameLabel;
	private Label inventoryLabel;
	private int amount;

	public ProductWidget(final Product product, final WebStore webStore) {
		
		int inventory = product.getInventory();

		String invString;
		if (inventory > 1000) {
			invString = "1000+";
		} else {
			invString = Integer.toString(inventory);
		}

		// price and inventory
		String price = Integer.toString(product.getPrice());
		HorizontalPanel pricePanel = new HorizontalPanel();
		pricePanel.add(new Label(price + " kr"));
		inventoryLabel = new Label(invString + " in stock");
		inventoryLabel.addStyleName("inventoryLabel");
		pricePanel.add(inventoryLabel);
		add(pricePanel, DockPanel.SOUTH);
		initiate(product);
		
		nameLabel.addMouseDownHandler(new MouseDownHandler() {

			@Override
			public void onMouseDown(MouseDownEvent event) {
				webStore.setCurrentProduct(product);
			}
		});
		
	}

	public ProductWidget(final Product product) {

	
		// Price and inventory
		HorizontalPanel pricePanel = new HorizontalPanel();
		inventoryLabel = new Label("Ordered: " + product.getInCurrentCart());
		inventoryLabel.addStyleName("inventoryLabel");
		pricePanel.add(inventoryLabel);
		add(pricePanel, DockPanel.SOUTH);
		initiate(product);
	}

	private void initiate(Product product) {
		String name = product.getName();

		// Name
		nameLabel = new Label(name);
		add(nameLabel, DockPanel.NORTH);

		// Description
		Label descriptionLabel = new Label("No description");
		descriptionLabel.setWidth("100px");
		add(descriptionLabel, DockPanel.EAST);

		// Image
		Label imageLabel = new Label();
		imageLabel.setSize("100px", "100px");
		imageLabel.setText("NO IMAGE");
		imageLabel.addStyleName("imageLabel");
		add(imageLabel, DockPanel.WEST);
		setStylePrimaryName("somestyle");
	}

	public void setDraggable(ProductDragController productDragController) {
		productDragController.makeDraggable(nameLabel);
	}

	public void showAmount() {

	}

}
