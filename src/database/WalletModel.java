package database;

public class WalletModel {
	private int id;
	private String name;
	private int color;
	private float totalIncome;
	private float totalExpense;

	public WalletModel(int id, String name, int color, float totalIncome, float totalExpense) {
		super();
		this.setId(id);
		this.setName(name);
		this.setColor(color);
		this.setTotalIncome(totalIncome);
		this.setTotalExpense(totalExpense);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public float getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(float totalIncome) {
		this.totalIncome = totalIncome;
	}

	public float getTotalExpense() {
		return totalExpense;
	}

	public void setTotalExpense(float totalExpense) {
		this.totalExpense = totalExpense;
	}

	@Override
	public String toString() {
		return name;
	}
}
