package property.search;

import property.Property;

public class CityFilter implements Filter {
	private String city;

	public CityFilter(String city) {
		super();
		this.setCity(city);
	}

	@Override
	public boolean matches(Property p) {
		return p.getCity() == this.getCity();
	}

	public String getCity() {
		return city;
	}

	private void setCity(String city) {
		this.city = city;
	}

}
