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
				final int id = result.getInt("id");
				final String name = result.getString("name");
				final int color = result.getInt("color");
				final int tot_income = result.getInt("total_income");
				final int tot_expense = result.getInt("total_expense");

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
				final int id = result.getInt("id");
				final String name = result.getString("name");
				final int color = result.getInt("color");
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

		String sql = "SELECT * FROM transaction";

		try {
			Connection connection = DbHelper.connection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				final int id = result.getInt("id");
				final float amount = result.getFloat("amount");
				final String desc = result.getString("description");
				final Date createdDate = result.getDate("created_date");
				final Date updatedDate = result.getDate("updated_date");
				final int categoryId = result.getInt("category_id");
				final int walletId = result.getInt("wallet_id");
				final String categoryName = result.getString("category_name");
				final String walletName = result.getString("wallet_name");

				trans.add(new TransactionModel(id, amount, desc, createdDate, updatedDate, categoryId, walletId,
						categoryName, walletName));
			}
		} catch (SQLException e) {
			DbHelper.printSQLException(e);
		}

		return trans;
	}
}
