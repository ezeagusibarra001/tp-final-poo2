package property;

import java.util.Date;

public class SpecialPrice {
	private double price;
	private Date startDate;
	private Date endDate;

	public SpecialPrice(double price, Date startDate, Date endDate) {
		this.setPrice(price);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
	}

	// Getters
	public double getPrice() {
		return price;
	}

	public Date getStartDate() {
		return startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	// Setters
	private void setPrice(double price) {
		this.price = price;
	}

	private void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	private void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
