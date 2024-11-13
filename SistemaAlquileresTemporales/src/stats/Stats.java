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
    
    public String showDetails() {
    	String output = "Promedio por categoría:\n";

    	for (Map.Entry<Category, Double> entry : avgRatingsPerCategory.entrySet()) {
            output += entry.getKey().getName() + ": " + entry.getValue() + "\n";
        }

        output += "Promedio total: " + this.getTotalAvg() + "\n";
        output += "Veces que fue alquilado: " + this.getTotalRentals();
        return output;
    }
}