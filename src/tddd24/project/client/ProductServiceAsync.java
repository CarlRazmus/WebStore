package tddd24.project.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProductServiceAsync {
	void addProduct(String name, int price, int category,
			AsyncCallback<Void> callback);
	void getProducts(String filter, AsyncCallback<ArrayList<Product>> callback);
	void getAllCategorys(AsyncCallback<ArrayList<String>> callback);
}
