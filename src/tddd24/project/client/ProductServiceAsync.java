package tddd24.project.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProductServiceAsync {
	void addProduct(String name, int price, AsyncCallback<Void> callback);
}
