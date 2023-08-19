package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {
	private static final String URL = "jdbc:mysql://localhost:3306/yenom_db";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	private static Connection connection;
	private static Statement statement;

	public DatabaseHelper() {
		if (connection == null) {
			connect();
		}
	}

	public static void connect() {
		try {
			connection = getConnection();
			if (connection != null) {
				System.out.println("Connected to the database!");
				statement = connection.createStatement();
			}

		} catch (SQLException e) {
            printSQLException(e);
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

	public static ResultSet query(String query) {
		if (statement == null)
			return null;
		try {
			return statement.executeQuery(query);
		} catch (SQLException e) {
            printSQLException(e);
		}
		return null;
	}

	public static void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}
	
    private static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
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
            }
        }
    }
}
