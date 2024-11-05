package ranking;

import java.util.Map;
import site.Category;

public interface RankingStrategy {
	public double calculateTotalAvg(Ranking ranking);
	public Map<Category, Double> calculateAvgPerCategory(Ranking ranking);
}
