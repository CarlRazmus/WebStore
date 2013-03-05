package tddd24.project.server;

import java.util.ArrayList;

import tddd24.project.client.Product;
import tddd24.project.client.ProductService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ProductServiceImpl extends RemoteServiceServlet implements
		ProductService {

	private DatabaseHandler dbHandler;

	public ProductServiceImpl() {
		dbHandler = new DatabaseHandler();
	}

	@Override
	public void addProduct(String name, int price, int category, int inventory) {
		dbHandler.addProduct(name, price, category, inventory);
	}

	@Override
	public ArrayList<Product> getProducts(String filter) {
		return dbHandler.getProducts(filter);
	}

	@Override
	public ArrayList<String> getAllCategorys() {
		return dbHandler.getAllCategorys();
	}

	@Override
	public void addAccount(String userName, String password) {
		dbHandler.addAccount(userName, password);
	}

	@Override
	public boolean verifyAccount(String userName, String password) {
		return dbHandler.verifyAccount(userName, password);
	}

	@Override
	public boolean confirmOrder(ArrayList<Product> order) {
		//Verify
		for (Product p : order) {
			if (!dbHandler.verifyInventory(p.getId(), -p.getInCurrentCart())) {
				return false;
			}
		}
		
		//Change inventory
		for (Product p : order) {
			dbHandler.addInventory(p.getId(), -p.getInCurrentCart());
		}
		return true;
	}
}
