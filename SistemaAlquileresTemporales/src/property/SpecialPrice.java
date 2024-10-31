package property;

import java.util.Date;

public class SpecialPrice {
	private double price;
	private Date startDate;
	private Date endDate;

	public SpecialPrice(double price, Date startDate, Date endDate) {
		super();
		this.setPrice(price);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
	}

	public double getPrice() {
		return price;
	}

	private void setPrice(double price) {
		this.price = price;
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
