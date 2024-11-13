package booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import notification.PropertyEvent;
import property.Property;

class BookingQueueTest {

    private BookingQueue bookingQueue;
    private BookingManager bookingManager;
    private Queue<Booking> conditionalBookings;
    private Booking booking;
    private PropertyEvent event;
    private Property property;

    @BeforeEach
    void setUp() {
        bookingManager = mock(BookingManager.class);
        conditionalBookings = new LinkedList<>();
        bookingQueue = new BookingQueue(conditionalBookings, bookingManager);

        booking = mock(Booking.class);
        property = mock(Property.class);
        event = mock(PropertyEvent.class);

        when(booking.getProperty()).thenReturn(property);
    }

    @Test
    void testAddConditionalBooking() {
        bookingQueue.addConditionalBooking(booking);
        assertTrue(conditionalBookings.contains(booking), "Booking should be added to the conditional queue");
    }

    @Test
    void testApplyBooking() {
        bookingQueue.addConditionalBooking(booking);
        bookingQueue.applyBooking(booking);
        
        verify(booking).confirm();
        assertFalse(conditionalBookings.contains(booking), "Booking should be removed from the queue after application");
    }

    @Test
    void testUpdateWithAvailableProperty() {
        bookingQueue.addConditionalBooking(booking);

        when(event.propertyIsAvailableBetween(any(), any())).thenReturn(true);
        bookingQueue.update(event);

        verify(booking).confirm();
        verify(bookingManager).addBooking(booking);
    }
}
