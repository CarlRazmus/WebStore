package tddd24.project.server;

import java.util.ArrayList;

import tddd24.project.client.Product;
import tddd24.project.client.ProductService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ProductServiceImpl extends RemoteServiceServlet implements ProductService{

	private DatabaseHandler dbHandler;
	
	public ProductServiceImpl()
	{
		dbHandler = new DatabaseHandler();
	}
	
	@Override
	public void addProduct(String name, int price, int category) {
		dbHandler.addProduct(name, price, category);
	}

	@Override
	public ArrayList<Product> getAll() {
		return dbHandler.getAll();
	}

	@Override
	public ArrayList<String> getAllCategorys() {
		return dbHandler.getAllCategorys();
	}
}
