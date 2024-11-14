package user;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import property.Property;
import ranking.Ranking;
import stats.Stats;

public abstract class User {
	protected String fullName;
	protected String email;
	protected int phone;
	private Ranking ranking;
	private Stats stats;
	private Set<Property> properties = new HashSet<Property>();
	private LocalDate registerDate;
	
	public User(String fullName, String email, int phone) {
		this.setFullName(fullName);
		this.setEmail(email);
		this.setPhone(phone);
		this.ranking = new Ranking();
		this.stats = new Stats();
	}
	 
	// Getters
    public Ranking getRanking() {
    	return this.ranking;
    }
    
    public Stats getStats() {
    	return this.stats;
    }
	
    protected Set<Property> getProperties() {
		 return this.properties;
	 }
    
    public LocalDate getRegisterDate() {
    	return this.registerDate;
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
	
	public void setRegisterDate(LocalDate date) {
		this.registerDate = date;
	}
	
	// ------------------------------------------------------
	
	public void addProperty(Property property) {
		this.properties.add(property);
	}
	
	public final void rateAfterCheckout(User otherUser, Property property, Ranking rankingUser, Ranking rankingProperty) {
		this.rateUser(otherUser, rankingUser);
		this.additionalRatings(property, rankingProperty);
	}
	
	private void rateUser(User user, Ranking ranking) {
		user.getRanking().addScores(ranking);
	}
	
	protected void additionalRatings(Property property, Ranking ranking) {}
}
