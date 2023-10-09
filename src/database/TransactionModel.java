package database;

import java.sql.Date;

//https://stackoverflow.com/questions/2400955/how-to-store-java-date-to-mysql-datetime-with-jpa
public class TransactionModel {
	private int id;
	private float amount;
	private String description;
	private Date updatedDate;
	private Date createdDate;
	private WalletModel walletModel;
	private CategoryModel categoryModel;

	public TransactionModel(int id, float amount, String description, Date createdDate, Date updatedDate,
			WalletModel walletModel, CategoryModel categoryModel) {
		super();
		this.setId(id);
		this.setAmount(amount);
		this.setDescription(description);
		this.setCreatedDate(createdDate);
		this.setUpdatedDate(updatedDate);
		this.setWalletModel(walletModel);
		this.setCategoryModel(categoryModel);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date datetime) {
		this.updatedDate = datetime;
	}


	public WalletModel getWalletModel() {
		return walletModel;
	}

	public void setWalletModel(WalletModel walletModel) {
		this.walletModel = walletModel;
	}

	public CategoryModel getCategoryModel() {
		return categoryModel;
	}

	public void setCategoryModel(CategoryModel categoryModel) {
		this.categoryModel = categoryModel;
	}

}
