package user;

import java.util.Date;


import booking.Booking;
import comment.Comment;
import property.Property;
import ranking.Ranking;
import site.Site;

public class Tenant extends User {
	public Tenant(String fullName, String email, int phone) {
		super(fullName, email, phone);
	}

	public String getFullName() {
		return this.fullName;
	}

	// ------------------------------------------------------

	@Override
	public void additionalRatings(Property property, Ranking ranking) {
		property.getRanking().addScores(ranking);
	}

	public void requestBooking(Site site, Property property, Date checkInDate, Date checkOutDate) {
		site.requestBooking(this, property, checkInDate, checkOutDate);
		// si la reserva se realiza agregar propiedad a lista de propiedades alquiladas
		// implementar logica de dos pasos
		this.addProperty(property);
		
	}
	
	public void cancelBooking(Site site, Booking booking) {
		site.cancelBooking(booking);
	}
	
	public void makeComment(Site site, Property property, String content) {
		if (this.getProperties().contains(property)) {
			Comment comment = new Comment(this.getFullName(), content, property);
			site.addComment(comment);
		}
	}

	public void receiveEmail(String email) {
		
	}
}
