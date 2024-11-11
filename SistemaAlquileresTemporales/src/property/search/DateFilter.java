package property.search;

import java.util.Date;

import property.Property;

public class DateFilter implements Filter {
	private Date startDate;
	private Date endDate;

	public DateFilter(Date startDate, Date endDate) {
		this.setStartDate(startDate);
		this.setEndDate(endDate);
	}
	
	// Getters
	private Date getEndDate() {
		return endDate;
	}
	
	private Date getStartDate() {
		return startDate;
	}
	
	// Setters
	private void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	private void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	// ------------------------------------------------------

	@Override
	public boolean matches(Property property) {
		return property.isAvailableBetween(this.getStartDate(), this.getEndDate());
	}
}
