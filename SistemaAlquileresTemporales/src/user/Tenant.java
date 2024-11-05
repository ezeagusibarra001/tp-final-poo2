package user;

import property.Property;
import ranking.Ranking;
import site.Site;

public class Tenant extends User {
	private Ranking ranking;

	public Tenant(String fullName, String email, int phone) {
		super(fullName, email, phone);
		this.ranking = new Ranking();
	}

	public void requestBooking(Site s, Property p) {
		s.requestBooking(this, p);
	}

	// Getters y Setters
	public Ranking getRanking() { // lo necesito?
		return this.ranking;
	}
}
