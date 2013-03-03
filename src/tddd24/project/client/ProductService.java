package tddd24.project.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("products")
public interface ProductService extends RemoteService{
	void addProduct(String name, int price);
	ArrayList<Product> getAll();
}
