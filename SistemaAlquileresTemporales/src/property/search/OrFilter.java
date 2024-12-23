package property.search;

import java.util.List;

import property.Property;

public class OrFilter implements Filter {
	private List<Filter> filters;

	public OrFilter(List<Filter> filters) {
		this.setFilters(filters);
	}
	
	// Getters
	public List<Filter> getFilters() {
		return filters;
	}
	
	// Setters
	private void setFilters(List<Filter> filters) {
		this.filters = filters;
	}
	
	// ------------------------------------------------------
	
	@Override
	public boolean matches(Property property) {
		return this.filters.stream()
				.anyMatch(filter -> filter.matches(property));
	}

	public void addFilter(Filter filter) {
		this.getFilters().add(filter);
	}

	public void removeFilter(Filter filter) {
		this.getFilters().remove(filter);
	}
}
