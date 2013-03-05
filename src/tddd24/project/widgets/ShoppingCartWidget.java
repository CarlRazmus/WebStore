package tddd24.project.widgets;

import java.util.ArrayList;

import tddd24.project.client.Product;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ShoppingCartWidget extends VerticalPanel {
	private Label title;
	private VerticalPanel shoppingCartArea;
	private HorizontalPanel pricePanel;
	private Label totalPrice = new Label("Total price: 0 kr");
	private Label emptyLabel = new Label(
			"Drop Products here to add them to your cart!");

	private ArrayList<Product> currentCart = new ArrayList<Product>();
	private ArrayList<Product> cartRemoveTemp = new ArrayList<Product>();

	public ArrayList<Product> GetProducts() {
		return currentCart;
	}

	public ShoppingCartWidget() {
		addStyleName("shoppingCartWidget");
		title = new Label("Your Shopping Cart: ");
		shoppingCartArea = new VerticalPanel();
		shoppingCartArea.addStyleName("shoppingCart");
		emptyLabel.addStyleName("emptyCartLabel");

		// price panel
		pricePanel = new HorizontalPanel();
		pricePanel.add(totalPrice);

		add(title);
		add(shoppingCartArea);
		add(pricePanel);

		updateCart();
	}

	public void addProductToCart(Product product) {
		for (Product p : currentCart) {
			if (p.getId() == product.getId()) {
				p.addedToCart();
				updateCart();
				return;
			}
		}
		Product newProduct = new Product(product.getId(), product.getName(),
				product.getPrice(), product.getCategory(),
				product.getInventory());
		newProduct.addedToCart();
		currentCart.add(newProduct);
		updateCart();
	}

	public void removeProductFromCart(Product p) {
		p.removedFromCart();
		updateCart();
		return;
	}

	private void updateCart() {
		shoppingCartArea.clear();
		if (currentCart.size() == 0) {
			shoppingCartArea.add(emptyLabel);
		} else {
			int price = 0;
			for (final Product p : currentCart) {
				
				if (p.getInCurrentCart() == 0) {
					cartRemoveTemp.add(p);
					price += p.getPrice() * p.getInCurrentCart();
					continue;
				}
				
				HorizontalPanel productPanel = new HorizontalPanel();
				productPanel.setWidth("100%");
				Label name = new Label(p.getName() + " x "
						+ p.getInCurrentCart());
				Button remove = new Button("x");
				remove.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						removeProductFromCart(p);
					}
				});
				
				productPanel.add(name);
				productPanel.add(remove);
				productPanel.setCellHorizontalAlignment(remove, ALIGN_RIGHT);
				shoppingCartArea.add(productPanel);
				price += p.getPrice() * p.getInCurrentCart();
			}
			
			currentCart.removeAll(cartRemoveTemp);
			cartRemoveTemp.clear();
			totalPrice.setText("Total price: " + Integer.toString(price)
					+ " kr");
		}
	}
}
