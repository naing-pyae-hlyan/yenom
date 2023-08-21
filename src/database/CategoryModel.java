package database;

public class CategoryModel {
    private int id;
    private String name;
    private int color;
    private boolean isIncome;

    public CategoryModel(int id, String name, int color, boolean isIncome) {
        super();
        this.setId(id);
        this.setName(name);
        this.setColor(color);
        this.setIncome(isIncome);
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

	public boolean isIncome() {
		return isIncome;
	}

	public void setIncome(boolean isIncome) {
		this.isIncome = isIncome;
	}

    @Override
    public String toString() {
        return name;
    }

}