package property.search;

import property.Property;

public class CityFilter implements Filter {
	private String city;

	public CityFilter(String city) {
		this.setCity(city);
	}
	
	private String getCity() {
		return city;
	}
	
	private void setCity(String city) {
		this.city = city;
	}

	// ------------------------------------------------------

	@Override
	public boolean matches(Property property) {
		return property.getLocation().getCity() == this.getCity();
	}
}
