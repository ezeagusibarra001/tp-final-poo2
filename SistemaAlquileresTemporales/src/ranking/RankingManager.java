package ranking;

import java.util.Map;

import booking.Booking;
import property.Property;
import site.Category;
import stats.Stats;
import user.Owner;
import user.Tenant;

public class RankingManager {
    private RankingStrategy rankingStrategy;
    
    public RankingManager(RankingStrategy strategy) {
    	this.setRankingStrategy(strategy);
    }
    
    public void setRankingStrategy(RankingStrategy strategy) {
    	this.rankingStrategy = strategy;
    }
    
	// ------------------------------------------------------
    
	public double calculateTotalAvg(Ranking ranking) {
		return rankingStrategy.calculateTotalAvg(ranking);
	}
    
	public Map<Category, Double> calculateAvgPerCategory(Ranking ranking) {
		return rankingStrategy.calculateAvgPerCategory(ranking);
	}

	public void updateRankings(Booking booking) {
		
		Owner owner = booking.getOwner();
	 	Property property = booking.getProperty();
	 	Tenant tenant = booking.getTenant();
	 		
	 	Map<Category, Double> ownerAvg = this.calculateAvgPerCategory(owner.getRanking());
	 	Map<Category, Double> propertyAvg = this.calculateAvgPerCategory(property.getRanking());
	 	Map<Category, Double> tenantAvg = this.calculateAvgPerCategory(tenant.getRanking());
	 	
	 	double ownerTotal =  this.calculateTotalAvg(owner.getRanking());
	 	double propertyTotal =  this.calculateTotalAvg(property.getRanking());
	 	double tenantTotal =  this.calculateTotalAvg(tenant.getRanking());
	 		
	 	this.updateEntityRanking(owner.getStats(), ownerAvg, ownerTotal);
        this.updateEntityRanking(property.getStats(), propertyAvg, propertyTotal);
        this.updateEntityRanking(tenant.getStats(), tenantAvg, tenantTotal);
    }

    private void updateEntityRanking(Stats stats, Map<Category, Double> avgCategory, double totalAvg) {
        stats.updateCategoryRating(avgCategory);
        stats.updateTotalAvgRating(totalAvg);
    }
}
		
		
