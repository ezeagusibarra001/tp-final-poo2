package site;

import java.util.List;
import booking.Booking; 

import property.PropertiesManager;
import property.Property;
import property.search.Filter;
import ranking.Rankeable;
import ranking.RankingManager;
import user.*;

public class Site {
	/* ATTRIBUTES */
	private String name;
	private List<Category> categories;
	private PropertiesManager propertiesManager;
	private RankingManager rankingManager;
	private List<User> users;

	/* CONSTRUCTOR */
	public Site(String name, PropertiesManager propertiesManager, RankingManager rankingManager) {
		super();
		this.setName(name);
		this.setPropertiesManager(propertiesManager);
		this.setRankingManager(rankingManager);
	}

	/* METHODS */
	public void registerUser(User u) {
		this.users.add(u);
	}

	public void postProperty(Property p, Owner o) {
		this.getPropertiesManager().post(p, o);
	}

	public List<Property> searchProperties(List<Filter> fs) {
		return this.getPropertiesManager().search(fs);
	}

	public void rank(Rankeable r, Category c, int n) {
		r.addRanking(c, n);
	}
	
	public void requestBooking(Tenant t, Property p) {
		if(p.isAvailable()) {
			Booking b = new Booking(t, p.getOwner(), p);
		}
	}

	/* GETTERS & SETTERS */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public PropertiesManager getPropertiesManager() {
		return propertiesManager;
	}

	public void setPropertiesManager(PropertiesManager propertiesManager) {
		this.propertiesManager = propertiesManager;
	}

	public RankingManager getRankingManager() {
		return rankingManager;
	}

	public void setRankingManager(RankingManager rankingManager) {
		this.rankingManager = rankingManager;
	}

	public List<User> getUsers() {
		return users;
	}
}
