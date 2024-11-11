package property.search;

import java.util.List;

import property.Property;

public class AndFilter implements Filter {
	private List<Filter> filters;

	public AndFilter(List<Filter> filters) {
		this.setFilters(filters);
	}
	
	private List<Filter> getFilters() {
		return filters;
	}
	
	private void setFilters(List<Filter> filters) {
		this.filters = filters;
	}

	// ------------------------------------------------------

	@Override
	public boolean matches(Property property) {
		return this.filters.stream()
				.allMatch(filter -> filter.matches(property));
	}

	public void addFilters(Filter filter) {
		this.getFilters().add(filter);
	}

	public void removeFilters(Filter filter) {
		this.getFilters().remove(filter);
	}
}
