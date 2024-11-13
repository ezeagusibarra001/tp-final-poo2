package stats;

import java.util.Map;
import java.util.HashMap;

import site.Category;

public class Stats {
    private int totalRentals;
    private Map<Category, Double> avgRatingsPerCategory = new HashMap<>();
    private double totalAvgRating;
    
    public Stats() {
    	this.setTotalRentals(0);
    	this.setTotalAvgRating(0);
    }
    
    // Getters
    public int getTotalRentals() {
    	return this.totalRentals;
    }
    
    public Map<Category, Double> getAvgPerCategory() {
    	return avgRatingsPerCategory;
    }
    
    public double getTotalAvg() {
    	return totalAvgRating;
    }
    
    // Setters
    private void setTotalRentals(int newTotal) {
    	this.totalRentals = newTotal;
    }
    
    private void setTotalAvgRating(double newTotal) {
    	this.totalAvgRating = newTotal;
    }
    
    private void setAvgRatingsPerCategory(Map<Category, Double> rating) {
    	this.avgRatingsPerCategory = rating;
    }
    
	// ------------------------------------------------------
    
    public void incrementRentalCount() {
    	this.totalRentals++;
    }
    
    public void updateCategoryRating(Map<Category, Double> newRatings) {
    	this.setAvgRatingsPerCategory(new HashMap<>(newRatings));
    }
    
    public void updateTotalAvgRating(double newTotal) {
    	this.setTotalAvgRating(newTotal);
    }   
    
    public void showDetails() {
    	System.out.println("Promedio por categoría:");
    	
    	for (Map.Entry<Category, Double> entry : avgRatingsPerCategory.entrySet()) {
            System.out.println(entry.getKey().getName() + ": " + entry.getValue());
        }
    	
    	System.out.println("Promedio total: " + this.getTotalAvg());
    	System.out.println("Veces que fue alquilado: " + this.getTotalRentals());
    }
}