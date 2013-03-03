package tddd24.project.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProductServiceAsync {
	void addProduct(String name, int price, AsyncCallback<Void> callback);
	void getAll(AsyncCallback<ArrayList<Product>> callback);
}
