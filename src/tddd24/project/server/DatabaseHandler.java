package tddd24.project.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class DatabaseHandler {

	private static final String DB_PATH = ClassLoader.class.getResource(
			"war/resources/WebStoreDB").getPath();
	private static final String DB_PRODUCT_TABLE = "products";

	public Connection openConnection() {
		try {
			Class.forName("org.sqlite.JDBC");

			Connection conn = DriverManager.getConnection("jdbc:sqlite:"
					+ DB_PATH);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void initiateDatabase() {
		try {
			Connection conn = openConnection();
			Statement stat = conn.createStatement();
			stat.executeUpdate("drop table if exists " + DB_PRODUCT_TABLE + ";");

			stat.executeUpdate("create table "
					+ DB_PRODUCT_TABLE
					+ " (id int, name varchar(20), price int, primary key (id));");
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertProduct(String name, int price) {
		try {
			Connection conn = openConnection();
			PreparedStatement prep = conn
					.prepareStatement("insert into " + DB_PRODUCT_TABLE + " values (?, ?);");
			prep.setString(1, name);
			prep.setInt(3, price);
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
}
