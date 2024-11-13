package booking;

import java.util.Queue;


import notification.EventListener;
import notification.PropertyEvent;

public class BookingQueue implements EventListener {

	private Queue<Booking> conditionalBookings;
	private BookingManager manager;
	
	//Constructor
	public BookingQueue(Queue<Booking> conditionalBookings, BookingManager manager) {
		this.setConditionalBookings(conditionalBookings);
		this.setManager(manager);
	}
	
	// Getters and Setters
	public Queue<Booking> getConditionalBookings() {
		return conditionalBookings;
	}
	
	private void setConditionalBookings(Queue<Booking> conditionalBookings) {
		this.conditionalBookings = conditionalBookings;
	}
	
	public BookingManager getManager() {
		return manager;
	}

	private void setManager(BookingManager manager) {
		this.manager = manager;
	}
	
	// Methods 
    
    // Método de la interfaz EventListener que actualiza la cola de reservas
    // condicionales al recibir un evento de la propiedad.
    // Si la propiedad está disponible entre las fechas de check-in del booking,
    // se confirma y se remueve de la cola de reservas condicionales.
	@Override
	public void update(PropertyEvent event) {
		Queue<Booking> conditionalBookings = this.getConditionalBookings();
		 
		 for (Booking booking : conditionalBookings) {
		 	if (event.propertyIsAvailableBetween(booking.getCheckInDate(), booking.getCheckInDate())) {
		        applyBooking(booking);
		        break;
		    }
		 }
	}
	
	
	public void addConditionalBooking(Booking booking) {
        this.getConditionalBookings().add(booking);
    }
	
	
	// Confirma un booking condicional, lo remueve de la cola de reservas
    // y lo agrega al BookingManager.
    public void applyBooking(Booking booking) {
       booking.confirm();
       this.getConditionalBookings().remove(booking);
       this.getManager().addBooking(booking);
       
    }



}

