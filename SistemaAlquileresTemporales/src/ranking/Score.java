package ranking;

import site.Category;

public class Score {
	private Category category;
	private int value;
	
	public Score(Category category, int value) {
		this.setCategory(category);
		this.setValue(value);
	}
	
	// Getters
	public int getValue() {
		return this.value;
	}
	
	public Category getCategory() {
		return this.category;
	}
	
	// Setters
	private void setValue(int value) {
		this.value = value;
	}
	
	private void setCategory(Category category) {
		this.category = category;
	}
}
