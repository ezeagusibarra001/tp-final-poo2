package property;

public class Location {
	private String country;
	private String address;
	private String city;

	public Location(String country, String address, String city) {
		this.setCountry(country);
		this.setAddress(address);
		this.setCity(city);
	}
	
	// Getters
	public String getCountry() {
		return country;
	}

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}
	
	// Setters
	private void setCountry(String country) {
		this.country = country;
	}
	
	private void setAddress(String address) {
		this.address = address;
	}

	private void setCity(String city) {
		this.city = city;
	}
}