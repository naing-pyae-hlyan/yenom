package database;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DbHelper {
	private static final String URL = "jdbc:mysql://localhost:3306/yenom_db";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	private static Connection connection = null;

	public DbHelper() {
		if (connection == null) {
			connect();
		}
	}

	public static void connect() {
		connection = getConnection();
		if (connection == null) {
			connect();
		} else {
			System.out.println("Connected to the database!");
		}
	}

	private static Connection getConnection() {
		try {
			return DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			printSQLException(e);
		}
		return null;
	}

	public static Connection connection() {
		if (connection == null) {
			System.out.println("Re-Connecting");
			return getConnection();
		} else {
			return connection;
		}
	}


	public static void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			printSQLException(e);

		}
		System.out.println("Closed database!");
	}

	public static void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
				JOptionPane.showMessageDialog(new JPanel(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
