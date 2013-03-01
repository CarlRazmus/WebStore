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
	public void addProduct(String name, int price) {
		dbHandler.insertProduct(name, price);
	}

	@Override
	public ArrayList<Product> getAll() {
		return dbHandler.getAll();
	}
}
