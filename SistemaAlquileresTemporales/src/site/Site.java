package site;

import java.util.List;
import java.util.Set;
import java.time.LocalDate;
import java.util.Date;


import ranking.Ranking;
import booking.Booking;
import booking.BookingManager;
import comment.Comment;
import comment.CommentManager;
import notification.EventListener;
import notification.EventType;
import notification.NotificationManager;
import property.PropertiesManager;
import property.Property;
import property.search.Filter;
import ranking.RankingManager;
import user.*;

public class Site {
	private String name;
	private PropertiesManager propertiesManager;
	private RankingManager rankingManager;
	private CommentManager commentManager;
	private SiteRegister siteRegister;
	private BookingManager bookingManager;
	private NotificationManager notificationManager;

	public Site(String name, SiteRegister siteRegister, PropertiesManager propertiesManager, RankingManager rankingManager) {
		this.setName(name);
		this.setSiteRegister(siteRegister);
		this.setPropertiesManager(propertiesManager);
		this.setRankingManager(rankingManager);
		this.commentManager = new CommentManager();
		this.bookingManager = new BookingManager();
		this.notificationManager = new NotificationManager();
	}
		
	// Getters
	public String getName() {
		return this.name;
	}
	
	public SiteRegister getSiteRegister() {
		return this.siteRegister;
	}

	public PropertiesManager getPropertiesManager() {
		return this.propertiesManager;
	}

	public RankingManager getRankingManager() {
		return this.rankingManager;
	}
	
	public CommentManager getCommentManager() {
		return this.commentManager;
	}
	
	public BookingManager getBookingManager() {
		return this.bookingManager;
	}
	
	public NotificationManager getNotificationManager() {
		return this.notificationManager;
	}
	
	public List<Booking> getBookings() {
		return this.getBookingManager().getBookings();
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

	//Si la propiedad está disponible se confirma la reserva, sino queda como reserva condicional
	public void requestBooking(Tenant tenant, Property property, Date checkInDate, Date checkOutDate) {
		this.getBookingManager().createBooking(this, tenant, property, checkInDate, checkOutDate);
	}
	
	// Se espera el siguiente orden: [rankingTenant, rankingOwner, rankingProperty]
	public void makeCheckout(Booking booking, List<Ranking> rankings) {
		this.getBookingManager().makeCheckout(this,booking, rankings);
	}
	
	public void cancelBooking(Booking booking) {
		this.getBookingManager().cancelBooking(this, booking);
		
	}
	
	public void addComment(Comment comment) {
		this.getCommentManager().addComment(comment);
	}

	
	// Notificaciones
	
	public void subscribeToEvent(EventType eventType, Property property, EventListener listener) {
        this.getNotificationManager().subscribe(eventType, property, listener);
    }

    public void unsubscribeFromEvent(EventType eventType, Property property, EventListener listener) {
    	this.getNotificationManager().unsubscribe(eventType, property, listener);
    }

    
    public void notifyEvent(EventType eventType, Property property) {
    	this.getNotificationManager().notify(eventType, property);
    }
    
    // Rankings

	public void updateRankings(Booking booking) {
		this.getRankingManager().updateRankings(booking);
		
	}
	
	// Estadisticas
	
    public List<User> getTopTenTenants() {
        List<User> tenants = this.getSiteRegister().getTenants();
        return this.getBookingManager().getTopTenTenants(tenants); 
    }

    public List<Property> getAllAvailableProperties() {
        return this.getPropertiesManager().getAllAvailableProperties();
    }

    public double getOccupancyRate() {
       return this.getPropertiesManager().getOccupancyRate();
    }

	


}
