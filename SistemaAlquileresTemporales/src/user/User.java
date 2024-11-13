package user;

import ranking.Ranking;
import property.Property;

public abstract class User {
	// pueden cambiar a protected
	protected String fullName;
	protected String email;
	protected int phone;
	private Ranking ranking;
	
	public User(String fullName, String email, int phone) {
		this.setFullName(fullName);
		this.setEmail(email);
		this.setPhone(phone);
		this.ranking = new Ranking();
	}
	 
	// Getters
    public Ranking getRanking() {
    	return this.ranking;
    }
	// Setters
	private void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	private void setEmail(String email) {
		this.email = email;
	}
	
	private void setPhone(int phone) {
		this.phone = phone;
	}
	
	// ------------------------------------------------------
	
	public final void rateAfterCheckout(User otherUser, Property property, Ranking rankingUser, Ranking rankingProperty) {
		this.rateUser(otherUser, rankingUser);
		this.additionalRatings(property, rankingProperty);
	}
	
	private void rateUser(User user, Ranking ranking) {
		user.getRanking().addScores(ranking);
	}
	
	protected void additionalRatings(Property property, Ranking ranking) {}
}
