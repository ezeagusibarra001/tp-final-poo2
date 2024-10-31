package property.search;

import java.util.Date;

import property.Property;

public class DateFilter implements Filter {
	private Date startDate;
	private Date endDate;

	public DateFilter(Date startDate, Date endDate) {
		super();
		this.setStartDate(startDate);
		this.setEndDate(endDate);
	}

	@Override
	public boolean matches(Property p) {
		return p.isAvailableBetween(this.getStartDate(), this.getEndDate());
	}

	public Date getEndDate() {
		return endDate;
	}

	private void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	private void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

}
