package property.search;

import java.util.Date;

import property.Property;

public class PriceFilter implements Filter {
	private double minPrice;
	private double maxPrice;
	private Date startDate;
	private Date endDate;

	public PriceFilter(double minPrice, double maxPrice, Date startDate, Date endDate) {
		super();
		this.setMinPrice(minPrice);
		this.setMaxPrice(maxPrice);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
	}

	@Override
	public boolean matches(Property p) {
		return p.getHighestPriceBetween(startDate, endDate) <= this.getMaxPrice()
				&& p.getLowerPriceBetween(startDate, endDate) >= this.getMinPrice();
	}

	public double getMinPrice() {
		return minPrice;
	}

	private void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}

	public double getMaxPrice() {
		return maxPrice;
	}

	private void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Date getStartDate() {
		return startDate;
	}

	private void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	private void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
