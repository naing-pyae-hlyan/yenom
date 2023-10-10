package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class DataController {

	public static WalletModel[] wallets() {
		return getWallets().toArray(new WalletModel[0]);
	}

	public static CategoryModel[] categories() {
		return getCategories().toArray(new CategoryModel[0]);
	}

	public static TransactionModel[] transactions() {
		return getTransactions().toArray(new TransactionModel[0]);
	}

	public static List<WalletModel> getWallets() {

		List<WalletModel> wallets = new ArrayList<>();

		String sql = "SELECT * FROM wallet";

		try {
			Connection connection = DbHelper.connection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				final int id = result.getInt("w_id");
				final String name = result.getString("w_name");
				final int color = result.getInt("w_color");
				final float tot_income = result.getInt("total_income");
				final float tot_expense = result.getInt("total_expense");

				wallets.add(new WalletModel(id, name, color, tot_income, tot_expense));
			}
		} catch (SQLException e) {
			DbHelper.printSQLException(e);
		}
		return wallets;
	}

	public static List<WalletModel> getWalletsByFilteredAmount() {
		List<WalletModel> wallets = new ArrayList<>();

		String sql = "SELECT " + "W.w_id AS \"w_id\"," + "W.w_name AS \"w_name\", " + "W.w_color AS \"w_color\","
				+ "COALESCE(SUM(CASE WHEN C.is_income = true THEN T.amount ELSE 0 END), 0) AS \"total_income\", " + ""
				+ "COALESCE(SUM(CASE WHEN c.is_income = false THEN t.amount ELSE 0 END), 0) AS \"total_expense\" " + ""
				+ "FROM wallet W " + "LEFT JOIN transaction T ON W.w_id = T.wallet_id "
				+ "LEFT JOIN category C ON T.category_id = c.c_id " + "" + "GROUP BY W.w_name, W.w_id";

		try {
			Connection connection = DbHelper.connection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				final int id = result.getInt("w_id");
				final String name = result.getString("w_name");
				final int color = result.getInt("w_color");
				final float tot_income = result.getInt("total_income");
				final float tot_expense = result.getInt("total_expense");
				wallets.add(new WalletModel(id, name, color, tot_income, tot_expense));
			}
		} catch (SQLException e) {
			DbHelper.printSQLException(e);
		}

		return wallets;
	}

	public static List<CategoryModel> getCategories() {
		List<CategoryModel> categories = new ArrayList<>();

		String sql = "SELECT * FROM category";

		try {
			Connection connection = DbHelper.connection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				final int id = result.getInt("c_id");
				final String name = result.getString("c_name");
				final int color = result.getInt("c_color");
				final boolean isIncome = result.getBoolean("is_income");

				categories.add(new CategoryModel(id, name, color, isIncome));
			}
		} catch (SQLException e) {
			DbHelper.printSQLException(e);
		}

		return categories;
	}

	public static List<TransactionModel> getTransactions() {
		List<TransactionModel> trans = new ArrayList<>();

		String sql = "SELECT * FROM transaction T " + "LEFT JOIN category C ON T.category_id = C.c_id "
				+ "LEFT JOIN wallet W ON T.wallet_id = W.w_id";

		try {
			Connection connection = DbHelper.connection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				final int id = result.getInt("t_id");
				final float amount = result.getFloat("amount");
				final String desc = result.getString("description");
				final java.sql.Timestamp createdDate = result.getTimestamp("created_date");
				final java.sql.Timestamp updatedDate = result.getTimestamp("updated_date");
				final int categoryId = result.getInt("category_id");
				final int walletId = result.getInt("wallet_id");

				final WalletModel walletModel = new WalletModel(walletId, result.getString("w_name"),
						result.getInt("w_color"), result.getFloat("total_income"), result.getFloat("total_expense"));

				final CategoryModel categoryModel = new CategoryModel(categoryId, result.getString("c_name"),
						result.getInt("c_color"), result.getBoolean("is_income"));

				trans.add(new TransactionModel(id, amount, desc, createdDate, updatedDate, walletModel, categoryModel));
			}
		} catch (SQLException e) {
			DbHelper.printSQLException(e);
		}

		return trans;
	}
}
