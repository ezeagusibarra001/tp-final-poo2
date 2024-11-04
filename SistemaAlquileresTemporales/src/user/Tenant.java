package user;

import property.Property;
import site.Site;

public class Tenant extends User {

	public Tenant(String fullName, String email, int phone) {
		super(fullName, email, phone);
	}

	public void requestBooking(Site s, Property p) {
		s.requestBooking(this, p);
	}
}
