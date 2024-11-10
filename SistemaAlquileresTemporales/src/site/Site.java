package site;

import java.util.List;

import java.util.ArrayList;
import java.util.Date;
import ranking.Ranking;
import booking.Booking;
import comment.Comment;
import comment.CommentManager;
import property.PropertiesManager;
import property.Property;
import property.search.Filter;
import ranking.RankingManager;
import ranking.RankingStrategy;
import ranking.RankingType;
import user.*;

public class Site {
	/* ATTRIBUTES */
	private String name;
	private List<Category> categories;
	private PropertiesManager propertiesManager;
	private RankingManager rankingManager;
	private List<User> users;
	private List<Booking> bookings;
	private CommentManager commentManager;

	/* CONSTRUCTOR */
	public Site(String name, PropertiesManager propertiesManager, RankingStrategy strategy) {
		this.setName(name);
		this.setPropertiesManager(propertiesManager);
		this.categories = new ArrayList<Category>();
		this.users = new ArrayList<User>();
		this.bookings = new ArrayList<Booking>();
		this.rankingManager = new RankingManager(strategy);
		this.commentManager = new CommentManager();
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

	public void requestBooking(Tenant tenant, Property property, Date checkInDate, Date checkOutDate) {
		if(property.isAvailable()) {
			Booking booking = new Booking(tenant, property.getOwner(), property, checkInDate, checkOutDate);
			booking.confirm();
			bookings.add(booking); 
		} else {
			// podria lanzar una excepcion, un mensaje o no hacer nada
		}
	}
	
	// Se espera el siguiente orden: [rankingTenant, rankingOwner, rankingProperty]
	public void makeCheckout(Booking booking, List<Ranking> rankings) {
	    if (rankings.size() != RankingType.values().length) {
	        throw new IllegalArgumentException("La cantidad de rankings no es correcta");
	    }
		booking.makeCheckout(rankings);
		
		Owner owner = booking.getOwner();
		Property property = booking.getProperty();
		Tenant tenant = booking.getTenant();
		
		this.rankingManager.calculateAvgPerCategory(owner.getRanking());
		this.rankingManager.calculateAvgPerCategory(property.getRanking());
		this.rankingManager.calculateAvgPerCategory(tenant.getRanking());
		// faltan guardar los calculos en una clase view
	}
	    
	    public void addComment(Comment comment) {
	    	this.getCommentManager().addComment(comment);
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
	
	public void setRankingStrategy(RankingStrategy strategy) {
		this.rankingManager.setRankingStrategy(strategy);
	}
	
	private CommentManager getCommentManager() {
		return this.commentManager;
	}
}
