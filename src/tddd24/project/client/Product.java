package tddd24.project.client;

import java.io.Serializable;

public class Product implements Serializable{
	private int id;
	private String name;
	private int price;
	private String category;

	public Product()
	{
		id = -1;
		name = "unknown";
		price = -1;
		category = "unknown";
	}
	
	public Product(int id, String name, int price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}
	
	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public int getId() {
		return id;
	}

	public String getCategory() {
		return category;
	}
}
