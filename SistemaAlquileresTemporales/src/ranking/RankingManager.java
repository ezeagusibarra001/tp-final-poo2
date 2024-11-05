package ranking;

import java.util.Map;

import site.Category;

public class RankingManager {
    private RankingStrategy rankingStrategy;
    
    public RankingManager(RankingStrategy strategy) {
    	this.setRankingStrategy(strategy);
    }
    
	public double calculateTotalAvg(Ranking ranking) {
		return rankingStrategy.calculateTotalAvg(ranking);
	}
    
	public Map<Category, Double> calculateAvgPerCategory(Ranking ranking) {
		return rankingStrategy.calculateAvgPerCategory(ranking);
	}
	
	// Setters
    public void setRankingStrategy(RankingStrategy strategy) {
    	this.rankingStrategy = strategy;
    }
}
