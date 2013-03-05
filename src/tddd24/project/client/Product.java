package tddd24.project.client;

import java.io.Serializable;

public class Product implements Serializable{
	private int id;
	private String name;
	private int price;
	private String category;
	private int inventory;
	private int inCurrentCart = 0;

	public Product()
	{
		id = -1;
		name = "unknown";
		price = -1;
		category = "unknown";
		inventory = 0;
	}
	
	public Product(int id, String name, int price, String category, int inventory) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.category = category;
		this.inventory = inventory;
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

	public int getInventory() {
		return inventory;
	}

	public void addedToCart(){
		inCurrentCart += 1;
	}
	
	public int getInCurrentCart() {
		return inCurrentCart;
	}
}
