package site;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

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
	private String name; // por que el site tiene name??
	private List<Category> categories;
	private PropertiesManager propertiesManager;
	private RankingManager rankingManager;
	private List<Booking> bookings;
	private CommentManager commentManager;
    private Map<User, SiteStats> statsByUser;

	public Site(String name, PropertiesManager propertiesManager, RankingStrategy strategy) {
//		this.setName(name);
		this.setPropertiesManager(propertiesManager);
		this.categories = new ArrayList<Category>();
		this.bookings = new ArrayList<Booking>();
		this.rankingManager = new RankingManager(strategy);
		this.commentManager = new CommentManager();
		this.statsByUser = new HashMap<>();
	}
		
	// Getters
//	private String getName() {
//		return name;
//	}
	
//	private List<Category> getCategories() {
//		return categories;
//	}
//	
	private PropertiesManager getPropertiesManager() {
		return propertiesManager;
	}

//	private RankingManager getRankingManager() {
//		return rankingManager;
//	}
	
	private CommentManager getCommentManager() {
		return this.commentManager;
	}
	
	// Setters
//	private void setName(String name) {
//		this.name = name;
//	}

//	private void setCategories(List<Category> categories) {
//		this.categories = categories;
//	}

	private void setPropertiesManager(PropertiesManager propertiesManager) {
		this.propertiesManager = propertiesManager;
	}

//	private void setRankingManager(RankingManager rankingManager) {
//		this.rankingManager = rankingManager;
//	}
	
	public void setRankingStrategy(RankingStrategy strategy) {
		this.rankingManager.setRankingStrategy(strategy);
	}
	
	// ------------------------------------------------------
	
	public void registerUser(User user) {
		LocalDate today = LocalDate.now();
		
		this.statsByUser.put(user, new SiteStats(user, today));
	}

	public void postProperty(Property property, Owner owner) {
		this.getPropertiesManager().post(property, owner);
	}

	public List<Property> searchProperties(List<Filter> filters) {
		return this.getPropertiesManager().search(filters);
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
}
