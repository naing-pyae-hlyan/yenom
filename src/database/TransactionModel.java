package database;

import java.sql.Date;

//https://stackoverflow.com/questions/2400955/how-to-store-java-date-to-mysql-datetime-with-jpa
public class TransactionModel {
	private int id;
	private float amount;
	private String description;
	private Date updatedDate;
	private Date createdDate;
	private int categoryId;
	private int walletId;
	private String categoryName;
	private String walletName;

	public TransactionModel(int id, float amount, String description, Date createdDate, Date updatedDate,
			int categoryId, int walletId, String categoryName, String walletName) {
		super();
		this.setId(id);
		this.setAmount(amount);
		this.setDescription(description);
		this.setCreatedDate(createdDate);
		this.setUpdatedDate(updatedDate);
		this.setCategoryId(categoryId);
		this.setWalletId(walletId);
		this.setCategoryName(categoryName);
		this.setWalletName(walletName);
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

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getWalletId() {
		return walletId;
	}

	public void setWalletId(int walletId) {
		this.walletId = walletId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getWalletName() {
		return walletName;
	}

	public void setWalletName(String walletName) {
		this.walletName = walletName;
	}

}
