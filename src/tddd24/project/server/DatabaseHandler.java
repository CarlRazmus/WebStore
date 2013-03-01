package tddd24.project.server;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import tddd24.project.client.Product;

public class DatabaseHandler {

	private String DB_PATH = "resurces/WebStoreDB";
	private static final String DB_PRODUCT_TABLE = "products";
	private static final String DB_CATEGORY_TABLE = "categories";

	public DatabaseHandler()  {
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
			
			//Category Table
			stat.executeUpdate("drop table if exists " + DB_PRODUCT_TABLE + ";");

			stat.executeUpdate("create table "
					+ DB_PRODUCT_TABLE
					+ " (id int primary key, name varchar(20), price int);");
			
			//Product Table
			stat.executeUpdate("drop table if exists " + DB_CATEGORY_TABLE + ";");

			stat.executeUpdate("create table "
					+ DB_CATEGORY_TABLE
					+ " (id int primary key, name varchar(20);");

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertProduct(String name, int price) {
		try {
			Connection conn = openConnection();
			PreparedStatement prep = conn
					.prepareStatement("insert into " + DB_PRODUCT_TABLE + "(name, price) values (?, ?);");
			prep.setString(1, name);
			prep.setInt(2, price);
			prep.execute();

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	public void remove(int id) {
		try {
			Connection conn = openConnection();
			PreparedStatement prep = conn
					.prepareStatement("delete from " + DB_PRODUCT_TABLE + " where id = ?");
			prep.setInt(1, id);
			prep.execute();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Product> getAll(){
		ArrayList<Product> products = new ArrayList<Product>();
		try {
			Connection conn = openConnection();
			Statement stat = conn
					.createStatement();
			ResultSet rs = stat.executeQuery("select * from " + DB_PRODUCT_TABLE + ";");
			while(rs.next())
			{
				products.add(new Product(rs.getInt("id"), rs.getString("name"), rs.getInt("price")));
			}
			rs.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return products;
	}
	
	public void setDummyData()
	{		
		insertProduct("Dominion", 399);
		insertProduct("Football", 99);
		insertProduct("Coca Cola", 10);
		insertProduct("Headphones", 699);
		insertProduct("Dart Board", 399);
		insertProduct("Aircraft", 1000000);
		insertProduct("Computer", 9001);
		insertProduct("Jigsaw puzzle", 199);
	}
}
