package ranking;

import site.Category;

public interface Rankeable {
	public void addRanking(Category c, int n);
	public double getAvgRanking(Category c);
}
