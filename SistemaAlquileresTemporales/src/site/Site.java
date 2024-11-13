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
	private String name;
	private PropertiesManager propertiesManager;
	private RankingManager rankingManager;
	private List<Booking> bookings;
	private CommentManager commentManager;
	private SiteRegister siteRegister;

	// public Site(String name, PropertiesManager propertiesManager, RankingStrategy strategy) {
	// 	this.setName(name);
	// 	this.setPropertiesManager(propertiesManager);
	// 	this.categories = new ArrayList<Category>();
	// 	this.setBookings(new ArrayList<Booking>());
	// 	this.rankingManager = new RankingManager(strategy);
	// 	this.commentManager = new CommentManager();
	// 	this.setStatsByUser(new HashMap<>());
	// }
	
	// public Site(String name, PropertiesManager propertiesManager, RankingStrategy strategy, CommentManager commentManager) {
    //     this.setName(name);
    //     this.setPropertiesManager(propertiesManager);
    //     this.categories = new ArrayList<Category>();
    //     this.setBookings(new ArrayList<Booking>());
    //     this.rankingManager = new RankingManager(strategy);
    //     this.commentManager = commentManager;
    //     this.setStatsByUser(new HashMap<>());
    // }

	public Site(String name, SiteRegister siteRegister, PropertiesManager propertiesManager, RankingManager rankingManager) {
		this.setName(name);
		this.setSiteRegister(siteRegister);
		this.setPropertiesManager(propertiesManager);
		this.bookings = new ArrayList<Booking>();
		this.setRankingManager(rankingManager);
		this.commentManager = new CommentManager();
	}
		
	// Getters
	public String getName() {
		return name;
	}
	
	public SiteRegister getSiteRegister() {
		return this.siteRegister;
	}

	private PropertiesManager getPropertiesManager() {
		return propertiesManager;
	}

	public RankingManager getRankingManager() {
		return rankingManager;
	}
	
	private CommentManager getCommentManager() {
		return this.commentManager;
	}
	
	// Setters
	private void setName(String name) {
		this.name = name;
	}
	
	private void setSiteRegister(SiteRegister siteRegister) {
		this.siteRegister = siteRegister;
	}

	private void setPropertiesManager(PropertiesManager propertiesManager) {
		this.propertiesManager = propertiesManager;
	}

	private void setRankingManager(RankingManager rankingManager) {
		this.rankingManager = rankingManager;
	}
	
	// ------------------------------------------------------
	
	public void registerUser(User user) {
		LocalDate today = LocalDate.now();
		this.getSiteRegister().registerUser(user, today);
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
			getBookings().add(booking); 
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
		

		// esto se tiene que hacer automatico(observer?)
		Map<Category, Double> ownerAvg =this.rankingManager.calculateAvgPerCategory(owner.getRanking());
		Map<Category, Double> propertyAvg =this.rankingManager.calculateAvgPerCategory(property.getRanking());
		Map<Category, Double> tenantAvg =this.rankingManager.calculateAvgPerCategory(tenant.getRanking());
		
		double ownerTotal = this.rankingManager.calculateTotalAvg(owner.getRanking());
		double propertyTotal = this.rankingManager.calculateTotalAvg(property.getRanking());
		double tenantTotal = this.rankingManager.calculateTotalAvg(tenant.getRanking());
		
		owner.getStats().updateCategoryRating(ownerAvg);
		owner.getStats().updateTotalAvgRating(ownerTotal);
		
		property.getStats().updateCategoryRating(propertyAvg);
		property.getStats().updateTotalAvgRating(propertyTotal);
		
		tenant.getStats().updateCategoryRating(tenantAvg);
		tenant.getStats().updateTotalAvgRating(tenantTotal);
	}
	    
	public void addComment(Comment comment) {
		this.getCommentManager().addComment(comment);
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	private void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}
}
