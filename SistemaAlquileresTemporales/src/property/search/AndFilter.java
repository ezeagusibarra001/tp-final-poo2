package property.search;

import java.util.List;

import property.Property;

public class AndFilter implements Filter {
	private List<Filter> filters;

	public AndFilter(List<Filter> filters) {
		super();
		this.setFilters(filters);
	}

	@Override
	public boolean matches(Property p) {
		return this.filters.stream().allMatch(filter -> filter.matches(p));
	}

	public List<Filter> getFilters() {
		return filters;
	}

	private void setFilters(List<Filter> filters) {
		this.filters = filters;
	}

	public void addFilters(Filter f) {
		this.getFilters().add(f);
	}

	public void removeFilters(Filter f) {
		this.getFilters().remove(f);
	}

}
