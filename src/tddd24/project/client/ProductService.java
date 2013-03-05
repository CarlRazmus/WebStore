package tddd24.project.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("products")
public interface ProductService extends RemoteService{
	void addProduct(String name, int price, int category, int inventory);
	ArrayList<Product> getProducts(String filter);
	ArrayList<String> getAllCategorys();
	void addAccount(String userName, String password);
	void addInventory(int productId, int amount);
	
	boolean verifyAccount(String userName, String password);
	
	boolean confirmOrder(ArrayList<Product> order);
}
