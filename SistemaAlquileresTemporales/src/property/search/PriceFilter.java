package property.search;

import java.util.Date;

import property.Property;

public class PriceFilter implements Filter {
	private double minPrice;
	private double maxPrice;
	private Date startDate; // SE USAN LOS DATE?
	private Date endDate;

	public PriceFilter(double minPrice, double maxPrice, Date startDate, Date endDate) {
		super();
		this.setMinPrice(minPrice);
		this.setMaxPrice(maxPrice);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
	}

	// Getters
	private double getMinPrice() {
		return minPrice;
	}
	
	private double getMaxPrice() {
		return maxPrice;
	}
	
	// SON NECESARIO ESTOS DOS METODOS? EN QUE MOMENTO SE USAN?
//	private Date getEndDate() {
//		return endDate;
//	}
//	
//	private Date getStartDate() {
//		return startDate;
//	}
	
	// Setters
	private void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	private void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	private void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}
	
	private void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}
	
	// ------------------------------------------------------
	
	@Override
	public boolean matches(Property property) {
		return property.getHighestPriceBetween(startDate, endDate) <= this.getMaxPrice()
				&& property.getLowerPriceBetween(startDate, endDate) >= this.getMinPrice();
	}
}
