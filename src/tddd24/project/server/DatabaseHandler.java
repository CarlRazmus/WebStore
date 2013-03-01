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
					+ "category_id int, "
					+ "foreign key(category_id) references "
					+ DB_CATEGORY_TABLE + " (id)," + "primary key(id));");

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addProduct(String name, int price, int category_id) {
		try {
			Connection conn = openConnection();
			PreparedStatement prep = conn.prepareStatement("insert into "
					+ DB_PRODUCT_TABLE
					+ "(name, price, category_id) values (?, ?, ?)");
			prep.setString(1, name);
			prep.setInt(2, price);
			prep.setInt(3, category_id);
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

	public void remove(int id) {
		try {
			Connection conn = openConnection();
			PreparedStatement prep = conn.prepareStatement("delete from "
					+ DB_PRODUCT_TABLE + " where id = ?");
			prep.setInt(1, id);
			prep.execute();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Product> getAll() {
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

				products.add(new Product(rs.getRow(), rs.getString("name"),
						rs.getInt("price"), category));
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

	public void setDummyData() {
		addCategory("Sports");
		addCategory("Games");
		addCategory("Electronics");
		addCategory("Other");

		addProduct("Dominion", 399, 2);
		addProduct("Football", 99, 1);
		addProduct("Coca Cola", 10, 4);
		addProduct("Headphones", 699, 3);
		addProduct("Dart Board", 399, 1);
		addProduct("Aircraft", 1000000, 4);
		addProduct("Computer", 9001, 3);
		addProduct("Jigsaw puzzle", 199, 2);
	}
}
