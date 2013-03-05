package tddd24.project.server;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.RowId;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import tddd24.project.client.Product;

public class DatabaseHandler {

	private String DB_PATH = "resurces/WebStoreDB";
	private static final String DB_PRODUCT_TABLE = "products";
	private static final String DB_CATEGORY_TABLE = "categories";
	private static final String DB_ACCOUNT_TABLE = "accounts";

	public DatabaseHandler() {
		File file = new File("resources/WebStoreDB");
		DB_PATH = file.getAbsolutePath();
		initiateTables();
		setDummyData();
	}

	public Connection openConnection() {
		try {
			Class.forName("org.sqlite.JDBC");

			Connection conn = DriverManager.getConnection("jdbc:sqlite:/"
					+ DB_PATH);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void initiateTables() {
		try {
			Connection conn = openConnection();
			Statement stat = conn.createStatement();

			// Category Table
			stat.executeUpdate("drop table if exists " + DB_CATEGORY_TABLE
					+ ";");

			stat.executeUpdate("create table " + DB_CATEGORY_TABLE
					+ " (id int, name varchar(20), primary key(id));");

			// Product Table
			stat.executeUpdate("drop table if exists " + DB_PRODUCT_TABLE + ";");

			stat.executeUpdate("create table " + DB_PRODUCT_TABLE
					+ " (id int, name varchar(20), price int, "
					+ "category_id int, " + "inventory int, "
					+ "foreign key(category_id) references "
					+ DB_CATEGORY_TABLE + " (id)," + "primary key(id));");

			// Account Table
			stat.executeUpdate("drop table if exists " + DB_ACCOUNT_TABLE + ";");

			stat.executeUpdate("create table "
					+ DB_ACCOUNT_TABLE
					+ " (username varchar(20), password varchar(30), primary key(username));");
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addProduct(String name, int price, int category_id,
			int inventory) {
		try {
			Connection conn = openConnection();
			PreparedStatement prep = conn
					.prepareStatement("insert into "
							+ DB_PRODUCT_TABLE
							+ "(name, price, category_id, inventory) values (?, ?, ?, ?)");
			prep.setString(1, name);
			prep.setInt(2, price);
			prep.setInt(3, category_id);
			prep.setInt(4, inventory);
			prep.execute();

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addCategory(String name) {
		try {
			Connection conn = openConnection();
			PreparedStatement prep = conn.prepareStatement("insert into "
					+ DB_CATEGORY_TABLE + " (name) values (?)");
			prep.setString(1, name);
			prep.execute();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean addInventory(int productId, int inventoryChange) {
		try {
			Connection conn = openConnection();

			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery("select inventory from "
					+ DB_PRODUCT_TABLE + " where rowid = " + productId + ";");
			inventoryChange += rs.getInt("inventory");
			if(inventoryChange < 0)
			{
				rs.close();
				conn.close();
				return false;
			}

			PreparedStatement prep = conn.prepareStatement("update "
					+ DB_PRODUCT_TABLE + " set inventory = " + inventoryChange
					+ " where rowid = " + productId);
			prep.setInt(1, inventoryChange);
			prep.execute();
			rs.close();
			conn.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean verifyInventory(int productId, int inventoryChange) {
		try {
			Connection conn = openConnection();

			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery("select inventory from "
					+ DB_PRODUCT_TABLE + " where rowid = " + productId + ";");
			inventoryChange += rs.getInt("inventory");
			if(inventoryChange < 0)
			{
				rs.close();
				conn.close();
				return false;
			}
			rs.close();
			conn.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void addAccount(String userName, String password) {
		try {
			Connection conn = openConnection();
			PreparedStatement prep = conn.prepareStatement("insert into "
					+ DB_ACCOUNT_TABLE + " (username, password) values (?, ?)");
			prep.setString(1, userName);
			prep.setString(2, password);
			prep.execute();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove(int id) {
		try {
			Connection conn = openConnection();
			PreparedStatement prep = conn.prepareStatement("delete from "
					+ DB_PRODUCT_TABLE + " where rowid = ?");
			prep.setInt(1, id);
			prep.execute();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Product> getProducts(String filter) {
		ArrayList<Product> products = new ArrayList<Product>();
		try {
			Connection conn = openConnection();
			Statement stat = conn.createStatement();
			Statement stat2 = conn.createStatement();
			ResultSet rs = stat.executeQuery("select * from "
					+ DB_PRODUCT_TABLE + ";");

			int category_id;
			while (rs.next()) {
				category_id = rs.getInt("category_id");
				ResultSet categorySet = stat2.executeQuery("select name from "
						+ DB_CATEGORY_TABLE + " where rowid = " + category_id
						+ ";");
				categorySet.next();
				String category = categorySet.getString("name");
				if (filter == null || category.equals(filter)) {
					products.add(new Product(rs.getRow(), rs.getString("name"),
							rs.getInt("price"), category, rs.getInt("inventory")));
				}
			}
			rs.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return products;
	}

	public ArrayList<String> getAllCategorys() {
		ArrayList<String> categorys = new ArrayList<String>();
		try {
			Connection conn = openConnection();
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery("select name from "
					+ DB_CATEGORY_TABLE + ";");
			while (rs.next()) {
				categorys.add(rs.getString("name"));
			}
			rs.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categorys;
	}

	public boolean verifyAccount(String userName, String password) {
		try {
			Connection conn = openConnection();
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery("select * from "
					+ DB_ACCOUNT_TABLE + " where username = \"" + userName
					+ "\" and password = \"" + password + "\";");
			if (rs.next()) {
				rs.close();
				conn.close();
				return true;
			}
			rs.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void setDummyData() {
		addCategory("Sports");
		addCategory("Games");
		addCategory("Electronics");
		addCategory("Other");

		addProduct("Dominion", 399, 2, 10);
		addProduct("Football", 99, 1, 101);
		addProduct("Coca Cola", 10, 4, 1005);
		addProduct("Headphones", 699, 3, 3);
		addProduct("Dart Board", 399, 1, 5);
		addProduct("Aircraft", 1000000, 4, 1);
		addProduct("Computer", 9001, 3, 19);
		addProduct("Jigsaw puzzle", 199, 2, 4);

		addAccount("admin", "admin");
	}

}
