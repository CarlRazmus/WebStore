package tddd24.project.widgets;

import java.util.ArrayList;

import tddd24.project.client.Product;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ShoppingCartWidget extends VerticalPanel {
	private Label title;
	private VerticalPanel shoppingCartArea;
	private HorizontalPanel pricePanel;
	private Label totalPrice = new Label("Total price: 0 kr");
	private Label emptyLabel = new Label("Drop Products here to add them to your cart!");
	
	private ArrayList<Product> currentCart = new ArrayList<Product>();

	public ArrayList<Product> GetProducts()
	{
		return currentCart;
	}
	
	public ShoppingCartWidget() {
		title = new Label("Your Shopping Cart: ");
		shoppingCartArea = new VerticalPanel();
		shoppingCartArea.addStyleName("shoppingCart");
		emptyLabel.addStyleName("emptyCartLabel");
		
		//price panel
		pricePanel = new HorizontalPanel();
		pricePanel.add(totalPrice);
		
		add(title);
		add(shoppingCartArea);
		add(pricePanel);
		
		updateCart();
	}
	
	public void addProductToCart(Product product){
		currentCart.add(product);
		updateCart();
	}
	
	private void updateCart(){
		shoppingCartArea.clear();
		if(currentCart.size() == 0)
		{
			shoppingCartArea.add(emptyLabel);
		}else{
			int price = 0;
			for(Product p : currentCart){
				Label name = new Label(p.getName());
				shoppingCartArea.add(name);
				price += p.getPrice();
			}
			totalPrice.setText("Total price: " + Integer.toString(price) + " kr");
		}
	}
}
