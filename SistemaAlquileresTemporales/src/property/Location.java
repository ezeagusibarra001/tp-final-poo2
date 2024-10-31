package property;

public class Location {
	private String country;
	private String address;
	private String city;

	public Location(String country, String address, String city) {
		super();
		this.setCountry(country);
		this.setAddress(address);
		this.setCity(city);
	}

	public String getCountry() {
		return country;
	}

	private void setCountry(String country) {
		this.country = country;
	}

	public String getAddress() {
		return address;
	}

	private void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	private void setCity(String city) {
		this.city = city;
	}

}
