package booking;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import notification.EventType;
import user.Tenant;
import user.User;
import property.Property;
import ranking.Ranking;
import ranking.RankingType;
import site.Site;

import java.time.LocalDate;
import java.time.ZoneId;

public class BookingManager {
	
	private List<Booking> bookings;
	private Map<Property, BookingQueue> waitingList;

	
	// Constructor
	public BookingManager() {
		this.setBookings(new ArrayList<>());
		this.setWaitingList(new HashMap<>());
		
	}
	
	// Getters and setters
    private void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}
    
    public List<Booking> getBookings() {
    	return this.bookings;
    }
    

	public Map<Property, BookingQueue> getWaitingList() {
		return waitingList;
	} 
	public BookingQueue getWaitingListOf(Property property) {
		return this.getWaitingList().computeIfAbsent(property, k -> new BookingQueue(new LinkedList<>(), this));
	}

	public void setWaitingList(Map<Property, BookingQueue> waitingList) {
		this.waitingList = waitingList;
	}

    // Methods
    
	public void createBooking(Site site, Tenant tenant, Property property, Date checkInDate, Date checkOutDate) {
	    Booking booking = new Booking(tenant, property.getOwner(), property, checkInDate, checkOutDate);
	    
	    if (property.isAvailableBetween(checkInDate, checkOutDate)) {
	        booking.confirm();
	        this.addBooking(booking);
	    } else {
	        this.addToWaitingListOf(property, booking);
	        site.subscribeToEvent(EventType.PROPERTY_CANCELLATION, property, this.getWaitingListOf(property));
	    }
	}
	
	public void addToWaitingListOf(Property property, Booking booking) {
		this.getWaitingListOf(property).addConditionalBooking(booking);		
	}

    public void addBooking(Booking booking) {
		this.getBookings().add(booking);
	}
    
 // Se espera el siguiente orden: [rankingTenant, rankingOwner, rankingProperty]
 	public void makeCheckout(Site site, Booking booking, List<Ranking> rankings) {
 	    if (rankings.size() != RankingType.values().length) {
 	        throw new IllegalArgumentException("La cantidad de rankings no es correcta");
 	    } 	  
 	    
 		booking.makeCheckout(rankings);
 		
 		site.updateRankings(booking);
 	
 	}

    public void cancelBooking(Site site, Booking booking) {
        int daysBeforeStart = calculateDaysBeforeStart(booking.getCheckInDate());
        double totalAmount = booking.getTotalPrice();
        double refundAmount = booking.getCancellationPolicy().calculateRefund(totalAmount, daysBeforeStart, booking.getDailyPrice());
        
        this.getBookings().remove(booking);
        site.notifyEvent(EventType.PROPERTY_CANCELLATION, booking.getProperty());
        
        this.sendCancellationNotification(booking, refundAmount);
    }


	private int calculateDaysBeforeStart(Date checkInDate) {
	    LocalDate today = LocalDate.now();
	    LocalDate checkIn = checkInDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    return today.until(checkIn).getDays();
	}
	
	
	private void sendCancellationNotification(Booking booking, double refundAmount) {
		
		String email = createEmail(booking, refundAmount);
		booking.getTenant().receiveEmail(email); 
		
	}
   
	private String createEmail(Booking booking, double refundAmount) {
		return "";
	}

	public List<Booking> getUserBookings(User tenant) {
		return getBookings().stream()
				.filter(b -> b.getTenant().equals(tenant))
				.collect(Collectors.toList());
	}
	
	public Integer getUserBookingCount(User tenant) {
		return this.getUserBookings(tenant).size();
	}

	public List<Booking> getUserFutureBookings(User tenant) {
		
		Date today = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
	    
	    return this.getUserBookings(tenant).stream()
	            	.filter(b -> b.getCheckInDate().after(today))
	            	.collect(Collectors.toList());
	}

	public List<Booking> getUserBookingsInCity(User tenant, String city) {
		return this.getUserBookings(tenant).stream()
					.filter(b -> b.getProperty().getLocation().getCity().equals(city))
					.collect(Collectors.toList());
		
	}

	public List<String> getCitiesWithUserBookings(User tenant) {
		   
		return this.getUserBookings(tenant).stream()
		            .map(booking -> booking.getProperty().getLocation().getCity())
		            .distinct()
		            .collect(Collectors.toList());
		}

	public List<User> getTopTenTenants(List<User> tenants) {
		return tenants.stream()
                .sorted((tenant1, tenant2) -> Integer.compare(this.getUserBookingCount(tenant2), this.getUserBookingCount(tenant1))) 
                .limit(10) 
                .collect(Collectors.toList());
	}


}
