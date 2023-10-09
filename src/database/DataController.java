package database;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.PreparedStatement;
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

				System.out.println(name + "	I:" + tot_income + "	E:" + tot_expense);

				wallets.add(new WalletModel(id, name, color, tot_income, tot_expense));
			}
		} catch (SQLException e) {
			DbHelper.printSQLException(e);
		}
		System.out.println("---------------------------------------");
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
				final Date createdDate = result.getDate("created_date");
				final Date updatedDate = result.getDate("updated_date");
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

//	public static void culcWalletTotalAmount(TransactionModel tm, boolean isIncome) {
//		String columnName;set
//		if (isIncome) {
//			columnName = "total_income";
//		} else {
//			columnName = "total_expense";
//		}
//
//		String sql = "UPDATE wallet SET " + columnName + " = " + columnName + " + ? WHERE w_id = ?";
//
//		try {
//			Connection connection = DbHelper.connection();
//			PreparedStatement statement = connection.prepareStatement(sql);
//			statement.setFloat(1, tm.getAmount());
//			statement.setInt(2, tm.getWalletModel().getId());
//			statement.executeUpdate();
//
//		} catch (SQLException e) {
//			DbHelper.printSQLException(e);
//		}
//
//	}
}
