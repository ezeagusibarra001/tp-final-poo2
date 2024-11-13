package booking;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import notification.EventType;
import notification.NotificationManager;
import user.Tenant;
import user.User;
import property.Property;


import java.time.LocalDate;
import java.time.ZoneId;

public class BookingManager {
	
	private List<Booking> bookings;
	private Map<Property, BookingQueue> waitingList;
	private NotificationManager notificationManager; //Creo que no debería ser variable de instancia de esta clase
	
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
    
    public NotificationManager getNotificationManager() {
		return notificationManager;
	}

	public void setNotificationManager(NotificationManager notificationManager) {
		this.notificationManager = notificationManager;
	}

	public Map<Property, BookingQueue> getWaitingList() {
		return waitingList;
	} 
	public BookingQueue getWaitingListOf(Property property) {
		return this.getWaitingList().getOrDefault(property, new BookingQueue(new LinkedList<>(), this));
	}

	private void setWaitingList(Map<Property, BookingQueue> waitingList) {
		this.waitingList = waitingList;
	}

    // Methods
    
	public void createBooking(Tenant tenant, Property property, Date checkInDate, Date checkOutDate) {
        Booking booking = new Booking(tenant, property.getOwner(), property, checkInDate, checkOutDate);
        
        if (property.isAvailableBetween(checkInDate, checkOutDate)) {
        	booking.confirm();
        	addBooking(booking);
        } else {
        	this.addToWaitingListOf(property, booking);
        	this.getNotificationManager().subscribe(EventType.PROPERTY_CANCELLATION, property, this.getWaitingListOf(property));     	
        }
        addBooking(booking);
    
	}
	
	private void addToWaitingListOf(Property property, Booking booking) {
		this.getWaitingListOf(property).addConditionalBooking(booking);		
	}

    public void addBooking(Booking booking) {
		this.getBookings().add(booking);
	}
    
    public void cancelBooking(Booking booking) {
        int daysBeforeStart = calculateDaysBeforeStart(booking.getCheckInDate());
        double totalAmount = booking.getTotalPrice();
        double refundAmount = booking.getCancellationPolicy().calculateRefund(totalAmount, daysBeforeStart, booking.getDailyPrice());
        
        this.getBookings().remove(booking);
        this.getNotificationManager().notify(EventType.PROPERTY_CANCELLATION, booking.getProperty());
        
        this.sendCancellationNotification(booking, refundAmount);
    }


	private int calculateDaysBeforeStart(Date checkInDate) {
	    LocalDate today = LocalDate.now();
	    LocalDate checkIn = checkInDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    return today.until(checkIn).getDays();
	}
	
	
	// Falta definir cómo recibe el email el inquilino	
	private void sendCancellationNotification(Booking booking, double refundAmount) {
		
		String email = createEmail(booking, refundAmount);
		//booking.getTenant().receiveEmail(email); 
		
	}
   
	private String createEmail(Booking booking, double refundAmount) {
		return "";
	}

	public List<Booking> getUserBookings(User tenant) {
		return getBookings().stream()
				.filter(b -> b.getTenant().equals(tenant))
				.collect(Collectors.toList());
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


}
