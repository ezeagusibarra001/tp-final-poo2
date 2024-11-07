package user;

import java.util.Date;

import property.Property;
import ranking.Ranking;
import site.Category;
import site.Site;

public class Tenant extends User {
	public Tenant(String fullName, String email, int phone) {
		super(fullName, email, phone);
	}
	
	@Override
	public void additionalRatings(Property property, Ranking ranking) {
		property.getRanking().addScores(ranking);
	}

	public void requestBooking(Site site, Property property, Date checkInDate, Date checkOutDate) {
		site.requestBooking(this, property, checkInDate, checkOutDate);
	}
}
