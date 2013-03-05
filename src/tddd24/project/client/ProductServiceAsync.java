package tddd24.project.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProductServiceAsync {
	void addProduct(String name, int price, int category, int inventory,
			AsyncCallback<Void> callback);
	void getProducts(String filter, AsyncCallback<ArrayList<Product>> callback);
	void getAllCategorys(AsyncCallback<ArrayList<String>> callback);
	void addAccount(String userName, String password,
			AsyncCallback<Void> callback);
	void verifyAccount(String userName, String password,
			AsyncCallback<Boolean> callback);
	void confirmOrder(ArrayList<Product> order, AsyncCallback<Boolean> callback);
	void addInventory(int productId, int amount, AsyncCallback<Void> callback);
}
